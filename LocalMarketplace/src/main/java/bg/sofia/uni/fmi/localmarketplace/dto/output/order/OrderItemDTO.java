package bg.sofia.uni.fmi.localmarketplace.dto.output.order;

import bg.sofia.uni.fmi.localmarketplace.domain.order.OrderItem;
import bg.sofia.uni.fmi.localmarketplace.dto.output.product.ProductDetailsDTO;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Details of a single item in an order")
public record OrderItemDTO(
    @Schema(description = "Order item ID")
    Long id,

    @Schema(description = "Product details at time of order")
    ProductDetailsDTO product,

    @Schema(description = "Quantity ordered")
    int quantity,

    @Schema(description = "Price snapshot at time of order")
    long price
) {

    public static OrderItemDTO from(OrderItem item) {
        return new OrderItemDTO(
            item.getId(),
            ProductDetailsDTO.from(item.getProduct()),
            item.getQuantity(),
            item.getPrice()
        );
    }
}
