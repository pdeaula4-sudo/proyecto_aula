package com.pqrs.system_pqrs.controller;

import com.pqrs.system_pqrs.dto.AuthResponse;
import com.pqrs.system_pqrs.dto.LoginRequest;
import com.pqrs.system_pqrs.dto.RegistroRequest;
import com.pqrs.system_pqrs.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest req) {
        return ResponseEntity.ok(authService.login(req));
    }

    @PostMapping("/registro")
    public ResponseEntity<AuthResponse> registro(@Valid @RequestBody RegistroRequest req) {
        return ResponseEntity.ok(authService.registro(req));
    }
}
