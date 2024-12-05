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

			System.out.print("Введите номер задачи: ");
			int task = Integer.parseInt(input.getString());

			switch (task) {
				case 0:
					repeat = false;
					break;
				case 1:
					System.out.print("Введите название одежды: ");
					String name = input.getString();
					System.out.print("Введите тип одежды: ");
					String type = input.getString();
					System.out.print("Введите размер одежды: ");
					String size = input.getString();
					System.out.print("Введите цвет одежды: ");
					String color = input.getString();
					System.out.print("Введите цену одежды: ");
					double price = Double.parseDouble(input.getString());

					Clothes clothes = new Clothes(name, type, size, color, price);
					if (clothingService.add(clothes)) {
						System.out.println("Одежда добавлена: " + clothes);
					} else {
						System.out.println("Одежду добавить не удалось.");
					}
					break;
				default:
					System.out.println("Выбрана задача не из списка!");
					break;
			}
		} while (repeat);

		System.out.println("До свидания!");
	}

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
