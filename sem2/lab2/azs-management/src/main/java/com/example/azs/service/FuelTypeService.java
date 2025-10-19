package com.example.azs.service;

import com.example.azs.model.FuelType;
import com.example.azs.repository.FuelTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuelTypeService {
    private final FuelTypeRepository fuelTypeRepository;

    @Autowired
    public FuelTypeService(FuelTypeRepository fuelTypeRepository) {
        this.fuelTypeRepository = fuelTypeRepository;
        initializeSampleData();
    }

    private void initializeSampleData() {
        if (fuelTypeRepository.getAllFuelTypes().isEmpty()) {
            fuelTypeRepository.addFuelType(new FuelType("АИ-95", "Бензин АИ-95", 52.50));
            fuelTypeRepository.addFuelType(new FuelType("АИ-98", "Бензин АИ-98 Premium", 58.75));
            fuelTypeRepository.addFuelType(new FuelType("ДТ", "Дизельное топливо", 55.30));
        }
    }

    public void addFuelType(FuelType fuelType) {
        fuelTypeRepository.addFuelType(fuelType);
    }

    public List<FuelType> getAllFuelTypes() {
        return fuelTypeRepository.getAllFuelTypes();
    }

    public Optional<FuelType> getFuelTypeById(Long id) {
        return fuelTypeRepository.findById(id);
    }

    public void updateFuelType(FuelType fuelType) {
        fuelTypeRepository.updateFuelType(fuelType);
    }

    public void deleteFuelType(Long id) {
        fuelTypeRepository.deleteFuelType(id);
    }
}