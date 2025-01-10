package ru.trading.indicators.trend;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class SMATest {

    SMA sma = new SMA(3);

    @Test
    void testSMA() {
        List<Double> prices = List.of(5d, 10d, 15d, 20d);
        sma.calculate(prices);

        Assertions.assertEquals(TrendDirection.UP, sma.getTrendDirection());
        Assertions.assertEquals(15, sma.getValue());

        sma.addPrice(4d);
        Assertions.assertEquals(13d, sma.getValue());
        Assertions.assertEquals(TrendDirection.DOWN, sma.getTrendDirection());

        sma.addPrice(6d);
        Assertions.assertEquals(10d, sma.getValue());
        Assertions.assertEquals(TrendDirection.DOWN, sma.getTrendDirection());

        sma.addPrice(35d);
        Assertions.assertEquals(15d, sma.getValue());
        Assertions.assertEquals(TrendDirection.UP, sma.getTrendDirection());

        sma.calculate(prices);
        Assertions.assertEquals(TrendDirection.UP, sma.getTrendDirection());
        Assertions.assertEquals(15, sma.getValue());

        sma.addPrice(10d);
        Assertions.assertEquals(TrendDirection.SIDEWAYS, sma.getTrendDirection());
        Assertions.assertEquals(15, sma.getValue());

        sma.calculate(List.of(5d, 10d));
        Assertions.assertTrue(Double.isNaN(sma.getValue()));
        Assertions.assertEquals(TrendDirection.UNDEFINED, sma.getTrendDirection());
    }
}
