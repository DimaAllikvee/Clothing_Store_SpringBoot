package org.example;

import org.example.interfaces.Input;
import org.example.interfaces.Service;
import org.example.model.Clothes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App implements CommandLineRunner {

	@Autowired
	private Input input;

	@Autowired
	private Service<Clothes> clothingService;

	@Override
	public void run(String... args) throws Exception {
		System.out.println("------ Магазин верхней одежды с базой данных ------");
		boolean repeat = true;

		do {
			System.out.println("Список задач: ");
			System.out.println("0. Выход");
			System.out.println("1. Добавить одежду");
			System.out.println("2. Список одежды");
			System.out.println("3. Редактировать одежду");

			System.out.print("Введите номер задачи: ");
			int task = Integer.parseInt(input.getString());

			switch (task) {
				case 0:
					repeat = false;
					break;
				case 1:
					if (clothingService.add()){
						System.out.println("Одежда добавлена.");
					} else {
						System.out.println("Одежду добавить не удалось.");
					}

					break;
				case 2:
					clothingService.print();
					break;
				case 3:

			}
		} while (repeat);

		System.out.println("До свидания!");
	}

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
