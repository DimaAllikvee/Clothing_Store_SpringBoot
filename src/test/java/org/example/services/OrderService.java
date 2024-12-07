package org.example.services;

import org.example.model.Customer;
import org.example.model.Order;
import org.example.interfaces.OrderRepository;
import org.example.interfaces.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
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

    /**
     * Оформление заказа для клиента.
     *
     * @param customerId  ID клиента
     * @param description Описание заказа
     * @param totalPrice  Общая стоимость заказа
     * @return true, если заказ успешно оформлен
     */
    @Transactional
    public boolean placeOrder(Long customerId, Order order) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);

        if (optionalCustomer.isEmpty()) {
            System.out.println("Ошибка: Клиент с ID " + customerId + " не найден.");
            return false;
        }

        Customer customer = optionalCustomer.get();

        // Устанавливаем связь между клиентом и заказом
        order.setCustomer(customer);
        order.setOrderDate(java.time.LocalDateTime.now()); // Устанавливаем текущую дату и время заказа

        // Сохраняем заказ
        orderRepository.save(order);

        System.out.println("Заказ успешно оформлен для клиента: " + customer.getFirstName() + " " + customer.getLastName());
        return true;
    }


    /**
     * Получение всех заказов клиента.
     *
     * @param customerId ID клиента
     * @return список заказов клиента
     */
    @Transactional(readOnly = true)
    public List<Order> getOrdersByCustomer(Long customerId) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);

        if (optionalCustomer.isEmpty()) {
            System.out.println("Ошибка: Клиент с ID " + customerId + " не найден.");
            return Collections.emptyList();
        }

        return orderRepository.findByCustomer(optionalCustomer.get());
    }

    /**
     * Получение всех заказов.
     *
     * @return список всех заказов
     */
    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    /**
     * Удаление заказа.
     *
     * @param orderId ID заказа
     * @return true, если заказ успешно удален
     */
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
}
