package org.example.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "orders") // Указываем имя таблицы
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // Автоматическая генерация UUID
    private String id; // Уникальный идентификатор заказа

    @ManyToOne(fetch = FetchType.LAZY) // Связь "многие к одному" с Clothes
    @JoinColumn(name = "clothes_id", nullable = false)
    private Clothes clothes;

    @ManyToOne(fetch = FetchType.LAZY) // Связь "многие к одному" с Customer
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;

    // Конструктор
    public Order(Clothes clothes, Customer customer, LocalDate orderDate) {
        this.clothes = clothes;
        this.customer = customer;
        this.orderDate = orderDate;
    }

    // Конструктор без параметров
    public Order() {
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
