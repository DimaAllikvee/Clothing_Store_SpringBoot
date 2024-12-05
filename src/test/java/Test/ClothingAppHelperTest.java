package Test;

import org.example.apphelpers.ClothingAppHelper;
import org.example.model.Clothes;
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

class ClothingAppHelperTest {

    private ClothingAppHelper clothingAppHelper;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        clothingAppHelper = Mockito.spy(new ClothingAppHelper());
        System.setOut(new PrintStream(outContent));
    }

    /**
     * Тест для метода create() с корректными данными.
     */
    @Test
    void testCreateClothesSuccessfully() {

        doReturn("Nike", "Футболка", "M", "Черный", "29.99").when(clothingAppHelper).getString();

        Clothes clothes = clothingAppHelper.create();


        assertNotNull(clothes);
        assertEquals("Nike", clothes.getName());
        assertEquals("Футболка", clothes.getType());
        assertEquals("M", clothes.getSize());
        assertEquals("Черный", clothes.getColor());
        assertEquals(29.99, clothes.getPrice());
    }

    /**
     * Тест для метода create() при ошибке ввода.
     */
    @Test
    void testCreateClothesFailure() {

        doThrow(new RuntimeException("Ошибка ввода")).when(clothingAppHelper).getString();

        Clothes clothes = clothingAppHelper.create();

        assertNull(clothes);
    }

    /**
     * Тест для метода printList() с непустым списком одежды.
     */
    @Test
    void testPrintListWithClothes() {

        List<Clothes> clothesList = new ArrayList<>();
        clothesList.add(new Clothes("Nike", "Футболка", "M", "Черный", 29.99));
        clothesList.add(new Clothes("Adidas", "Куртка", "L", "Белый", 79.99));


        clothingAppHelper.printList(clothesList);


        String expectedOutput1 = "1. Название: Nike, Тип: Футболка, Размер: M, Цвет: Черный, Цена: $29.99";
        String expectedOutput2 = "2. Название: Adidas, Тип: Куртка, Размер: L, Цвет: Белый, Цена: $79.99";

        assertTrue(outContent.toString().contains(expectedOutput1));
        assertTrue(outContent.toString().contains(expectedOutput2));
    }

    /**
     * Тест для метода printList() с пустым списком одежды.
     */
    @Test
    void testPrintListEmpty() {

        List<Clothes> emptyList = new ArrayList<>();


        boolean result = clothingAppHelper.printList(emptyList);

        assertFalse(result);
        assertTrue(outContent.toString().contains("Список одежды пуст."));
    }

    @AfterEach
    public void tearDown() {

        System.setOut(originalOut);
        outContent.reset();
    }
}
