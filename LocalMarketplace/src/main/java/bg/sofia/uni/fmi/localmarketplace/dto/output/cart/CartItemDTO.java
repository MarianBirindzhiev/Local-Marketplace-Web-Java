package bg.sofia.uni.fmi.localmarketplace.dto.output.cart;

import bg.sofia.uni.fmi.localmarketplace.domain.cart.CartItem;
import bg.sofia.uni.fmi.localmarketplace.dto.output.product.ProductDetailsDTO;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Details of a single item in the cart")
public record CartItemDTO(
    @Schema(description = "Cart item ID")
    Long id,

    @Schema(description = "Product details")
    ProductDetailsDTO product,

    @Schema(description = "Quantity in cart")
    int quantity,

    @Schema(description = "Subtotal for this line item (price × quantity)")
    long subtotal
) {

    public static CartItemDTO from(CartItem item) {
        return new CartItemDTO(
            item.getId(),
            ProductDetailsDTO.from(item.getProduct()),
            item.getQuantity(),
            item.getProduct().getPrice() * item.getQuantity()
        );
    }
}
