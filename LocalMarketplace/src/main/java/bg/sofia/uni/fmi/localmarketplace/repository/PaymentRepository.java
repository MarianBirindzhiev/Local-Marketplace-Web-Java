package bg.sofia.uni.fmi.localmarketplace.repository;

import bg.sofia.uni.fmi.localmarketplace.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
