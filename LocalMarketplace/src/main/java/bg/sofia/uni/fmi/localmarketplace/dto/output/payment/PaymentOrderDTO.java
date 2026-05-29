//package bg.sofia.uni.fmi.localmarketplace.dto.output.payment;
//
//import bg.sofia.uni.fmi.localmarketplace.vo.CurrencyType;
//import bg.sofia.uni.fmi.localmarketplace.vo.OrderStatus;
//import io.swagger.v3.oas.annotations.media.Schema;
//
//@Schema(description = "Response body containing detailed information about the order of a payment.")
//public record PaymentOrderDTO(
//    @Schema(description = "The id of the order", requiredMode = Schema.RequiredMode.REQUIRED)
//    long orderId,
//
//    @Schema(description = "The username of the user who made the order", requiredMode = Schema.RequiredMode.REQUIRED)
//    String username,
//
//    @Schema(description = "The status of the order", requiredMode = Schema.RequiredMode.REQUIRED)
//    OrderStatus orderStatus,
//
//    @Schema(description = "The total amount of the order", requiredMode = Schema.RequiredMode.AUTO)
//    long totalAmount,
//
//    @Schema(description = "The currency fot the payment", requiredMode = Schema.RequiredMode.AUTO)
//    CurrencyType currencyType
//) {
//}
