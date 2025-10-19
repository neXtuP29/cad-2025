package com.example.azs.controller;

import com.example.azs.model.FuelStation;
import com.example.azs.service.FuelStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class FuelStationController {

    private final FuelStationService stationService;

    @Autowired
    public FuelStationController(FuelStationService stationService) {
        this.stationService = stationService;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<FuelStation> stations = stationService.getAllStations();

        // Вычисляем количество активных АЗС в контроллере
        long activeCount = stations.stream()
                .filter(FuelStation::isActive)
                .count();

        model.addAttribute("stations", stations);
        model.addAttribute("newStation", new FuelStation());
        model.addAttribute("activeCount", activeCount); // Добавляем готовое значение
        model.addAttribute("totalCount", stations.size()); // Общее количество

        return "index";
    }

    @PostMapping("/stations/add")
    public String addStation(@ModelAttribute FuelStation station) {
        stationService.addStation(station);
        return "redirect:/";
    }

    @GetMapping("/stations/delete/{id}")
    public String deleteStation(@PathVariable Long id) {
        stationService.deleteStation(id);
        return "redirect:/";
    }

    @GetMapping("/stations/toggle/{id}")
    public String toggleStation(@PathVariable Long id) {
        stationService.getStationById(id).ifPresent(station -> {
            station.setActive(!station.isActive());
            stationService.updateStation(station);
        });
        return "redirect:/";
    }
}