package bg.sofia.uni.fmi.localmarketplace.service;

import bg.sofia.uni.fmi.localmarketplace.domain.Payment;
import bg.sofia.uni.fmi.localmarketplace.domain.order.Order;
import bg.sofia.uni.fmi.localmarketplace.dto.input.payment.CreatePaymentDTO;
import bg.sofia.uni.fmi.localmarketplace.dto.output.payment.PaymentDetailsDTO;
import bg.sofia.uni.fmi.localmarketplace.exception.order.OrderDoesNotExistException;
import bg.sofia.uni.fmi.localmarketplace.exception.payment.PaymentDoesNotExistException;
import bg.sofia.uni.fmi.localmarketplace.exception.user.OwnershipMismatchException;
import bg.sofia.uni.fmi.localmarketplace.repository.PaymentRepository;
import bg.sofia.uni.fmi.localmarketplace.repository.UserRepository;
import bg.sofia.uni.fmi.localmarketplace.repository.order.OrderRepository;
import bg.sofia.uni.fmi.localmarketplace.service.contract.PaymentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository, OrderRepository orderRepository,
                              UserRepository userRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Override
    public PaymentDetailsDTO getPayment(Long id) {
        return PaymentDetailsDTO.from(getPaymentById(id));
    }

    @Override
    public Page<PaymentDetailsDTO> getAllPaymentsOfAnOrder(Long orderId, Pageable pageable) {
        return paymentRepository.findByOrder_Id(orderId, pageable).map(PaymentDetailsDTO::from);
    }

    @Override
    public PaymentDetailsDTO createPayment(CreatePaymentDTO dto, String username) {
        Order order = getOrderById(dto.orderId());
        if (!order.getUser().getUsername().equals(username)) {
            throw new OwnershipMismatchException("Current user is not the owner of the order");
        }

        Payment payment = new Payment(order, dto.amount(), order.getCurrency(), dto.paymentMethod());
        paymentRepository.save(payment);
        return PaymentDetailsDTO.from(payment);
    }

    private Payment getPaymentById(Long id) {
        Optional<Payment> payment = paymentRepository.findById(id);
        if (payment.isEmpty()) {
            throw new PaymentDoesNotExistException("Payment with id " +  id + " does not exist");
        }
        return payment.get();
    }

    private Order getOrderById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty()) {
            throw new OrderDoesNotExistException("Order with id " +  id + " does not exist");
        }
        return order.get();
    }
}
