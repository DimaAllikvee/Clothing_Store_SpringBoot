package org.example.services;

import org.example.apphelpers.CustomerAppHelper;
import org.example.interfaces.CustomerRepository;
import org.example.interfaces.Service;
import org.example.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
public class CustomerService implements Service<Customer> {

    private final CustomerAppHelper customerAppHelper;
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerAppHelper customerAppHelper, CustomerRepository customerRepository) {
        this.customerAppHelper = customerAppHelper;
        this.customerRepository = customerRepository;
    }

    @Override
    public boolean add() {
        Customer customer = customerAppHelper.create();
        if (customer != null) {
            customerRepository.save(customer);
            System.out.println("Клиент добавлен: " + customer);
            return true;
        }
        System.out.println("Ошибка: не удалось добавить клиента.");
        return false;
    }

    @Override
    public boolean add(Customer customer) {
        if (customer != null) {
            customerRepository.save(customer);
            System.out.println("Клиент добавлен через REST API: " + customer);
            return true;
        }
        System.out.println("Ошибка: не удалось добавить клиента через REST API.");
        return false;
    }

    @Override
    public boolean edit(Customer customer) {
        if (customer == null || customer.getId() == null) {
            System.out.println("Ошибка: Указанный клиент или ID отсутствует.");
            return false;
        }

        return customerRepository.findById(customer.getId()).map(existingCustomer -> {
            try {
                // Обновляем поля существующего клиента
                System.out.println("Редактирование клиента:");
                System.out.print("Введите новое имя (текущее: " + existingCustomer.getFirstName() + "): ");
                String newFirstName = customerAppHelper.getInput().getString();
                existingCustomer.setFirstName(newFirstName.isEmpty() ? existingCustomer.getFirstName() : newFirstName);

                System.out.print("Введите новую фамилию (текущая: " + existingCustomer.getLastName() + "): ");
                String newLastName = customerAppHelper.getInput().getString();
                existingCustomer.setLastName(newLastName.isEmpty() ? existingCustomer.getLastName() : newLastName);

                // Сохраняем обновлённого клиента
                customerRepository.save(existingCustomer);
                System.out.println("Клиент успешно обновлён: " + existingCustomer);
                return true;
            } catch (Exception e) {
                System.out.println("Ошибка при редактировании клиента: " + e.getMessage());
                return false;
            }
        }).orElseGet(() -> {
            System.out.println("Ошибка: Клиент с ID " + customer.getId() + " не найден.");
            return false;
        });
    }

    @Override
    public boolean remove(Long id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            System.out.println("Клиент с ID " + id + " успешно удалён.");
            return true;
        } else {
            System.out.println("Ошибка: Клиент с ID " + id + " не найден.");
            return false;
        }
    }

    @Override
    public Customer findById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public boolean print() {
        List<Customer> customerList = customerRepository.findAll();
        return customerAppHelper.printList(customerList);
    }

    @Override
    public List<Customer> list() {
        return customerRepository.findAll();
    }
}
