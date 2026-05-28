package bg.sofia.uni.fmi.localmarketplace.controller;

public class OrderController {


    @Operation(
        summary = "Get all payments of an order",
        description = "Retrieves a paginated list of all payments associated with a specific order."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Payments retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized - invalid or missing auth credentials",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "404", description = "Order not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Unexpected server error",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/order/{orderId}")
    public ResponseEntity<Page<PaymentDetailsDTO>> getAllPaymentsOfOrder(
        @Parameter(description = "ID of the order", required = true)
        @PathVariable Long orderId,
        Pageable pageable) {

        return ResponseEntity.ok(
            paymentService.getAllPaymentsOfOrder(orderId, pageable)
        );
    }
}
