package com.example.demo.services;

import com.example.demo.dtos.ConfiguracionDTO;
import com.example.demo.entities.Configuracion;
import com.example.demo.repositories.ConfiguracionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfiguracionService {

    private final ConfiguracionRepository configuracionRepository;

    public ConfiguracionDTO obtenerConfiguracion() {
        // En tu script SQL insertaste la configuración con el ID 1
        Configuracion config = configuracionRepository.findById(1)
                .orElseThrow(() -> new RuntimeException("Configuración del negocio no encontrada"));

        ConfiguracionDTO dto = new ConfiguracionDTO();
        dto.setTelefono(config.getTelefono());

        return dto;
    }
}