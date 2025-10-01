package com.clinic.backend.presentation.controller;

import com.clinic.backend.application.auth.AuthResponse;
import com.clinic.backend.application.auth.AuthService;
import com.clinic.backend.application.auth.LoginRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        // In a stateless JWT setup, logout is typically handled client-side by removing the token
        // For audit logging, you could add the logout event here
        return ResponseEntity.ok("Logged out successfully");
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Authentication service is running");
    }
}
