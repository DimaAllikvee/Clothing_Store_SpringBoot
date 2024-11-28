package Test;

import org.example.interfaces.AppHelper;
import org.example.interfaces.FileRepository;
import org.example.interfaces.Service;
import org.example.model.Clothes;
import org.example.model.Customer;
import org.example.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private AppHelper<Order> orderAppHelper;

    @Mock
    private Service<Clothes> clothingService;

    @Mock
    private Service<Customer> customerService;

    @Mock
    private FileRepository<Order> storage;

    @InjectMocks
    private OrderService orderService;

    private final String fileName = "orders.dat";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Тест для успешного добавления заказа.
     */
    @Test
    void testAddOrderSuccessfully() {
        Order order = new Order();
        when(orderAppHelper.create()).thenReturn(order);
        doNothing().when(storage).save(order, fileName);

        boolean result = orderService.add();

        assertTrue(result);
        verify(orderAppHelper, times(1)).create();
        verify(storage, times(1)).save(order, fileName);
    }

    /**
     * Тест для случая, когда создание заказа возвращает null.
     */
    @Test
    void testAddOrderFailure() {
        when(orderAppHelper.create()).thenReturn(null);

        boolean result = orderService.add();

        assertFalse(result);
        verify(orderAppHelper, times(1)).create();
        verify(storage, never()).save(any(), eq(fileName));
    }

    /**
     * Тест для метода list().
     */
    @Test
    void testListOrders() {
        Order order = new Order();
        List<Order> orders = List.of(order);

        when(storage.load(fileName)).thenReturn(orders);

        List<Order> result = orderService.list();

        assertEquals(orders, result);
        verify(storage, times(1)).load(fileName);
    }

    /**
     * Тест для метода print().
     */
    @Test
    void testPrintOrders() {
        when(orderAppHelper.printList(anyList())).thenReturn(true);

        boolean result = orderService.print();

        assertTrue(result);
        verify(orderAppHelper, times(1)).printList(anyList());
    }

    /**
     * Тест для метода print() при отсутствии заказов.
     */
    @Test
    void testPrintOrdersEmptyList() {
        when(storage.load(fileName)).thenReturn(Collections.emptyList());
        when(orderAppHelper.printList(Collections.emptyList())).thenReturn(false);

        boolean result = orderService.print();

        assertFalse(result);
        verify(orderAppHelper, times(1)).printList(Collections.emptyList());
    }
}
