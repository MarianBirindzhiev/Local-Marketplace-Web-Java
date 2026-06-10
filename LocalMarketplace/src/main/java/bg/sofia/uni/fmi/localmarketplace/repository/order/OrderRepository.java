package bg.sofia.uni.fmi.localmarketplace.repository.order;

import bg.sofia.uni.fmi.localmarketplace.domain.order.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findByUser_Username(String username, Pageable pageable);
}
