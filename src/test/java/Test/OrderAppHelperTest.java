package Test;

import org.example.apphelpers.OrderAppHelper;
import org.example.interfaces.Service;
import org.example.model.Clothes;
import org.example.model.Customer;
import org.example.model.Order;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderAppHelperTest {

    private OrderAppHelper orderAppHelper;

    @Mock
    private Service<Clothes> clothingServiceMock;

    @Mock
    private Service<Customer> customerServiceMock;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        orderAppHelper = Mockito.spy(new OrderAppHelper(clothingServiceMock, customerServiceMock));
        System.setOut(new PrintStream(outContent));
    }

    /**
     * Тест для метода create() с корректными данными.
     */
    @Test
    void testCreateOrderSuccessfully() {

        List<Clothes> clothesList = List.of(new Clothes("Nike", "Футболка", "M", "Черный", 29.99));
        List<Customer> customerList = List.of(new Customer("John", "Doe"));


        when(clothingServiceMock.list()).thenReturn(clothesList);
        when(customerServiceMock.list()).thenReturn(customerList);
        when(clothingServiceMock.print()).thenReturn(true);
        when(customerServiceMock.print()).thenReturn(true);


        doReturn("1", "1").when(orderAppHelper).getString();


        Order order = orderAppHelper.create();


        assertNotNull(order);
        assertEquals(clothesList.get(0), order.getClothes());
        assertEquals(customerList.get(0), order.getCustomer());
        assertEquals(LocalDate.now(), order.getOrderDate());
    }


    /**
     * Тест для метода create() при ошибке ввода.
     */
    @Test
    void testCreateOrderFailure() {
        doThrow(new RuntimeException("Ошибка ввода")).when(orderAppHelper).getString();

        Order order = orderAppHelper.create();

        assertNull(order);
    }


    @Test
    void testPrintListWithOrders() {
        // Создаем тестовые данные
        Clothes clothes = new Clothes("Nike", "Футболка", "M", "Черный", 29.99);
        Customer customer = new Customer("John", "Doe");
        Order order = new Order(clothes, customer, LocalDate.now());

        List<Order> orders = List.of(order);


        orderAppHelper.printList(orders);


        String expectedOutput = String.format("1. Клиент: John Doe, Одежда: Nike, Тип: Футболка, Размер: M, Цвет: Черный, Дата заказа: %s",
                LocalDate.now());
        assertTrue(outContent.toString().contains(expectedOutput));
    }

    /**
     * Тест для метода printList() с пустым списком заказов.
     */
    @Test
    void testPrintListEmpty() {
        List<Order> emptyList = new ArrayList<>();

        boolean result = orderAppHelper.printList(emptyList);

        assertFalse(result);
        assertTrue(outContent.toString().contains("Заказы отсутствуют."));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
        outContent.reset();
    }
}
