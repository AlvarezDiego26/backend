package com.tecsup.pechera.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tecsup.pechera.backend.model.SensorData;

import java.util.List;

public interface SensorDataRepository extends JpaRepository<SensorData, Long> {
    List<SensorData> findByIdGreaterThanOrderByIdAsc(Long id);
}
