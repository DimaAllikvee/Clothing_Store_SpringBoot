package Test;

import org.example.model.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerAppHelperTest {

    private CustomerAppHelper spyHelper;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {

        spyHelper = Mockito.spy(new CustomerAppHelper());
        System.setOut(new PrintStream(outContent));
    }

    /**
     * Тест для метода create() с корректными данными.
     */
    @Test
    void testCreateCustomerSuccessfully() {

        doReturn("John", "Snow").when(spyHelper).getString();


        Customer customer = spyHelper.create();


        assertNotNull(customer);
        assertEquals("John", customer.getFirstName());
        assertEquals("Snow", customer.getLastName());
    }

    @Test
    void testPrintListWithCustomers() {
        // Создаем тестовые данные
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("John", "Snow"));
        customers.add(new Customer("Jane", "Smith"));

        // Вызываем метод printList()
        spyHelper.printList(customers);


        String expectedOutput1 = "1. John Snow";
        String expectedOutput2 = "2. Jane Smith";

        assertTrue(outContent.toString().contains(expectedOutput1));
        assertTrue(outContent.toString().contains(expectedOutput2));
    }

    /**
     * Тест для метода printList() с пустым списком клиентов.
     */
    @Test
    void testPrintListEmpty() {

        List<Customer> emptyList = new ArrayList<>();


        boolean result = spyHelper.printList(emptyList);

        assertTrue(result);
        assertTrue(outContent.toString().isEmpty());
    }

    @AfterEach
    public void tearDown() {

        System.setOut(originalOut);
        outContent.reset();
    }
}
