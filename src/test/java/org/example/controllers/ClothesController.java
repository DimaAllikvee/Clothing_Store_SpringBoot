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

    // Добавление новой одежды в базу данных
    @PostMapping
    public String addClothes(@RequestBody Clothes clothes) {
        clothingService.add(clothes); // Передаём объект `clothes` в сервис
        return "Одежда успешно добавлена: " + clothes;
    }

    // Получение всех записей из базы данных
    @GetMapping
    public List<Clothes> getAllClothes() {
        return clothingService.list();
    }
}
