package bg.sofia.uni.fmi.localmarketplace.dto.input.order;

import bg.sofia.uni.fmi.localmarketplace.utils.ValidationConstants;
import bg.sofia.uni.fmi.localmarketplace.vo.CurrencyType;
import bg.sofia.uni.fmi.localmarketplace.vo.PaymentMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request body for placing an order from the current cart")
public record PlaceOrderDTO(
    @NotNull(message = ValidationConstants.Order.NULL_PAYMENT_METHOD)
    @Schema(description = "Payment method to use", requiredMode = Schema.RequiredMode.REQUIRED)
    PaymentMethod paymentMethod,

    @NotNull(message = ValidationConstants.Order.NULL_CURRENCY)
    @Schema(description = "Currency for the order", requiredMode = Schema.RequiredMode.REQUIRED)
    CurrencyType currency
) {
}
