package com.example.azs.controller;

import com.example.azs.model.FuelType;
import com.example.azs.service.FuelTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class FuelTypeController {

    private final FuelTypeService fuelTypeService;

    @Autowired
    public FuelTypeController(FuelTypeService fuelTypeService) {
        this.fuelTypeService = fuelTypeService;
    }

    @GetMapping("/fuel-types")
    public String showFuelTypes(Model model) {
        model.addAttribute("fuelTypes", fuelTypeService.getAllFuelTypes());
        model.addAttribute("newFuelType", new FuelType());
        return "fuel-types";
    }

    @PostMapping("/fuel-types/add")
    public String addFuelType(@ModelAttribute FuelType fuelType) {
        // Убедимся, что ID генерируется правильно
        if (fuelType.getId() == null) {
            // Создаем новый объект с автогенерацией ID
            FuelType newFuelType = new FuelType(fuelType.getName(), fuelType.getDescription(), fuelType.getPrice());
            fuelTypeService.addFuelType(newFuelType);
        } else {
            fuelTypeService.addFuelType(fuelType);
        }
        return "redirect:/fuel-types";
    }

    @PostMapping("/fuel-types/update/{id}")
    public String updateFuelType(@PathVariable Long id,
                                 @RequestParam String name,
                                 @RequestParam String description,
                                 @RequestParam Double price) {
        // Находим существующий тип топлива и обновляем его
        fuelTypeService.getFuelTypeById(id).ifPresent(existingFuelType -> {
            existingFuelType.setName(name);
            existingFuelType.setDescription(description);
            existingFuelType.setPrice(price);
            fuelTypeService.updateFuelType(existingFuelType);
        });
        return "redirect:/fuel-types";
    }

    @GetMapping("/fuel-types/delete/{id}")
    public String deleteFuelType(@PathVariable Long id) {
        fuelTypeService.deleteFuelType(id);
        return "redirect:/fuel-types";
    }
}