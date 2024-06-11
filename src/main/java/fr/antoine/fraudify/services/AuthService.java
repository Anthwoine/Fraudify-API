package fr.antoine.fraudify.services;

import fr.antoine.fraudify.dto.request.LoginRequest;
import fr.antoine.fraudify.dto.request.RegisterRequest;
import fr.antoine.fraudify.dto.response.LoginResponse;
import fr.antoine.fraudify.dto.response.RegisterResponse;
import fr.antoine.fraudify.models.User;
import fr.antoine.fraudify.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;



    public RegisterResponse register(RegisterRequest registerRequest) {
        User user = User.builder()
                .username(registerRequest.username())
                .password(passwordEncoder.encode(registerRequest.password()))
                .build();

        User savedUser = userRepository.save(user);
        String token = jwtService.generateToken(savedUser);

        return RegisterResponse.builder()
                .user(savedUser)
                .accessToken(token)
                .build();
    }



    public LoginResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.username(),
                        loginRequest.password()
                )
        );

        User user = userRepository.findByUsername(loginRequest.username())
                .orElseThrow();

        return LoginResponse.builder()
                .token(jwtService.generateToken(user))
                .id(user.getUserId())
                .username(user.getUsername())
                .profilePicture(user.getProfilePicture())
                .build();
    }
}
