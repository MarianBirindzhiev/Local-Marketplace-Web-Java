package bg.sofia.uni.fmi.localmarketplace.repository.cart;

import bg.sofia.uni.fmi.localmarketplace.domain.cart.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
