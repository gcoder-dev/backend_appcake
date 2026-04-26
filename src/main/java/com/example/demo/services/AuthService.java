package com.example.demo.services;

import com.example.demo.dtos.AuthRequestDTO;
import com.example.demo.dtos.AuthResponseDTO;
import com.example.demo.entities.Admin;
import com.example.demo.repositories.AdminRepository;
import com.example.demo.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    // Inyectamos lo necesario para el registro temporal
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthResponseDTO login(AuthRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsuario(), request.getPassword())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsuario());
        String token = jwtService.generateToken(userDetails);

        return new AuthResponseDTO(true, token);
    }

    // --- ENDPOINT TEMPORAL ---
    public Map<String, String> registrarAdminTemporal(AuthRequestDTO request) {
        if (adminRepository.findByUsuario(request.getUsuario()).isPresent()) {
            throw new RuntimeException("El usuario ya existe");
        }

        Admin nuevoAdmin = new Admin();
        nuevoAdmin.setUsuario(request.getUsuario());
        // Aquí Spring Security hace la magia de encriptar
        nuevoAdmin.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        nuevoAdmin.setActivo(true);

        adminRepository.save(nuevoAdmin);

        return Map.of("mensaje", "Admin registrado exitosamente. ¡Ya puedes borrar este endpoint!");
    }
}