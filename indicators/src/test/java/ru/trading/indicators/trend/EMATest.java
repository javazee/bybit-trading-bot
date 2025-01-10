package ru.trading.indicators.trend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EMATest {
    private EMA emaIndicator;

    @BeforeEach
    public void setUp() {
        emaIndicator = new EMA(10); // Создаем EMA с периодом 10
    }

    @Test
    public void testCalculateWithValidPrices() {
        // Данные для теста
        List<Double> closingPrices = Arrays.asList(22.0, 23.0, 24.0, 25.0, 26.0);

        // Рассчитываем EMA
        emaIndicator.calculate(closingPrices);

        // Проверяем текущее значение EMA
        assertNotEquals(0.0, emaIndicator.getValue(), "EMA should be calculated and not zero.");
    }

    @Test
    public void testAddPrice() {
        emaIndicator.addPrice(22.0);
        emaIndicator.addPrice(23.0);

        double currentEma = emaIndicator.getValue();

        assertNotEquals(0.0, currentEma, "EMA should be calculated after adding prices.");

        // Проверяем направление тренда после добавления цены
        assertEquals(TrendDirection.UP, emaIndicator.getTrendDirection(), "Trend direction should be UP.");

        // Добавляем больше цен и проверяем направление тренда
        emaIndicator.addPrice(21.0);
        assertEquals(TrendDirection.DOWN, emaIndicator.getTrendDirection(), "Trend direction should be DOWN.");

        emaIndicator.addPrice(22.0);
        assertEquals(TrendDirection.UP, emaIndicator.getTrendDirection(), "Trend direction should be UP again.");
    }

    @Test
    public void testGetTrendDirectionUndefined() {
        // Тренд не может быть определен без достаточного количества данных
        assertEquals(TrendDirection.UNDEFINED, emaIndicator.getTrendDirection(), "Trend direction should be UNDEFINED initially.");

        // Добавляем одну цену и проверяем снова
        emaIndicator.addPrice(22.0);
        assertEquals(TrendDirection.UNDEFINED, emaIndicator.getTrendDirection(), "Trend direction should still be UNDEFINED with only one price.");

        // Добавляем еще одну цену и проверяем направление тренда
        emaIndicator.addPrice(23.0);
        assertEquals(TrendDirection.UP, emaIndicator.getTrendDirection(), "Trend direction should now be UP after adding two prices.");
    }

    @Test
    public void testCalculateWithEmptyList() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            emaIndicator.calculate(Arrays.asList());
        });

        String expectedMessage = "Prices list cannot be null or empty";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage), "Should throw an exception for empty price list.");
    }

    @Test
    public void testAddNullPrice() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            emaIndicator.addPrice(null);
        });

        String expectedMessage = "Price cannot be null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage), "Should throw an exception for null price.");
    }
}

