package bg.sofia.uni.fmi.localmarketplace.dto.input.order;

import bg.sofia.uni.fmi.localmarketplace.utils.ValidationConstants;
import bg.sofia.uni.fmi.localmarketplace.vo.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request body for updating the status of an order")
public record UpdateOrderStatusDTO(
    @NotNull(message = ValidationConstants.Order.NULL_STATUS)
    @Schema(description = "New order status", requiredMode = Schema.RequiredMode.REQUIRED)
    OrderStatus status
) {
}
