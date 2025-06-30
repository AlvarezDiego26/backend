package com.tecsup.pechera.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sensor_data")
public class SensorData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ecg_raw")
    private int ecgRaw;

    private Double temperatura;

    private String movimiento;

    private LocalDateTime fecha;

    @Column(name = "pet_id")
    private Long petId;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getEcgRaw() { return ecgRaw; }
    public void setEcgRaw(int ecgRaw) { this.ecgRaw = ecgRaw; }

    public Double getTemperatura() { return temperatura; }
    public void setTemperatura(Double temperatura) { this.temperatura = temperatura; }

    public String getMovimiento() { return movimiento; }
    public void setMovimiento(String movimiento) { this.movimiento = movimiento; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public Long getPetId() { return petId; }
    public void setPetId(Long petId) { this.petId = petId; }
}
