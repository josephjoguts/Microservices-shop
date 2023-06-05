package com.me.controller;

import com.me.dto.AuthRequest;
import com.me.dto.AuthResponse;
import com.me.dto.RegisterRequest;
import com.me.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;

    @PostMapping("/api/v1/auth/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/v1/auth/auth")
    public ResponseEntity<AuthResponse> auth (@RequestBody AuthRequest request) {
        AuthResponse auth = authService.auth(request);
        if (auth == null) {
            log.info("Failed auth for: {}", request.getName());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        return ResponseEntity.ok(auth);
    }

    @PostMapping("/api/v1/auth/validate/{token}")
    public ResponseEntity<Boolean> validate(@PathVariable("token") String token) {
        authService.extractUser(token);
        return ResponseEntity.ok(null);
    }
}
