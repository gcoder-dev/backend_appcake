package com.example.demo.controllers;

import com.example.demo.dtos.AuthRequestDTO;
import com.example.demo.dtos.SessionResponseDTO;
import com.example.demo.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDTO request) {
        try {
            return ResponseEntity.ok(authService.login(request));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("error", "Credenciales incorrectas"));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // Con JWT en el backend no se destruye la sesión como tal,
        // el frontend simplemente debe borrar el token de su LocalStorage/Cookies.
        return ResponseEntity.ok(Map.of("ok", true));
    }

    @GetMapping("/session")
    public ResponseEntity<SessionResponseDTO> checkSession() {
        // Si llega aquí, es porque el token JWT pasó el filtro de seguridad
        return ResponseEntity.ok(new SessionResponseDTO(true));
    }

//
//    // --- ENDPOINT TEMPORAL ---
//    @PostMapping("/register-temp")
//    public ResponseEntity<?> registerTemp(@RequestBody AuthRequestDTO request) {
//        try {
//            return ResponseEntity.ok(authService.registrarAdminTemporal(request));
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
//        }
//    }
}