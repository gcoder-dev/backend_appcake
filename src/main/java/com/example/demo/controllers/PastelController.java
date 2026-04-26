package com.example.demo.controllers;

import com.example.demo.entities.Pastel;
import com.example.demo.services.PastelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/pasteles")
@RequiredArgsConstructor
public class PastelController {

    private final PastelService pastelService;

    @GetMapping
    public ResponseEntity<?> obtenerPasteles() {
        return ResponseEntity.ok(pastelService.obtenerTodos());
    }

    @PostMapping
    public ResponseEntity<?> crearPastel(
            @RequestParam("nombre") String nombre,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("precio") BigDecimal precio,
            @RequestParam(value = "imagenArchivo", required = false) MultipartFile imagenArchivo,
            @RequestParam(value = "imagenUrl", required = false) String imagenUrl
    ) {
        try {
            Pastel nuevoPastel = pastelService.guardarPastel(nombre, descripcion, precio, imagenArchivo, imagenUrl);
            return ResponseEntity.ok(nuevoPastel);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarPastel(
            @PathVariable Long id,
            @RequestParam("nombre") String nombre,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("precio") BigDecimal precio,
            @RequestParam(value = "imagenArchivo", required = false) MultipartFile imagenArchivo,
            @RequestParam(value = "imagenUrl", required = false) String imagenUrl
    ) {
        try {
            Pastel pastelActualizado = pastelService.actualizarPastel(id, nombre, descripcion, precio, imagenArchivo, imagenUrl);
            return ResponseEntity.ok(pastelActualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPastel(@PathVariable Long id) {
        try {
            pastelService.eliminarPastel(id);
            return ResponseEntity.ok(Map.of("ok", true));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "No se pudo eliminar el pastel"));
        }
    }
}