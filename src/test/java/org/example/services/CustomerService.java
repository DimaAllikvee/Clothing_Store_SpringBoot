package org.example.services;

import org.example.interfaces.AppHelper;
import org.example.interfaces.FileRepository;
import org.example.interfaces.Service;
import org.example.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;

@org.springframework.stereotype.Service
public class CustomerService implements Service<Customer> {

    private final AppHelper<Customer> customerAppHelper;
    private final FileRepository<Customer> storage;
    private final String fileName = "customers.dat";

    @Autowired
    public CustomerService(AppHelper<Customer> customerAppHelper, FileRepository<Customer> storage) {
        this.customerAppHelper = customerAppHelper;
        this.storage = storage;
    }

    @Override
    public boolean add() {
        Customer customer = customerAppHelper.create();
        if (customer != null) {
            storage.save(customer, fileName);
            System.out.println("Клиент успешно добавлен.");
            return true;
        }
        System.out.println("Ошибка при добавлении клиента.");
        return false;
    }

    @Override
    public boolean edit(Customer customer) {
        List<Customer> customers = storage.load(fileName);
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getFirstName().equals(customer.getFirstName()) &&
                    customers.get(i).getLastName().equals(customer.getLastName())) {
                System.out.println("Введите новые данные для клиента:");
                Customer updatedCustomer = customerAppHelper.create();
                if (updatedCustomer != null) {
                    customers.set(i, updatedCustomer);
                    storage.saveAll(customers, fileName);
                    System.out.println("Клиент успешно обновлён.");
                    return true;
                }
                System.out.println("Ошибка при обновлении клиента.");
                return false;
            }
        }
        System.out.println("Клиент не найден.");
        return false;
    }

    @Override
    public boolean remove(Customer customer) {
        List<Customer> customers = storage.load(fileName);
        boolean removed = customers.removeIf(c -> c.getFirstName().equals(customer.getFirstName()) &&
                c.getLastName().equals(customer.getLastName()));
        if (removed) {
            storage.saveAll(customers, fileName);
            System.out.println("Клиент успешно удалён.");
        } else {
            System.out.println("Клиент не найден для удаления.");
        }
        return removed;
    }

    @Override
    public boolean print() {
        List<Customer> customers = this.list();
        if (customers.isEmpty()) {
            System.out.println("Список клиентов пуст.");
            return false;
        }
        return customerAppHelper.printList(customers);
    }

    @Override
    public List<Customer> list() {
        List<Customer> customers = storage.load(fileName);
        if (customers == null) {
            System.out.println("Ошибка при загрузке списка клиентов.");
            return List.of();
        }
        return customers;
    }
}
