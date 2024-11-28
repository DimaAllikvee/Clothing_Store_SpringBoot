package org.example.model;

import java.io.Serializable;

public class Clothes implements Serializable {
    private String name;
    private String type;
    private String size;
    private String color;
    private double price;

    // Конструктор без параметров
    public Clothes() {}

    // Конструктор с пятью параметрами
    public Clothes(String name, String type, String size, String color, double price) {
        this.name = name;
        this.type = type;
        this.size = size;
        this.color = color;
        this.price = price;
    }

    // Геттеры и сеттеры
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    @Override
    public String toString() {
        return String.format("Название: %s, Тип: %s, Размер: %s, Цвет: %s, Цена: $%.2f",
                name, type, size, color, price);
    }
}
