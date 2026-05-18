package bg.sofia.uni.fmi.localmarketplace.repository;

import bg.sofia.uni.fmi.localmarketplace.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer> {
}
