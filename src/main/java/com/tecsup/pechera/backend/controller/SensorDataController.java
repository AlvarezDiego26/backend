package com.tecsup.pechera.backend.controller;

import com.tecsup.pechera.backend.model.SensorData;
import com.tecsup.pechera.backend.repository.SensorDataRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sensores")
public class SensorDataController {

    private final SensorDataRepository repository;

    public SensorDataController(SensorDataRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<SensorData> getAll() {
        return repository.findAll();
    }
}
