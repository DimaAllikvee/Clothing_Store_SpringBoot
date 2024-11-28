package org.example.apphelpers;

import org.example.interfaces.AppHelper;
import org.example.interfaces.Input;
import org.example.interfaces.Service;
import org.example.model.Clothes;
import org.example.model.Customer;
import org.example.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class OrderAppHelper implements AppHelper<Order>, Input {

    private final Service<Clothes> clothingService;
    private final Service<Customer> customerService;

    @Autowired
    public OrderAppHelper(Service<Clothes> clothingService, Service<Customer> customerService) {
        this.clothingService = clothingService;
        this.customerService = customerService;
    }

    /**
     * Метод для создания заказа.
     */
    @Override
    public Order create() {
        try {
            // Вывод списка одежды
            clothingService.print();
            System.out.print("Выберите номер одежды: ");
            int clothingIndex = Integer.parseInt(getString()) - 1;
            Clothes selectedClothes = clothingService.list().get(clothingIndex);

            // Вывод списка клиентов
            customerService.print();
            System.out.print("Выберите номер клиента: ");
            int customerIndex = Integer.parseInt(getString()) - 1;
            Customer selectedCustomer = customerService.list().get(customerIndex);

            // Создание заказа
            Order order = new Order(selectedClothes, selectedCustomer, LocalDate.now());
            System.out.println("Заказ успешно создан.");
            return order;
        } catch (Exception e) {
            System.out.println("Ошибка создания заказа: " + e.getMessage());
            return null;
        }
    }

    /**
     * Метод для отображения списка заказов.
     */
    @Override
    public boolean printList(List<Order> orders) {
        if (orders == null || orders.isEmpty()) {
            System.out.println("Заказы отсутствуют.");
            return false;
        }

        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            System.out.printf("%d. Клиент: %s %s, Одежда: %s, Тип: %s, Размер: %s, Цвет: %s, Дата заказа: %s%n",
                    i + 1,
                    order.getCustomer().getFirstName(), order.getCustomer().getLastName(),
                    order.getClothes().getName(),
                    order.getClothes().getType(),
                    order.getClothes().getSize(),
                    order.getClothes().getColor(),
                    order.getOrderDate());
        }
        return true;
    }
}
