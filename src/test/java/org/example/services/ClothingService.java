package org.example.services;

import org.example.apphelpers.ClothingAppHelper;
import org.example.interfaces.ClothesRepository;
import org.example.interfaces.Service;
import org.example.model.Clothes;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;

@org.springframework.stereotype.Service
public class ClothingService implements Service<Clothes> {

    private final ClothingAppHelper clothingAppHelper;
    private final ClothesRepository clothesRepository;

    @Autowired
    public ClothingService(ClothingAppHelper clothingAppHelper, ClothesRepository clothesRepository) {
        this.clothingAppHelper = clothingAppHelper;
        this.clothesRepository = clothesRepository;
    }

    @Override
    public boolean add() {
        Clothes clothes = clothingAppHelper.create();
        if (clothes != null) {
            clothesRepository.save(clothes);
            System.out.println("Одежда добавлена: " + clothes);
            return true;
        }
        System.out.println("Ошибка: не удалось добавить одежду.");
        return false;
    }

    @Override
    public boolean add(Clothes clothes) {
        if (clothes != null) {
            clothesRepository.save(clothes);
            System.out.println("Одежда добавлена через REST API: " + clothes);
            return true;
        }
        System.out.println("Ошибка: не удалось добавить одежду через REST API.");
        return false;
    }

    @Override
    public boolean edit(Clothes clothes) {
        return false;
    }

    @Override
    public boolean remove(Long id) {
        return false;
    }

    @Override
    public boolean print() {
        List<Clothes> clothesList = clothesRepository.findAll();
        return clothingAppHelper.printList(clothesList);
    }

    @Override
    public List<Clothes> list() {
        return clothesRepository.findAll();
    }
}
