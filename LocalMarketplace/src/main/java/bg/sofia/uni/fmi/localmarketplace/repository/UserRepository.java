package bg.sofia.uni.fmi.localmarketplace.repository;

import bg.sofia.uni.fmi.localmarketplace.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

//    User save(User user);
//    Optional<User> findById(Long id);
//    List<User> findAll();
//    void deleteById(Long id);
//    boolean existsById(Long id);
//
//    Optional<User> findByEmail(String email);
}
