package fr.antoine.fraudify.utils;

import fr.antoine.fraudify.models.User;
import fr.antoine.fraudify.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserInitializer implements ApplicationRunner {

    private static final String USERNAME = "Antoine";
    private static final String PASSWORD = "password";


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if(!userExists()) {
            createUser();
        }
    }

    private void createUser() {
        User user = User.builder()
                .username(USERNAME)
                .password(passwordEncoder.encode(PASSWORD))
                .build();
        userRepository.save(user);
    }

    private boolean userExists() {
        return userRepository.findByUsername("Antoine").isPresent();
    }
}
