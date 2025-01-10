package ru.trading.indicators.trend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Deque;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MACDTest {
    private MACD macdIndicator;

    @BeforeEach
    public void setUp() {
        macdIndicator = new MACD(12, 26, 9); // Создаем MACD с периодами 12, 26 и 9
    }

    @Test
    public void testCalculateWithValidPrices() {
        List<Double> closingPrices = Arrays.asList(22.0, 23.0, 24.0, 25.0, 26.0);

        macdIndicator.calculate(closingPrices);

        assertNotEquals(0.0, macdIndicator.getValue(), "MACD should be calculated and not zero.");

        Deque<Double> history = macdIndicator.getMacdHistory();
        assertEquals(5, history.size(), "History should contain the last 5 MACD values.");
    }

    @Test
    public void testAddPrice() {
        // Добавляем первую цену
        macdIndicator.addPrice(22.0);

        // На данном этапе MACD может быть не определен, если недостаточно данных для расчета
        double initialMacd = macdIndicator.getValue();

        // Проверяем, что значение MACD не равно нулю, но оно может быть неопределенным (например, NaN)
        assertTrue(Double.isNaN(initialMacd) || initialMacd == 0.0, "MACD should be initially undefined or zero after one price.");

        // Добавляем больше цен и проверяем изменения в значении MACD
        macdIndicator.addPrice(23.0);

        double secondMacd = macdIndicator.getValue();

        // Теперь значение MACD должно измениться, так как мы добавили больше данных
        assertNotEquals(initialMacd, secondMacd, "MACD value should change after adding a new price.");

        // Добавляем еще одну цену и проверяем направление тренда
        macdIndicator.addPrice(24.0);

        assertNotEquals(0.0, macdIndicator.getValue(), "MACD should still be valid after multiple price additions.");
    }


    @Test
    public void testGetTrendDirectionUndefined() {
        // Проверяем направление тренда на начальном этапе
        assertEquals(TrendDirection.UNDEFINED, macdIndicator.getTrendDirection(), "Trend direction should be UNDEFINED initially.");

        // Добавляем одну цену и проверяем снова
        macdIndicator.addPrice(22.0);

        // После добавления одной цены направление должно оставаться UNDEFINED
        assertEquals(TrendDirection.UNDEFINED, macdIndicator.getTrendDirection(), "Trend direction should still be UNDEFINED with only one price.");

        // Добавляем еще одну цену и проверяем направление тренда
        macdIndicator.addPrice(23.0);

        // Теперь у нас есть два значения MACD, и направление должно быть определено
        assertTrue(macdIndicator.getTrendDirection() == TrendDirection.UP ||
                        macdIndicator.getTrendDirection() == TrendDirection.SIDEWAYS,
                "Trend direction should be UP or SIDEWAYS after adding two prices.");

        // Добавляем третью цену, чтобы проверить изменение направления
        macdIndicator.addPrice(22.0); // Это может вызвать изменение направления

        // Проверяем, что направление тренда теперь определено
        TrendDirection trendDirection = macdIndicator.getTrendDirection();
        assertTrue(trendDirection == TrendDirection.UP || trendDirection == TrendDirection.DOWN || trendDirection == TrendDirection.SIDEWAYS,
                "Trend direction should now be defined (UP, DOWN, or SIDEWAYS) after adding three prices.");
    }

}