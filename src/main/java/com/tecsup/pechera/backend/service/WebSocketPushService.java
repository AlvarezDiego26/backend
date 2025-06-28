package com.tecsup.pechera.backend.service;

import com.tecsup.pechera.backend.repository.SensorDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class WebSocketPushService {

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private SensorDataRepository repository;

    @Scheduled(fixedRate = 5000)
    public void sendData() {
        var ultimos = repository.findAll(); // Puedes filtrar por fecha o ID
        template.convertAndSend("/topic/sensores", ultimos);
    }
}
