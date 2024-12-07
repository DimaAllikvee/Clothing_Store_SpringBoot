package org.example.controllers;

import org.example.model.Order;
import org.example.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Оформление заказа на клиента
    @PostMapping("/customer/{customerId}")
    public String placeOrder(@PathVariable Long customerId, @RequestBody Order order) {
        if (orderService.placeOrder(customerId, order)) {
            return "Заказ успешно оформлен для клиента с ID " + customerId;
        } else {
            return "Ошибка: Заказ не удалось оформить.";
        }
    }

    // Получение всех заказов клиента
    @GetMapping("/customer/{customerId}")
    public List<Order> getOrdersByCustomer(@PathVariable Long customerId) {
        return orderService.getOrdersByCustomer(customerId);
    }

    // Получение всех заказов с информацией о клиентах
    @GetMapping("/with-customer-details")
    public List<Map<String, Object>> getOrdersWithCustomerDetails(@RequestParam Long customerId) {
        return orderService.getOrdersWithCustomerDetails(customerId);
    }
}
