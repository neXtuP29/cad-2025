package com.example.azs.repository;

import com.example.azs.model.FuelStation;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class FuelStationRepository {
    private List<FuelStation> stations = new ArrayList<>();

    public void addStation(FuelStation station) {
        stations.add(station);
    }

    public List<FuelStation> getAllStations() {
        return new ArrayList<>(stations);
    }

    public Optional<FuelStation> findById(Long id) {
        return stations.stream()
                .filter(station -> station.getId().equals(id))
                .findFirst();
    }

    public void updateStation(FuelStation updatedStation) {
        for (int i = 0; i < stations.size(); i++) {
            if (stations.get(i).getId().equals(updatedStation.getId())) {
                stations.set(i, updatedStation);
                break;
            }
        }
    }

    public void deleteStation(Long id) {
        stations.removeIf(station -> station.getId().equals(id));
    }
}