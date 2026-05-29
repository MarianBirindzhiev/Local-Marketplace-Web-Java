package bg.sofia.uni.fmi.localmarketplace.repository.order;

import bg.sofia.uni.fmi.localmarketplace.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
