package org.example.controllers;

import org.example.model.Customer;
import org.example.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Добавление нового клиента
    @PostMapping
    public String addCustomer(@RequestBody Customer customer) {
        customerService.add(customer); // Используем метод add с параметром
        return "Клиент успешно добавлен: " + customer;
    }

    // Получение всех клиентов
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.list();
    }

    // Получение клиента по ID
    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.findById(id);
        if (customer != null) {
            return customer;
        } else {
            throw new RuntimeException("Клиент с ID " + id + " не найден.");
        }
    }

    // Редактирование клиента
    @PutMapping("/{id}")
    public String updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        customer.setId(id); // Устанавливаем ID клиента из пути
        if (customerService.edit(customer)) {
            return "Клиент с ID " + id + " успешно обновлён.";
        } else {
            return "Не удалось обновить клиента с ID " + id + ".";
        }
    }

    // Удаление клиента
    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        if (customerService.remove(id)) {
            return "Клиент с ID " + id + " успешно удалён.";
        } else {
            return "Не удалось удалить клиента с ID " + id + ".";
        }
    }
}
