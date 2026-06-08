package bg.sofia.uni.fmi.localmarketplace.repository.cart;

import bg.sofia.uni.fmi.localmarketplace.domain.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUser_Username(String username);
}
