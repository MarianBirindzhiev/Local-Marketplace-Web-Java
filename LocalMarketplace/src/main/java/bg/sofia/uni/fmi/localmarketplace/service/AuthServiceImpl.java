package bg.sofia.uni.fmi.localmarketplace.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bg.sofia.uni.fmi.localmarketplace.dto.input.auth.LoginRequestDTO;
import bg.sofia.uni.fmi.localmarketplace.dto.input.user.CreateUserDTO;
import bg.sofia.uni.fmi.localmarketplace.dto.output.auth.AuthTokenDTO;
import bg.sofia.uni.fmi.localmarketplace.dto.output.user.UserDetailsDTO;
import bg.sofia.uni.fmi.localmarketplace.exception.auth.InvalidCredentialsException;
import bg.sofia.uni.fmi.localmarketplace.security.JwtService;
import bg.sofia.uni.fmi.localmarketplace.service.contract.AuthService;
import bg.sofia.uni.fmi.localmarketplace.service.contract.UserService;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthServiceImpl(UserService userService,
                           AuthenticationManager authenticationManager,
                           JwtService jwtService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public UserDetailsDTO register(CreateUserDTO dto) {
        return userService.createUser(dto);
    }

    @Override
    public AuthTokenDTO login(LoginRequestDTO dto) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.username(), dto.password())
            );
        } catch (AuthenticationException e) {
            throw new InvalidCredentialsException("Invalid username or password.", e);
        }
        String token = jwtService.generateToken(dto.username());
        return AuthTokenDTO.of(token, dto.username());
    }

    @Override
    public void logout() {
    }

    @Override
    public void forgotPassword(String email) {
    }
}
