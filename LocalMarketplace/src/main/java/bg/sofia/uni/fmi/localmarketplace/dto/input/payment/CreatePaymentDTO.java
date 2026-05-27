package bg.sofia.uni.fmi.localmarketplace.dto.input.payment;

import bg.sofia.uni.fmi.localmarketplace.vo.CurrencyType;
import bg.sofia.uni.fmi.localmarketplace.vo.PaymentMethod;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request body for creating a new payment")
public record CreatePaymentDTO(
    @Schema(description = "The id of the order.", requiredMode = Schema.RequiredMode.REQUIRED)
    long orderId,

    @Schema(description = "The amount of the payment.", requiredMode = Schema.RequiredMode.REQUIRED)
    long amount,

    @Schema(description = "The currency of the payment.", requiredMode = Schema.RequiredMode.REQUIRED)
    CurrencyType currencyType,

    @Schema(description = "The payment method.", requiredMode = Schema.RequiredMode.REQUIRED)
    PaymentMethod paymentMethod
) {
}
