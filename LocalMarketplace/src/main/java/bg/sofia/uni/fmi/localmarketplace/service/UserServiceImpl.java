package bg.sofia.uni.fmi.localmarketplace.service;

import bg.sofia.uni.fmi.localmarketplace.domain.User;
import bg.sofia.uni.fmi.localmarketplace.dto.input.user.CreateUserDTO;
import bg.sofia.uni.fmi.localmarketplace.dto.input.user.UpdateUserDTO;
import bg.sofia.uni.fmi.localmarketplace.dto.output.user.UserDetailsDTO;
import bg.sofia.uni.fmi.localmarketplace.exception.user.EmailAlreadyExistsException;
import bg.sofia.uni.fmi.localmarketplace.exception.user.UserAlreadyExistsException;
import bg.sofia.uni.fmi.localmarketplace.exception.user.UserNotFoundException;
import bg.sofia.uni.fmi.localmarketplace.repository.UserRepository;
import bg.sofia.uni.fmi.localmarketplace.service.contract.UserService;
import bg.sofia.uni.fmi.localmarketplace.vo.UserType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isAdmin(String username) {
        User user = getUserByUsername(username);
        return user.isAdmin();
    }

    @Override
    public UserDetailsDTO getUser(String username) {
        User user = getUserByUsername(username);
        return UserDetailsDTO.from(user);
    }

    @Override
    public Page<UserDetailsDTO> getAllUsers(Pageable pageable) {
        Page<User> usersPage = userRepository.findAll(pageable);
        return usersPage.map(UserDetailsDTO::from);
    }

    @Override
    public UserDetailsDTO createUser(CreateUserDTO dto) {

        if (userRepository.existsByUsername(dto.username())) {
            throw new UserAlreadyExistsException(dto.username());
        }

        if (userRepository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("Този имейл адрес вече се използва!");
        }

        User newUser =
            new User(dto.username(), dto.firstName(), dto.lastName(), dto.password(), dto.email(), dto.phone(),
                UserType.CUSTOMER);

        userRepository.save(newUser);
        return UserDetailsDTO.from(newUser);
    }

    @Override
    public UserDetailsDTO updateUser(String username, UpdateUserDTO updateDTO) {

        User existingUser = getUserByUsername(username);

        if (!existingUser.getEmail().equals(updateDTO.email()) && userRepository.existsByEmail(updateDTO.email())) {
            throw new EmailAlreadyExistsException("Email already in use!");
        }

        if (updateDTO.email() != null && !updateDTO.email().isBlank()) {
            existingUser.setEmail(updateDTO.email());
        }
        if (updateDTO.password() != null && !updateDTO.password().isBlank()) {
            existingUser.setPassword(updateDTO.password());
        }
        if (updateDTO.phone() != null && !updateDTO.phone().isBlank()) {
            existingUser.setPhone(updateDTO.phone());
        }

        return UserDetailsDTO.from(existingUser);
    }

    @Override
    public void deleteUser(String username) {
        User user = getUserByUsername(username);
        userRepository.delete(user);
    }

    private User getUserByUsername(String username) {
        Optional<User> user = userRepository.findById(username);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        return user.get();
    }
}
