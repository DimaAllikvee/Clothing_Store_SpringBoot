package org.example.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

public class Order implements Serializable {
    private String id; // Уникальный идентификатор заказа
    private Clothes clothes;
    private Customer customer;
    private LocalDate orderDate;

    // Конструктор
    public Order(Clothes clothes, Customer customer, LocalDate orderDate) {
        this.id = UUID.randomUUID().toString(); // Генерация уникального ID
        this.clothes = clothes;
        this.customer = customer;
        this.orderDate = orderDate;
    }

    // Конструктор без параметров
    public Order() {
        this.id = UUID.randomUUID().toString(); // Генерация уникального ID
    }

    // Геттеры и сеттеры
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Clothes getClothes() {
        return clothes;
    }

    public void setClothes(Clothes clothes) {
        this.clothes = clothes;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }
}
