package org.example.apphelpers;

import org.example.interfaces.AppHelper;
import org.example.interfaces.Input;
import org.example.model.Customer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerAppHelper implements AppHelper<Customer>, Input {

    @Override
    public Customer create() {
        System.out.print("Имя клиента: ");
        String firstName = getString();
        System.out.print("Фамилия клиента: ");
        String lastName = getString();

        return new Customer(firstName, lastName);
    }

    @Override
    public boolean printList(List<Customer> customers) {
        if (customers == null || customers.isEmpty()) {
            System.out.println("Список клиентов пуст.");
            return false;
        }

        for (int i = 0; i < customers.size(); i++) {
            Customer customer = customers.get(i);
            System.out.printf("%d. %s %s%n", i + 1, customer.getFirstName(), customer.getLastName());
        }
        return true;
    }
}
