package bg.sofia.uni.fmi.localmarketplace.dto.output.cart;

import bg.sofia.uni.fmi.localmarketplace.domain.cart.Cart;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Details of the current user's shopping cart")
public record CartDetailsDTO(
    @Schema(description = "Cart ID")
    Long id,

    @Schema(description = "Items in the cart")
    List<CartItemDTO> items,

    @Schema(description = "Total price of all items in the cart")
    long total
) {

    public static CartDetailsDTO from(Cart cart) {
        List<CartItemDTO> itemDTOs = cart.getItems().stream()
            .map(CartItemDTO::from)
            .toList();
        long total = itemDTOs.stream().mapToLong(CartItemDTO::subtotal).sum();
        return new CartDetailsDTO(cart.getId(), itemDTOs, total);
    }
}
