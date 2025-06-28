package com.tecsup.pechera.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "datos_sensores")
public class SensorData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer ecg_raw;
    private Double temperatura;
    private String movimiento;

    @Column(name = "fecha", columnDefinition = "TIMESTAMP")
    private LocalDateTime fecha;

    // Getters y setters
}
