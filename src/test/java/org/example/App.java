package org.example;

import org.example.interfaces.Input;
import org.example.interfaces.Service;
import org.example.model.Clothes;
import org.example.model.Customer;
import org.example.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App implements CommandLineRunner {

	 private final Input input;
	 private final Service<Clothes> clothingService;
	 private final Service<Customer> customerService;
	 private final Service<Order> orderService;

	@Autowired
	public App(@Qualifier("clothingAppHelper") Input input, // Указываем конкретную реализацию Input
			   Service<Clothes> clothingService,
			   Service<Customer> customerService,
			   Service<Order> orderService) {
		this.input = input;
		this.clothingService = clothingService;
		this.customerService = customerService;
		this.orderService = orderService;
	}

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Override
	public void run(String... args) {
		boolean repeat = true;
		do {
			System.out.println("------ Магазин верхней одежды ------");
			System.out.println("Список задач: ");
			System.out.println("0. Выход");
			System.out.println("1. Добавить одежду");
			System.out.println("2. Список одежды");
			System.out.println("3. Редактировать одежду");
			System.out.println("4. Удалить одежду");
			System.out.println("5. Добавить клиента");
			System.out.println("6. Список клиентов");
			System.out.println("7. Редактировать клиента");
			System.out.println("8. Удалить клиента");
			System.out.println("9. Оформить заказ");
			System.out.println("10. Список заказов");

			System.out.print("Введите номер задачи: ");
			int task = Integer.parseInt(input.getString());
			switch (task) {
				case 0:
					repeat = false;
					break;
				case 1:
					if (clothingService.add()) {
						System.out.println("Одежда добавлена");
					} else {
						System.out.println("Одежду добавить не удалось");
					}
					break;
				case 2:
					clothingService.print();
					break;
				case 3:
					System.out.print("Введите название одежды для редактирования: ");
					String clothingName = input.getString();
					Clothes clothesToEdit = new Clothes(clothingName, "", "", "", 0.0);
					if (clothingService.edit(clothesToEdit)) {
						System.out.println("Одежда успешно отредактирована");
					} else {
						System.out.println("Одежда не найдена");
					}
					break;
				case 4:
					System.out.print("Введите название одежды для удаления: ");
					String clothingNameToDelete = input.getString();
					Clothes clothesToDelete = new Clothes(clothingNameToDelete, "", "", "", 0.0);
					if (clothingService.remove(clothesToDelete)) {
						System.out.println("Одежда успешно удалена");
					} else {
						System.out.println("Одежда не найдена");
					}
					break;
				case 5:
					if (customerService.add()) {
						System.out.println("Клиент добавлен");
					} else {
						System.out.println("Клиента добавить не удалось");
					}
					break;
				case 6:
					customerService.print();
					break;
				case 7:
					System.out.print("Введите имя клиента для редактирования: ");
					String customerFirstName = input.getString();
					System.out.print("Введите фамилию клиента для редактирования: ");
					String customerLastName = input.getString();
					Customer customerToEdit = new Customer(customerFirstName, customerLastName);
					if (customerService.edit(customerToEdit)) {
						System.out.println("Клиент успешно отредактирован");
					} else {
						System.out.println("Клиент не найден");
					}
					break;
				case 8:
					System.out.print("Введите имя клиента для удаления: ");
					String customerFirstNameToDelete = input.getString();
					System.out.print("Введите фамилию клиента для удаления: ");
					String customerLastNameToDelete = input.getString();
					Customer customerToDelete = new Customer(customerFirstNameToDelete, customerLastNameToDelete);
					if (customerService.remove(customerToDelete)) {
						System.out.println("Клиент успешно удален");
					} else {
						System.out.println("Клиент не найден");
					}
					break;
				case 9:
					if (orderService.add()) {
						System.out.println("Заказ оформлен");
					} else {
						System.out.println("Не удалось оформить заказ");
					}
					break;
				case 10:
					orderService.print();
					break;
				default:
					System.out.println("Выбрана задача не из списка!");
					break;
			}
		} while (repeat);
		System.out.println("До свидания!");
	}
}
