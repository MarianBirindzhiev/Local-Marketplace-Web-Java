//package bg.sofia.uni.fmi.localmarketplace.controller;
//
//import bg.sofia.uni.fmi.localmarketplace.dto.input.payment.CreatePaymentDTO;
//import bg.sofia.uni.fmi.localmarketplace.dto.output.payment.PaymentDetailsDTO;
//import bg.sofia.uni.fmi.localmarketplace.service.contract.PaymentService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.Parameter;
//import io.swagger.v3.oas.annotations.media.Content;
//import io.swagger.v3.oas.annotations.media.Schema;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.responses.ApiResponses;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import jakarta.validation.Valid;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.ErrorResponse;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/payments")
//@Tag(name = "Payment Management", description = "Endpoints for handling payments for orders")
//public class PaymentController {
//
//    private PaymentService paymentService;
//
//    public PaymentController(PaymentService paymentService) {
//        this.paymentService = paymentService;
//    }
//
//    @Operation(summary = "Get a single payment", description = "Retrieves the details of a specific payment by its ID.")
//    @ApiResponses({
//        @ApiResponse(responseCode = "200", description = "Payment retrieved successfully",
//            content = @Content(schema = @Schema(implementation = PaymentDetailsDTO.class))),
//        @ApiResponse(responseCode = "401", description = "Unauthorized - invalid or missing auth credentials",
//            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
//        @ApiResponse(responseCode = "404", description = "Payment not found",
//            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
//        @ApiResponse(responseCode = "500", description = "Unexpected server error",
//            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
//    })
//    @GetMapping("/{paymentId}")
//    public ResponseEntity<PaymentDetailsDTO> getPaymentById(
//        @Parameter(description = "ID of the payment", required = true)
//        @PathVariable Long paymentId) {
//
//        return ResponseEntity.ok(paymentService.getPayment(paymentId));
//    }
//
//    @Operation(summary = "Create a new payment", description = "Creates a new payment for an existing order.")
//    @ApiResponses({
//        @ApiResponse(responseCode = "200", description = "Payment created successfully",
//            content = @Content(schema = @Schema(implementation = PaymentDetailsDTO.class))),
//        @ApiResponse(responseCode = "400", description = "Invalid payment data",
//            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
//        @ApiResponse(responseCode = "401", description = "Unauthorized - invalid or missing auth credentials",
//            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
//        @ApiResponse(responseCode = "403", description = "User tried paying for someone else's order",
//            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
//        @ApiResponse(responseCode = "404", description = "Order not found",
//            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
//        @ApiResponse(responseCode = "500", description = "Unexpected server error",
//            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
//    })
//    @PostMapping
//    public ResponseEntity<PaymentDetailsDTO> createPayment(
//        @io.swagger.v3.oas.annotations.parameters.RequestBody(
//            description = "Payment creation data", required = true, content = @Content)
//        @Valid @RequestBody CreatePaymentDTO dto) {
//
//        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        return ResponseEntity.ok(paymentService.createPayment(dto, username));
//    }
//}
