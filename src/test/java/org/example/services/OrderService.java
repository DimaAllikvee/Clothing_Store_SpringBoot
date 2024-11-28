package org.example.services;

import org.example.interfaces.AppHelper;
import org.example.interfaces.FileRepository;
import org.example.interfaces.Service;
import org.example.model.Clothes;
import org.example.model.Customer;
import org.example.model.Order;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;

@org.springframework.stereotype.Service
public class OrderService implements Service<Order> {

    private final AppHelper<Order> orderAppHelper;
    private final Service<Clothes> clothingService;
    private final Service<Customer> customerService;
    private final FileRepository<Order> storage;
    private final String fileName = "orders.dat";

    @Autowired
    public OrderService(AppHelper<Order> orderAppHelper, Service<Clothes> clothingService, Service<Customer> customerService, FileRepository<Order> storage) {
        this.orderAppHelper = orderAppHelper;
        this.clothingService = clothingService;
        this.customerService = customerService;
        this.storage = storage;
    }

    @Override
    public boolean add() {
        Order order = orderAppHelper.create();
        if (order != null) {
            storage.save(order, fileName);
            System.out.println("Заказ успешно добавлен.");
            return true;
        }
        System.out.println("Ошибка при создании заказа.");
        return false;
    }

    @Override
    public boolean edit(Order order) {
        System.out.println("Редактирование заказов не поддерживается.");
        return false;
    }

    @Override
    public boolean remove(Order order) {
        List<Order> orders = storage.load(fileName);
        boolean removed = orders.removeIf(o -> o.getId().equals(order.getId()));
        if (removed) {
            storage.saveAll(orders, fileName);
            System.out.println("Заказ успешно удалён.");
            return true;
        }
        System.out.println("Заказ не найден для удаления.");
        return false;
    }

    @Override
    public boolean print() {
        List<Order> orders = this.list();
        if (orders.isEmpty()) {
            System.out.println("Список заказов пуст.");
            return false;
        }
        return orderAppHelper.printList(orders);
    }

    @Override
    public List<Order> list() {
        List<Order> orders = storage.load(fileName);
        if (orders == null) {
            System.out.println("Ошибка при загрузке списка заказов.");
            return List.of();
        }
        return orders;
    }
}
