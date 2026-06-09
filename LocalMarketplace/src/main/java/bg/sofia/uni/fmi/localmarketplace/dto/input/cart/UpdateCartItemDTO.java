package bg.sofia.uni.fmi.localmarketplace.dto.input.cart;

import bg.sofia.uni.fmi.localmarketplace.utils.ValidationConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;

@Schema(description = "Request body for updating the quantity of a cart item")
public record UpdateCartItemDTO(
    @Min(value = 1, message = ValidationConstants.Cart.MIN_QUANTITY)
    @Schema(description = "New quantity. Minimum 1.", requiredMode = Schema.RequiredMode.REQUIRED, minimum = "1")
    int quantity
) {
}
