package com.me.service;

import com.me.dto.AuthRequest;
import com.me.dto.AuthResponse;
import com.me.dto.RegisterRequest;
import com.me.repository.UserRepository;
import com.me.repository.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    public AuthResponse register(RegisterRequest request) {
        User user = new User()
                .setFirstname(request.getFirstname())
                .setName(request.getName())
                .setLastname(request.getLastname())
                .setPassword(passwordEncoder.encode(request.getPassword()));
        Optional<User> userOptional = userRepository.findByName(request.getName());
        if (userOptional.isEmpty()) {
            userRepository.save(user);
            log.info("User created: {}", user.getName());
            String token = jwtService.generateToken(user);
            return new AuthResponse()
                    .setToken(token);
        }

        return null;
    }

    public AuthResponse auth(AuthRequest request) {
        Optional<User> optionalUser = userRepository.findByName(request.getName());
        if (optionalUser.isEmpty()) {
            return null;
        }
        User user = optionalUser.get();
        boolean checkPassword = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!checkPassword) {
            return null;
        }

        String token = jwtService.generateToken(user);

        return new AuthResponse()
                .setToken(token);
    }

    public User extractUser(String token) {
        //TODO
        String username = jwtService.extractUsername(token);
        if (username == null) {
            return null;
        }

        return null;
    }
}
