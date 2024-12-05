package org.example.apphelpers;

import org.example.interfaces.AppHelper;
import org.example.interfaces.Input;
import org.example.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerAppHelper implements AppHelper<Customer> {

    private final Input input;

    @Autowired
    public CustomerAppHelper(Input input) {
        this.input = input;
    }

    /**
     * Метод для создания клиента.
     */
    @Override
    public Customer create() {
        try {
            System.out.print("Введите имя клиента: ");
            String firstName = input.getString();
            System.out.print("Введите фамилию клиента: ");
            String lastName = input.getString();

            // Создаём и возвращаем объект Customer
            return new Customer(firstName, lastName);
        } catch (Exception e) {
            System.out.println("Ошибка при создании клиента: " + e.getMessage());
            return null;
        }
    }

    /**
     * Метод для отображения списка клиентов.
     */
    @Override
    public boolean printList(List<Customer> customerList) {
        if (customerList == null || customerList.isEmpty()) {
            System.out.println("Список клиентов пуст.");
            return false;
        }

        for (int i = 0; i < customerList.size(); i++) {
            Customer customer = customerList.get(i);
            System.out.printf("%d. Имя: %s, Фамилия: %s%n",
                    i + 1,
                    customer.getFirstName(),
                    customer.getLastName());
        }
        return true;
    }
}
