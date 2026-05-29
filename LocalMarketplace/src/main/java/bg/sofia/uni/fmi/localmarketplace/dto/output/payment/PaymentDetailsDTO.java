//package bg.sofia.uni.fmi.localmarketplace.dto.output.payment;
//
//import bg.sofia.uni.fmi.localmarketplace.domain.Payment;
//import bg.sofia.uni.fmi.localmarketplace.domain.order.Order;
//import bg.sofia.uni.fmi.localmarketplace.vo.CurrencyType;
//import bg.sofia.uni.fmi.localmarketplace.vo.PaymentMethod;
//import io.swagger.v3.oas.annotations.media.Schema;
//
//@Schema(description = "Response body containing detailed information about a payment.")
//public record PaymentDetailsDTO(
//    @Schema(description = "Id of the payment.", requiredMode = Schema.RequiredMode.REQUIRED)
//    Long id,
//
//    @Schema(description = "Description of the order.", requiredMode = Schema.RequiredMode.REQUIRED)
//    PaymentOrderDTO orderDTO,
//
//    @Schema(description = "The amount to be paid.", requiredMode = Schema.RequiredMode.REQUIRED)
//    long amount,
//
//    @Schema(description = "The currency fot the payment.", requiredMode = Schema.RequiredMode.AUTO)
//    CurrencyType currencyType,
//
//    @Schema(description = "The payment method.", requiredMode = Schema.RequiredMode.REQUIRED)
//    PaymentMethod paymentMethod
//) {
//
//    public static PaymentDetailsDTO from(Payment payment) {
//        Order order = payment.getOrder();
//
//        PaymentOrderDTO paymentOrderDTO =
//            new PaymentOrderDTO(order.getId(), order.getUser().getUsername(), order.getStatus(), order.getTotalAmount(),
//                order.getCurrency());
//
//        return new PaymentDetailsDTO(payment.getId(), paymentOrderDTO, payment.getAmount(), payment.getCurrency(),
//            payment.getPaymentMethod());
//    }
//}
