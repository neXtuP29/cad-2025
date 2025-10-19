package com.example.azs.service;

import com.example.azs.model.FuelStation;
import com.example.azs.repository.FuelStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuelStationService {
    private final FuelStationRepository stationRepository;

    @Autowired
    public FuelStationService(FuelStationRepository stationRepository) {
        this.stationRepository = stationRepository;
        initializeSampleData();
    }

    private void initializeSampleData() {
        // Простая инициализация без сложных проверок
        if (stationRepository.getAllStations().isEmpty()) {
            stationRepository.addStation(new FuelStation("АЗС №1", "ул. Центральная, 123", "+7 (495) 123-45-67"));
            stationRepository.addStation(new FuelStation("АЗС №2", "пр. Ленина, 45", "+7 (495) 765-43-21"));
        }
    }

    public void addStation(FuelStation station) {
        stationRepository.addStation(station);
    }

    public List<FuelStation> getAllStations() {
        return stationRepository.getAllStations();
    }

    public Optional<FuelStation> getStationById(Long id) {
        return stationRepository.findById(id);
    }

    public void updateStation(FuelStation station) {
        stationRepository.updateStation(station);
    }

    public void deleteStation(Long id) {
        stationRepository.deleteStation(id);
    }
}