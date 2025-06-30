package com.tecsup.pechera.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import com.tecsup.pechera.backend.model.SensorData;
import com.tecsup.pechera.backend.repository.SensorDataRepository;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/sensores")
public class SensorDataController {

    @Autowired
    private SensorDataRepository repository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostMapping
    public SensorData recibirDatos(@RequestBody SensorData data) {
        data.setFecha(LocalDateTime.now());
        SensorData saved = repository.save(data);

        // Emitir por WebSocket a Kotlin
        messagingTemplate.convertAndSend("/topic/sensores", saved);

        return saved;
    }
}