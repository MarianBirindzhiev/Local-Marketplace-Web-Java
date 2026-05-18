package bg.sofia.uni.fmi.localmarketplace.repository;

import bg.sofia.uni.fmi.localmarketplace.domain.User;
import bg.sofia.uni.fmi.localmarketplace.dto.output.UserDetailsDTO;
import bg.sofia.uni.fmi.localmarketplace.utils.ValidationConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    User findByUsername(String username);

    boolean existsByUsername(@NotBlank(message = ValidationConstants.User.BLANK_USERNAME)
                             @Size(max = 50, message = ValidationConstants.User.LENGTH_USERNAME) String username);

    boolean existsByEmail(String email);

//    User save(User user);
//    Optional<User> findById(Long id);
//    List<User> findAll();
//    void deleteById(Long id);
//    boolean existsById(Long id);
//
//    Optional<User> findByEmail(String email);
}
