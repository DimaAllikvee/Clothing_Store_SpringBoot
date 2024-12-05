package Test;

import org.example.interfaces.AppHelper;
import org.example.interfaces.FileRepository;
import org.example.model.Customer;
import org.example.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    @Mock
    private AppHelper<Customer> customerAppHelper;

    @Mock
    private FileRepository<Customer> storage;

    @InjectMocks
    private CustomerService customerService;

    private final String fileName = "customers.dat";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Тест для добавления клиента.
     */
    @Test
    void testAddCustomerSuccessfully() {
        Customer customer = new Customer("John", "Doe");
        when(customerAppHelper.create()).thenReturn(customer);
        doNothing().when(storage).save(customer, fileName);

        boolean result = customerService.add();

        assertTrue(result);
        verify(customerAppHelper, times(1)).create();
        verify(storage, times(1)).save(customer, fileName);
    }

    /**
     * Тест для случая, когда создание клиента возвращает null.
     */
    @Test
    void testAddCustomerFailure() {
        when(customerAppHelper.create()).thenReturn(null);

        boolean result = customerService.add();

        assertFalse(result);
        verify(customerAppHelper, times(1)).create();
        verify(storage, never()).save(any(), eq(fileName));
    }

    /**
     * Тест на редактирования клиента.
     */
    @Test
    void testEditCustomerSuccessfully() {
        Customer existingCustomer = new Customer("John", "Doe");
        Customer updatedCustomer = new Customer("John", "Doe Updated");
        List<Customer> customers = new ArrayList<>(List.of(existingCustomer));

        when(storage.load(fileName)).thenReturn(customers);
        when(customerAppHelper.create()).thenReturn(updatedCustomer);
        doNothing().when(storage).saveAll(customers, fileName);

        boolean result = customerService.edit(existingCustomer);

        assertTrue(result);
        verify(storage, times(1)).load(fileName);
        verify(customerAppHelper, times(1)).create();
        verify(storage, times(1)).saveAll(customers, fileName);
    }

    /**
     * Тест для случая, когда редактируемый клиент не найден.
     */
    @Test
    void testEditCustomerNotFound() {
        Customer customer = new Customer("John", "Doe");
        List<Customer> customers = Collections.emptyList();

        when(storage.load(fileName)).thenReturn(customers);

        boolean result = customerService.edit(customer);

        assertFalse(result);
        verify(storage, times(1)).load(fileName);
        verify(customerAppHelper, never()).create();
        verify(storage, never()).saveAll(anyList(), eq(fileName));
    }

    /**
     * Тест для удаления клиента.
     */
    @Test
    void testRemoveCustomerSuccessfully() {
        Customer customer = new Customer("John", "Doe");
        List<Customer> customers = new ArrayList<>(List.of(customer));

        when(storage.load(fileName)).thenReturn(customers);
        doNothing().when(storage).saveAll(anyList(), eq(fileName));

        boolean result = customerService.remove(customer);

        assertTrue(result);
        verify(storage, times(1)).load(fileName);
        verify(storage, times(1)).saveAll(anyList(), eq(fileName));
    }

    /**
     * Тест для случая, когда удаляемый клиент не найден.
     */
    @Test
    void testRemoveCustomerNotFound() {
        Customer customer = new Customer("John", "Doe");
        List<Customer> customers = Collections.emptyList();

        when(storage.load(fileName)).thenReturn(customers);

        boolean result = customerService.remove(customer);

        assertFalse(result);
        verify(storage, times(1)).load(fileName);
        verify(storage, never()).saveAll(anyList(), eq(fileName));
    }

    /**
     * Тест для метода list().
     */
    @Test
    void testListCustomers() {
        Customer customer = new Customer("John", "Doe");
        List<Customer> customers = List.of(customer);

        when(storage.load(fileName)).thenReturn(customers);

        List<Customer> result = customerService.list();

        assertEquals(customers, result);
        verify(storage, times(1)).load(fileName);
    }
}
