package com.tecsup.pechera.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import com.tecsup.pechera.backend.model.SensorData;
import com.tecsup.pechera.backend.repository.SensorDataRepository;

import java.time.LocalDateTime;

/**
 * SensorDataController
 *
 * Controlador REST para manejar los datos de los sensores.
 * Recibe información de los sensores y la retransmite a través de WebSockets.
 *
 * @author VitalPaw
 * @version 1.0
 * @since 2025-06-23
 */
@RestController // Marca esta clase como un controlador REST.
@RequestMapping("/api/sensores") // Ruta base para todos los endpoints de este controlador.
public class SensorDataController {

    @Autowired 
    private SensorDataRepository repository;

    @Autowired 
    private SimpMessagingTemplate messagingTemplate;

    /**
     * Recibe y procesa datos de sensores.
     *
     * Guarda los datos recibidos en la base de datos y luego los envía
     * a todos los clientes suscritos al tema WebSocket `/topic/sensores`.
     *
     * @param data Los datos del sensor enviados en el cuerpo de la petición.
     * @return Los datos del sensor guardados.
     */
    @PostMapping // Mapea peticiones POST a /api/sensores.
    public SensorData recibirDatos(@RequestBody SensorData data) {
        data.setFecha(LocalDateTime.now()); // Establece la fecha y hora actual al dato recibido.
        SensorData saved = repository.save(data); // Guarda los datos en la base de datos.

        // Envía los datos guardados a través de WebSocket a todos los clientes suscritos a /topic/sensores.
        messagingTemplate.convertAndSend("/topic/sensores", saved);

        return saved; // Retorna los datos que fueron guardados.
    }
}