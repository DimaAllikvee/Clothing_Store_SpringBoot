package org.example.controllers;

import org.example.model.Clothes;
import org.example.services.ClothingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clothes")
public class ClothesController {

    private final ClothingService clothingService;

    @Autowired
    public ClothesController(ClothingService clothingService) {
        this.clothingService = clothingService;
    }

    @PostMapping
    public String addClothes(@RequestBody Clothes clothes) {
        clothingService.add(clothes); // Используем метод add с параметром
        return "Одежда успешно добавлена: " + clothes;
    }

    @GetMapping
    public List<Clothes> getAllClothes() {
        return clothingService.list();
    }
}
