package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "configuracion")
@Data
public class Configuracion {
    @Id
    private Integer id; // Usamos Integer en lugar de Long porque es TINYINT en tu script

    @Column(nullable = false, length = 20)
    private String telefono;

    @Column(name = "negocio_nombre", length = 120)
    private String negocioNombre = "AppCake";

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}