package bg.sofia.uni.fmi.localmarketplace.repository;

import bg.sofia.uni.fmi.localmarketplace.domain.Event;
import bg.sofia.uni.fmi.localmarketplace.vo.EventType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {

    Page<Event> findByType(EventType type, Pageable pageable);

    Page<Event> findByUser_Username(String username, Pageable pageable);

    Page<Event> findByActiveTrue(Pageable pageable);
}
