package bg.sofia.uni.fmi.localmarketplace.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bg.sofia.uni.fmi.localmarketplace.domain.Product;
import bg.sofia.uni.fmi.localmarketplace.domain.User;
import bg.sofia.uni.fmi.localmarketplace.domain.cart.Cart;
import bg.sofia.uni.fmi.localmarketplace.domain.cart.CartItem;
import bg.sofia.uni.fmi.localmarketplace.domain.order.Order;
import bg.sofia.uni.fmi.localmarketplace.domain.order.OrderItem;
import bg.sofia.uni.fmi.localmarketplace.dto.input.order.PlaceOrderDTO;
import bg.sofia.uni.fmi.localmarketplace.dto.output.order.OrderDetailsDTO;
import bg.sofia.uni.fmi.localmarketplace.exception.order.EmptyCartException;
import bg.sofia.uni.fmi.localmarketplace.exception.order.InsufficientStockException;
import bg.sofia.uni.fmi.localmarketplace.exception.order.InvalidOrderStatusException;
import bg.sofia.uni.fmi.localmarketplace.exception.order.OrderDoesNotExistException;
import bg.sofia.uni.fmi.localmarketplace.exception.user.OwnershipMismatchException;
import bg.sofia.uni.fmi.localmarketplace.exception.user.UserNotFoundException;
import bg.sofia.uni.fmi.localmarketplace.repository.UserRepository;
import bg.sofia.uni.fmi.localmarketplace.repository.cart.CartRepository;
import bg.sofia.uni.fmi.localmarketplace.repository.order.OrderRepository;
import bg.sofia.uni.fmi.localmarketplace.service.contract.OrderService;
import bg.sofia.uni.fmi.localmarketplace.utils.ValidationConstants;
import bg.sofia.uni.fmi.localmarketplace.vo.OrderStatus;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository, CartRepository cartRepository,
                            UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    @Override
    public OrderDetailsDTO placeOrderFromCart(String username, PlaceOrderDTO dto) {
        User user = getUser(username);
        Cart cart = getNonEmptyCart(username);

        List<CartItem> cartItems = cart.getItems();
        validateStockAvailability(cartItems);

        Order order = new Order(user, dto.currency(), 0L, dto.paymentMethod(),
            OrderStatus.PENDING_PAYMENT, new ArrayList<>());
        order.setTotalAmount(buildOrderItems(order, cartItems));
        orderRepository.save(order);

        decrementStock(cartItems);
        clearCart(cart);

        return OrderDetailsDTO.from(order);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderDetailsDTO> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable).map(OrderDetailsDTO::from);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDetailsDTO getOrder(Long id, String username) {
        Order order = findOrder(id);
        User requester = getUser(username);
        if (!requester.isAdmin() && !order.getUser().getUsername().equals(username)) {
            throw new OwnershipMismatchException(
                "Order " + id + " does not belong to user " + username);
        }
        return OrderDetailsDTO.from(order);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderDetailsDTO> getMyOrders(String username, Pageable pageable) {
        return orderRepository.findByUser_Username(username, pageable).map(OrderDetailsDTO::from);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderDetailsDTO> getVendorOrders(String vendorUsername, Pageable pageable) {
        return orderRepository
            .findDistinctByOrderItems_Product_Maker_Username(vendorUsername, pageable)
            .map(OrderDetailsDTO::from);
    }

    @Override
    public OrderDetailsDTO updateStatus(Long id, OrderStatus newStatus, String requester) {
        // TODO: enforce vendor/admin role once Spring Security is wired
        if (newStatus == OrderStatus.CANCELLED) {
            throw new InvalidOrderStatusException(
                "Use PATCH /api/orders/" + id + "/cancel to cancel an order — direct status update to CANCELLED is not allowed");
        }
        Order order = findOrder(id);
        rejectIfTerminal(order);
        order.setStatus(newStatus);
        return OrderDetailsDTO.from(order);
    }

    @Override
    public OrderDetailsDTO cancelOrder(Long id, String requester) {
        // TODO: enforce customer/vendor ownership once Spring Security is wired
        Order order = findOrder(id);
        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new InvalidOrderStatusException(
                ValidationConstants.Order.INVALID_STATUS_TRANSITION + " Order " + id + " is already cancelled");
        }
        rejectIfTerminal(order);

        restoreStock(order.getOrderItems());
        order.setStatus(OrderStatus.CANCELLED);
        return OrderDetailsDTO.from(order);
    }

    private Cart getNonEmptyCart(String username) {
        Cart cart = cartRepository.findByUser_Username(username)
            .orElseThrow(() -> new EmptyCartException(ValidationConstants.Order.EMPTY_CART));
        if (cart.getItems().isEmpty()) {
            throw new EmptyCartException(ValidationConstants.Order.EMPTY_CART);
        }
        return cart;
    }

    private void validateStockAvailability(List<CartItem> cartItems) {
        for (CartItem ci : cartItems) {
            Product product = ci.getProduct();
            if (ci.getQuantity() > product.getQuantity()) {
                throw new InsufficientStockException(
                    ValidationConstants.Order.INSUFFICIENT_STOCK +
                        " Product " + product.getId() +
                        ": requested " + ci.getQuantity() + ", available " + product.getQuantity());
            }
        }
    }

    private long buildOrderItems(Order order, List<CartItem> cartItems) {
        long total = 0L;
        for (CartItem ci : cartItems) {
            long price = ci.getProduct().getPrice();
            order.getOrderItems().add(new OrderItem(order, ci.getProduct(), ci.getQuantity(), price));
            total += price * ci.getQuantity();
        }
        return total;
    }

    private void decrementStock(List<CartItem> cartItems) {
        for (CartItem ci : cartItems) {
            Product product = ci.getProduct();
            product.setQuantity(product.getQuantity() - ci.getQuantity());
        }
    }

    private void restoreStock(List<OrderItem> orderItems) {
        for (OrderItem oi : orderItems) {
            Product product = oi.getProduct();
            product.setQuantity(product.getQuantity() + oi.getQuantity());
        }
    }

    private void clearCart(Cart cart) {
        cart.getItems().clear();
    }

    private void rejectIfTerminal(Order order) {
        OrderStatus current = order.getStatus();
        if (current == OrderStatus.DELIVERED || current == OrderStatus.CANCELLED) {
            throw new InvalidOrderStatusException(
                ValidationConstants.Order.INVALID_STATUS_TRANSITION + " Cannot modify a " + current + " order");
        }
    }

    private Order findOrder(Long id) {
        return orderRepository.findById(id)
            .orElseThrow(() -> new OrderDoesNotExistException("Order with id " + id + " does not exist"));
    }

    private User getUser(String username) {
        return userRepository.findById(username)
            .orElseThrow(() -> new UserNotFoundException("User with username " + username + " does not exist"));
    }
}
