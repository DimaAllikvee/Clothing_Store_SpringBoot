package org.example.services;

import org.example.model.Customer;
import org.example.model.Order;
import org.example.interfaces.OrderRepository;
import org.example.interfaces.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
    }

    @Transactional
    public boolean placeOrder(Long customerId, Order order) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);

        if (optionalCustomer.isEmpty()) {
            System.out.println("Ошибка: Клиент с ID " + customerId + " не найден.");
            return false;
        }

        Customer customer = optionalCustomer.get();
        order.setCustomer(customer);
        order.setOrderDate(java.time.LocalDateTime.now());
        orderRepository.save(order);

        System.out.println("Заказ успешно оформлен для клиента: " + customer.getFirstName() + " " + customer.getLastName());
        return true;
    }

    @Transactional(readOnly = true)
    public List<Order> getOrdersByCustomer(Long customerId) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isEmpty()) {
            System.out.println("Ошибка: Клиент с ID " + customerId + " не найден.");
            return new ArrayList<>();
        }
        return orderRepository.findByCustomer(optionalCustomer.get());
    }

    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Transactional
    public boolean removeOrder(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            System.out.println("Ошибка: Заказ с ID " + orderId + " не найден.");
            return false;
        }

        orderRepository.deleteById(orderId);
        System.out.println("Заказ с ID " + orderId + " успешно удален.");
        return true;
    }

    @Transactional(readOnly = true)
    public void printAllOrders() {
        List<Order> orders = getAllOrders();
        if (orders.isEmpty()) {
            System.out.println("Заказов нет.");
            return;
        }

        System.out.println("Все заказы:");
        for (Order order : orders) {
            System.out.printf("ID заказа: %d, Описание: %s, Цена: %.2f, Дата: %s, Клиент: %s%n",
                    order.getId(),
                    order.getDescription(),
                    order.getTotalPrice(),
                    order.getOrderDate(),
                    order.getCustomer().getFirstName() + " " + order.getCustomer().getLastName());
        }
    }

    @Transactional(readOnly = true)
    public void printOrdersByCustomer(Long customerId) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);

        if (optionalCustomer.isEmpty()) {
            System.out.println("Клиент с ID " + customerId + " не найден.");
            return;
        }

        List<Order> orders = getOrdersByCustomer(customerId);
        if (orders.isEmpty()) {
            System.out.println("У клиента с ID " + customerId + " нет заказов.");
            return;
        }

        System.out.println("Заказы клиента " + optionalCustomer.get().getFirstName() + " " + optionalCustomer.get().getLastName() + ":");
        for (Order order : orders) {
            System.out.printf("ID заказа: %d, Описание: %s, Цена: %.2f, Дата: %s%n",
                    order.getId(),
                    order.getDescription(),
                    order.getTotalPrice(),
                    order.getOrderDate());
        }
    }


    @Transactional(readOnly = true)
    public List<Map<String, Object>> getOrdersWithCustomerDetails(Long customerId) {
        List<Order> orders = getOrdersByCustomer(customerId);
        List<Map<String, Object>> result = new ArrayList<>();

        for (Order order : orders) {
            Map<String, Object> orderDetails = new HashMap<>();
            orderDetails.put("orderId", order.getId());
            orderDetails.put("description", order.getDescription());
            orderDetails.put("totalPrice", order.getTotalPrice());
            orderDetails.put("orderDate", order.getOrderDate());
            orderDetails.put("customerName", order.getCustomer().getFirstName() + " " + order.getCustomer().getLastName());
            result.add(orderDetails);
        }

        return result;
    }
}
