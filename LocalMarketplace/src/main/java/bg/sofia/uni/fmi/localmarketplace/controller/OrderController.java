package bg.sofia.uni.fmi.localmarketplace.controller;

import bg.sofia.uni.fmi.localmarketplace.dto.input.order.PlaceOrderDTO;
import bg.sofia.uni.fmi.localmarketplace.dto.input.order.UpdateOrderStatusDTO;
import bg.sofia.uni.fmi.localmarketplace.dto.output.order.OrderDetailsDTO;
import bg.sofia.uni.fmi.localmarketplace.response.ValidationErrorResponse;
import bg.sofia.uni.fmi.localmarketplace.service.contract.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api")
@Tag(name = "Order management", description = "Endpoints for placing and managing orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/orders")
    @Operation(summary = "Place an order from cart",
        description = "Creates an order from the current user's cart. Snapshots prices, decrements stock, sets status PENDING_PAYMENT, and clears the cart.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Order placed successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input or empty cart",
            content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized",
            content = @Content(schema = @Schema()))
    })
    public ResponseEntity<OrderDetailsDTO> placeOrder(
        @Valid @RequestBody PlaceOrderDTO dto,
        @Parameter(hidden = true) Principal principal) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(orderService.placeOrderFromCart(principal.getName(), dto));
    }

    @GetMapping("/orders")
    @Operation(summary = "List all orders (admin)",
        description = "Returns a paginated list of all orders. Admin use only.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Orders retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized",
            content = @Content(schema = @Schema()))
    })
    public ResponseEntity<Page<OrderDetailsDTO>> getAllOrders(
        @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(orderService.getAllOrders(pageable));
    }

    @GetMapping("/orders/{id}")
    @Operation(summary = "Get order by ID",
        description = "Returns the order with the given ID. Accessible by the order owner or an admin.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Order retrieved successfully"),
        @ApiResponse(responseCode = "403", description = "Requester is not the order owner or an admin",
            content = @Content(schema = @Schema())),
        @ApiResponse(responseCode = "404", description = "Order not found",
            content = @Content(schema = @Schema()))
    })
    public ResponseEntity<OrderDetailsDTO> getOrderById(
        @Parameter(description = "Order ID", required = true) @PathVariable Long id,
        @Parameter(hidden = true) Principal principal) {
        return ResponseEntity.ok(orderService.getOrder(id, principal.getName()));
    }

    @GetMapping("/users/me/orders")
    @Operation(summary = "Get my orders",
        description = "Returns a paginated list of orders placed by the currently authenticated user.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Orders retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized",
            content = @Content(schema = @Schema()))
    })
    public ResponseEntity<Page<OrderDetailsDTO>> getMyOrders(
        @PageableDefault(size = 20, sort = "id") Pageable pageable,
        @Parameter(hidden = true) Principal principal) {
        return ResponseEntity.ok(orderService.getMyOrders(principal.getName(), pageable));
    }

    @GetMapping("/vendors/{vendorUsername}/orders")
    @Operation(summary = "Get vendor orders",
        description = "Returns a paginated list of orders that contain products made by the given vendor.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Orders retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized",
            content = @Content(schema = @Schema()))
    })
    public ResponseEntity<Page<OrderDetailsDTO>> getVendorOrders(
        @Parameter(description = "Vendor username", required = true) @PathVariable String vendorUsername,
        @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(orderService.getVendorOrders(vendorUsername, pageable));
    }

    @PatchMapping("/orders/{id}/status")
    @Operation(summary = "Update order status",
        description = "Updates the status of an order. Cannot set CANCELLED (use the /cancel endpoint) or transition out of DELIVERED.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Status updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid status or transition not allowed",
            content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class))),
        @ApiResponse(responseCode = "404", description = "Order not found",
            content = @Content(schema = @Schema()))
    })
    public ResponseEntity<OrderDetailsDTO> updateStatus(
        @Parameter(description = "Order ID", required = true) @PathVariable Long id,
        @Valid @RequestBody UpdateOrderStatusDTO dto,
        @Parameter(hidden = true) Principal principal) {
        return ResponseEntity.ok(orderService.updateStatus(id, dto.status(), principal.getName()));
    }

    @PatchMapping("/orders/{id}/cancel")
    @Operation(summary = "Cancel an order",
        description = "Cancels the order and restores product stock for each item.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Order cancelled successfully"),
        @ApiResponse(responseCode = "400", description = "Order cannot be cancelled (already CANCELLED or DELIVERED)",
            content = @Content(schema = @Schema())),
        @ApiResponse(responseCode = "404", description = "Order not found",
            content = @Content(schema = @Schema()))
    })
    public ResponseEntity<OrderDetailsDTO> cancelOrder(
        @Parameter(description = "Order ID", required = true) @PathVariable Long id,
        @Parameter(hidden = true) Principal principal) {
        return ResponseEntity.ok(orderService.cancelOrder(id, principal.getName()));
    }
}
