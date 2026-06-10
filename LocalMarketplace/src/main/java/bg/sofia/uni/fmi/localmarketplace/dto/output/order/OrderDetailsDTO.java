package bg.sofia.uni.fmi.localmarketplace.dto.output.order;

import bg.sofia.uni.fmi.localmarketplace.domain.order.Order;
import bg.sofia.uni.fmi.localmarketplace.vo.CurrencyType;
import bg.sofia.uni.fmi.localmarketplace.vo.OrderStatus;
import bg.sofia.uni.fmi.localmarketplace.vo.PaymentMethod;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Details of an order")
public record OrderDetailsDTO(
    @Schema(description = "Order ID")
    Long id,

    @Schema(description = "Username of the customer who placed the order")
    String username,

    @Schema(description = "Currency of the order")
    CurrencyType currency,

    @Schema(description = "Total amount of the order")
    long totalAmount,

    @Schema(description = "Payment method used")
    PaymentMethod paymentMethod,

    @Schema(description = "Current status of the order")
    OrderStatus status,

    @Schema(description = "Items in the order")
    List<OrderItemDTO> orderItems
) {

    public static OrderDetailsDTO from(Order order) {
        List<OrderItemDTO> items = order.getOrderItems().stream()
            .map(OrderItemDTO::from)
            .toList();
        return new OrderDetailsDTO(
            order.getId(),
            order.getUser().getUsername(),
            order.getCurrency(),
            order.getTotalAmount(),
            order.getPaymentMethod(),
            order.getStatus(),
            items
        );
    }
}
