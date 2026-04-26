package com.example.demo.services;

import com.example.demo.entities.Pastel;
import com.example.demo.repositories.PastelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PastelService {

    private final PastelRepository pastelRepository;
    private final FileStorageService fileStorageService;

    public List<Pastel> obtenerTodos() {
        return pastelRepository.findAll();
    }

    public Pastel guardarPastel(String nombre, String descripcion, BigDecimal precio, MultipartFile imagenArchivo, String imagenUrl) {
        Pastel pastel = new Pastel();
        pastel.setNombre(nombre);
        pastel.setDescripcion(descripcion);
        pastel.setPrecio(precio);

        // Si mandan un archivo, lo guardamos y sacamos la URL
        if (imagenArchivo != null && !imagenArchivo.isEmpty()) {
            String rutaImagen = fileStorageService.guardarImagen(imagenArchivo);
            pastel.setImagen(rutaImagen);
        } else if (imagenUrl != null && !imagenUrl.isEmpty()) {
            // Si no hay archivo pero sí una URL en texto (para retrocompatibilidad)
            pastel.setImagen(imagenUrl);
        } else {
            throw new RuntimeException("Se requiere una imagen (archivo o URL)");
        }

        return pastelRepository.save(pastel);
    }

    public Pastel actualizarPastel(Long id, String nombre, String descripcion, BigDecimal precio, MultipartFile imagenArchivo, String imagenUrl) {
        Pastel pastel = pastelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pastel no encontrado"));

        pastel.setNombre(nombre);
        pastel.setDescripcion(descripcion);
        pastel.setPrecio(precio);

        if (imagenArchivo != null && !imagenArchivo.isEmpty()) {
            String rutaImagen = fileStorageService.guardarImagen(imagenArchivo);
            pastel.setImagen(rutaImagen);
        } else if (imagenUrl != null && !imagenUrl.isEmpty()) {
            pastel.setImagen(imagenUrl);
        }

        return pastelRepository.save(pastel);
    }

    public void eliminarPastel(Long id) {
        pastelRepository.deleteById(id);
    }
}