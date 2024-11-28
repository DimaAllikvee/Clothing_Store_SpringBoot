package Test;

import org.example.interfaces.AppHelper;
import org.example.interfaces.FileRepository;
import org.example.model.Clothes;
import org.example.services.ClothingService;
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

class ClothingServiceTest {

    @Mock
    private AppHelper<Clothes> clothingAppHelper;

    @Mock
    private FileRepository<Clothes> storage;

    @InjectMocks
    private ClothingService clothingService;

    private final String fileName = "clothes.dat";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Тест для успешного добавления одежды.
     */
    @Test
    void testAddClothesSuccessfully() {
        Clothes clothes = new Clothes("Nike", "Футболка", "M", "Черный", 29.99);
        when(clothingAppHelper.create()).thenReturn(clothes);
        doNothing().when(storage).save(clothes, fileName);

        boolean result = clothingService.add();

        assertTrue(result);
        verify(clothingAppHelper, times(1)).create();
        verify(storage, times(1)).save(clothes, fileName);
    }

    /**
     * Тест для случая, когда создание одежды возвращает null.
     */
    @Test
    void testAddClothesFailure() {
        when(clothingAppHelper.create()).thenReturn(null);

        boolean result = clothingService.add();

        assertFalse(result);
        verify(clothingAppHelper, times(1)).create();
        verify(storage, never()).save(any(), eq(fileName));
    }

    /**
     * Тест для успешного редактирования одежды.
     */
    @Test
    void testEditClothesSuccessfully() {
        Clothes existingClothes = new Clothes("Nike", "Футболка", "M", "Черный", 29.99);
        Clothes updatedClothes = new Clothes("Nike", "Футболка", "L", "Белый", 35.99);
        List<Clothes> clothesList = new ArrayList<>(List.of(existingClothes));

        when(storage.load(fileName)).thenReturn(clothesList);
        when(clothingAppHelper.create()).thenReturn(updatedClothes);
        doNothing().when(storage).saveAll(clothesList, fileName);

        boolean result = clothingService.edit(existingClothes);

        assertTrue(result);
        verify(storage, times(1)).load(fileName);
        verify(clothingAppHelper, times(1)).create();
        verify(storage, times(1)).saveAll(clothesList, fileName);
    }


    /**
     * Тест для случая, когда редактируемая одежда не найдена.
     */
    @Test
    void testEditClothesNotFound() {
        Clothes clothes = new Clothes("Nike", "Футболка", "M", "Черный", 29.99);
        List<Clothes> clothesList = Collections.emptyList();

        when(storage.load(fileName)).thenReturn(clothesList);

        boolean result = clothingService.edit(clothes);

        assertFalse(result);
        verify(storage, times(1)).load(fileName);
        verify(clothingAppHelper, never()).create();
        verify(storage, never()).saveAll(any(), eq(fileName));
    }

    /**
     * Тест для удаления одежды.
     */
    @Test
    void testRemoveClothesSuccessfully() {
        Clothes clothes = new Clothes("Nike", "Футболка", "M", "Черный", 29.99);
        List<Clothes> clothesList = new ArrayList<>(List.of(clothes));

        when(storage.load(fileName)).thenReturn(clothesList);
        doNothing().when(storage).saveAll(anyList(), eq(fileName));

        boolean result = clothingService.remove(clothes);

        assertTrue(result);
        verify(storage, times(1)).load(fileName);
        verify(storage, times(1)).saveAll(anyList(), eq(fileName));
    }


    /**
     * Тест для случая, когда удаляемая одежда не найдена.
     */
    @Test
    void testRemoveClothesNotFound() {
        Clothes clothes = new Clothes("Nike", "Футболка", "M", "Черный", 29.99);
        List<Clothes> clothesList = Collections.emptyList();

        when(storage.load(fileName)).thenReturn(clothesList);

        boolean result = clothingService.remove(clothes);

        assertFalse(result);
        verify(storage, times(1)).load(fileName);
        verify(storage, never()).saveAll(anyList(), eq(fileName));
    }

    /**
     * Тест для метода list().
     */
    @Test
    void testListClothes() {
        Clothes clothes = new Clothes("Nike", "Футболка", "M", "Черный", 29.99);
        List<Clothes> clothesList = List.of(clothes);

        when(storage.load(fileName)).thenReturn(clothesList);

        List<Clothes> result = clothingService.list();

        assertEquals(clothesList, result);
        verify(storage, times(1)).load(fileName);
    }
}
