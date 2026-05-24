package bg.sofia.uni.fmi.localmarketplace.controller;

import bg.sofia.uni.fmi.localmarketplace.dto.input.product.CreateProductDTO;
import bg.sofia.uni.fmi.localmarketplace.dto.input.product.UpdateProductDTO;
import bg.sofia.uni.fmi.localmarketplace.dto.input.review.CreateReviewDTO;
import bg.sofia.uni.fmi.localmarketplace.dto.output.product.ProductDetailsDTO;
import bg.sofia.uni.fmi.localmarketplace.dto.output.review.ReviewDetailsDTO;
import bg.sofia.uni.fmi.localmarketplace.response.ValidationErrorResponse;
import bg.sofia.uni.fmi.localmarketplace.service.contract.ProductService;
import bg.sofia.uni.fmi.localmarketplace.service.contract.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Product management", description = "Endpoints for managing products in the local marketplace")
public class ProductController {

    private final ProductService productService;
    private final ReviewService reviewService;

    public ProductController(ProductService productService, ReviewService reviewService) {
        this.productService = productService;
        this.reviewService = reviewService;
    }

    @GetMapping
    @Operation(summary = "List all products",
        description = "Retrieves a paginated and optionally sorted list of all available products in the marketplace.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the paginated list of products.")
    public ResponseEntity<Page<ProductDetailsDTO>> getAllProducts(
        @PageableDefault(size = 12, sort = "id")
        @Parameter(description = "Pagination parameters (page, size, sort). Page numbers are zero-based.")
        Pageable pageable
    ) {
        Page<ProductDetailsDTO> products = productService.getAllProducts(pageable);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID",
        description = "Retrieves detailed information about a specific product using its unique identifier.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product found and returned successfully."),
        @ApiResponse(responseCode = "404", description = "Product not found with the provided ID.")
    })
    public ResponseEntity<ProductDetailsDTO> getProductById(
        @PathVariable
        @Parameter(description = "The unique long identifier of the product.", required = true)
        Long id) {

        ProductDetailsDTO product = productService.getProduct(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping
    @Operation(
        summary = "Create a new product",
        description = "Allows an authenticated artisan/user to upload and list a new product in the marketplace."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Product successfully created."),
        @ApiResponse(responseCode = "401", description = "Unauthorized. User must be authenticated to create a product.")
    })
    public ResponseEntity<ProductDetailsDTO> createProduct(
        @RequestBody CreateProductDTO dto,
        @Parameter(hidden = true) Principal principal) {

        // principal.getName() automatically returns the username of the currently logged user
        ProductDetailsDTO createdProduct = productService.createProduct(dto, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing product",
        description = "Updates the product details. Only the authenticated user who originally created the product (the maker) is allowed to perform this operation.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product successfully updated."),
        @ApiResponse(responseCode = "403", description = "Forbidden. Requester is not the original owner/maker of the product."),
        @ApiResponse(responseCode = "44", description = "Product not found.")
    })
    public ResponseEntity<ProductDetailsDTO> updateProduct(
        @PathVariable
        @Parameter(description = "The ID of the product to update.", required = true)
        Long id,
        @RequestBody UpdateProductDTO dto,
        @Parameter(hidden = true) Principal principal) {

        ProductDetailsDTO updatedProduct = productService.updateProduct(id, principal.getName(), dto);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product",
        description = "Removes a product from the marketplace. Only the authenticated maker of the product can delete it.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Product successfully removed. No content returned."),
        @ApiResponse(responseCode = "403", description = "Forbidden. Requester does not own this product."),
        @ApiResponse(responseCode = "404", description = "Product not found.")
    })
    public ResponseEntity<Void> deleteProduct(
        @PathVariable
        @Parameter(description = "The ID of the product to delete.", required = true)
        Long id,
        @Parameter(hidden = true) Principal principal) {

        productService.deleteProduct(id, principal.getName());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get reviews for a product", description = "Retrieves a paginated list of reviews left by users for a specific product.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Reviews retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized - invalid or missing auth credentials",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "404", description = "Product not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Unexpected server error",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{productId}/comments")
    public ResponseEntity<Page<ReviewDetailsDTO>> getReviewsByProduct(
        @Parameter(description = "ID of the product to fetch reviews for", required = true) @PathVariable
        Long productId,
        @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(reviewService.getAllReviewsOfAProduct(productId, pageable));
    }

    @Operation(summary = "Add review to product", description = "Creates a new review for a product. The author is the currently authenticated user.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Review added successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid review data",
            content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized - invalid or missing auth credentials",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "404", description = "Ticket or author user not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Unexpected server error",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/{productId}/comments")
    public ResponseEntity<Void> addReviewToProduct(
        @Parameter(description = "Id of the product", required = true) @PathVariable Long productId,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Review creation data", required = true, content = @Content)
        @Valid @org.springframework.web.bind.annotation.RequestBody CreateReviewDTO dto) {

        String author = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        reviewService.createReview(dto, author);

        return ResponseEntity.noContent().build();
    }
}
