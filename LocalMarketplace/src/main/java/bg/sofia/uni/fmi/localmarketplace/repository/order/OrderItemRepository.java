package bg.sofia.uni.fmi.localmarketplace.repository.order;

import bg.sofia.uni.fmi.localmarketplace.domain.order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
