package org.example.services;

import jakarta.transaction.Transactional;
import org.example.apphelpers.CustomerAppHelper;
import org.example.interfaces.AppHelper;
import org.example.interfaces.ClothesRepository;
import org.example.interfaces.Service;
import org.example.interfaces.CustomerRepository;
import org.example.model.Clothes;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;


@org.springframework.stereotype.Service
public class ClothingService implements Service<Clothes> {

    @Autowired
    private AppHelper<Clothes> clothesAppHelper;

    @Autowired
    private CustomerAppHelper customerAppHelper;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ClothesRepository clothesRepository;

    @Transactional
    @Override
    public boolean add(Clothes clothes) {
        try {
            clothesRepository.save(clothes);
            System.out.println("Одежда добавлена: " + clothes);
            return true;
        } catch (Exception e) {
            System.err.println("Ошибка при добавлении одежды: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean edit(Clothes clothes) {
        if (clothesRepository.existsById(clothes.getId())) {
            clothesRepository.save(clothes);
            return true;
        }
        System.out.println("Одежда с ID " + clothes.getId() + " не найдена.");
        return false;
    }

    @Override
    public boolean remove(Long id) {
        if (clothesRepository.existsById(id)) {
            clothesRepository.deleteById(id);
            return true;
        }
        System.out.println("Одежда с ID " + id + " не найдена.");
        return false;
    }

    @Override
    public boolean print() {
        List<Clothes> clothesList = list();
        if (clothesList.isEmpty()) {
            System.out.println("Список одежды пуст.");
            return false;
        }
        clothesList.forEach(System.out::println);
        return true;
    }

    @Override
    public List<Clothes> list() {
        return clothesRepository.findAll();
    }

}

