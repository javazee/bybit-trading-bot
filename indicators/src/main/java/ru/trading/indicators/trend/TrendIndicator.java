package ru.trading.indicators.trend;

import ru.trading.indicators.Indicator;

import java.util.List;

public interface TrendIndicator extends Indicator {

    void calculate(List<Double> prices);

    TrendDirection getTrendDirection();
}
