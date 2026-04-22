package com.pqrs.system_pqrs.controller;

import com.pqrs.system_pqrs.dto.LoginRequest;
import com.pqrs.system_pqrs.entity.Miembro;
import com.pqrs.system_pqrs.service.MiembroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private MiembroService miembroService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<Miembro> miembroOpt = miembroService.findByCorreo(loginRequest.getCorreo());

        if (miembroOpt.isPresent()) {
            Miembro miembro = miembroOpt.get();
            if (miembro.getPasswordMiembro().equals(loginRequest.getPassword())) {
                return ResponseEntity.ok(miembro);
            }
        }
        return ResponseEntity.badRequest().body("Credenciales inválidas");
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registro(@RequestBody Miembro miembro) {
        if (miembroService.existsByCorreo(miembro.getCorreoMiembro())) {
            return ResponseEntity.badRequest().body("El correo ya está registrado");
        }

        miembro.setRolIdRol(1L); // Rol por defecto: Residente
        Miembro nuevoMiembro = miembroService.save(miembro);
        return ResponseEntity.ok(nuevoMiembro);
    }
}