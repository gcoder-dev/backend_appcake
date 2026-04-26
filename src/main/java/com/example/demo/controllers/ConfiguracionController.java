package com.example.demo.controllers;

import com.example.demo.dtos.ConfiguracionDTO;
import com.example.demo.services.ConfiguracionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/config")
@RequiredArgsConstructor
public class ConfiguracionController {

    private final ConfiguracionService configuracionService;

    @GetMapping
    public ResponseEntity<ConfiguracionDTO> getConfig() {
        return ResponseEntity.ok(configuracionService.obtenerConfiguracion());
    }
}