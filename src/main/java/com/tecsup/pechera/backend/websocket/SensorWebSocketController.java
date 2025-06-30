package com.tecsup.pechera.backend.websocket;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.tecsup.pechera.backend.model.SensorData;

@Controller
public class SensorWebSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    public SensorWebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendSensorData(SensorData data) {
        messagingTemplate.convertAndSend("/topic/sensores", data);
    }
}
