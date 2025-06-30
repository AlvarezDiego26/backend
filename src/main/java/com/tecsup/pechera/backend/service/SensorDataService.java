package com.tecsup.pechera.backend.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.tecsup.pechera.backend.model.SensorData;
import com.tecsup.pechera.backend.repository.SensorDataRepository;
import com.tecsup.pechera.backend.websocket.SensorWebSocketController;
import java.util.List;

@Service
public class SensorDataService {

    private final SensorDataRepository repository;
    private final SensorWebSocketController webSocketController;
    private Long lastProcessedId = 0L;

    public SensorDataService(SensorDataRepository repository, SensorWebSocketController webSocketController) {
        this.repository = repository;
        this.webSocketController = webSocketController;
    }

    @Scheduled(fixedRate = 1000)
    public void checkNewSensorData() {
        List<SensorData> nuevos = repository.findByIdGreaterThanOrderByIdAsc(lastProcessedId);

        for (SensorData data : nuevos) {
            webSocketController.sendSensorData(data);
            lastProcessedId = data.getId();
        }
    }
}
