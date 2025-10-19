package com.example.azs.repository;

import com.example.azs.model.FuelType;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class FuelTypeRepository {
    private List<FuelType> fuelTypes = new ArrayList<>();

    public void addFuelType(FuelType fuelType) {
        fuelTypes.add(fuelType);
    }

    public List<FuelType> getAllFuelTypes() {
        return new ArrayList<>(fuelTypes);
    }

    public Optional<FuelType> findById(Long id) {
        return fuelTypes.stream()
                .filter(fuelType -> fuelType.getId().equals(id))
                .findFirst();
    }

    public void updateFuelType(FuelType updatedFuelType) {
        for (int i = 0; i < fuelTypes.size(); i++) {
            if (fuelTypes.get(i).getId().equals(updatedFuelType.getId())) {
                fuelTypes.set(i, updatedFuelType);
                break;
            }
        }
    }

    public void deleteFuelType(Long id) {
        fuelTypes.removeIf(fuelType -> fuelType.getId().equals(id));
    }
}