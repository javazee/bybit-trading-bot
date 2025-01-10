package ru.trading.indicators.trend;

import ru.trading.bybit.collections.LimitedStack;

import java.util.List;
import java.util.Stack;

public class EMA implements TrendIndicator {
    private final int period;

    private double value;
    private Stack<Double> history;

    public EMA(int period) {
        this.period = period;
        this.value = 0;
        this.history = new LimitedStack<>(period);
    }

    @Override
    public void calculate(final List<Double> prices) {
        if (prices.size() <= this.period) {
            return;
        }
    }

    public void update(final Double newPrice) {

    }

    @Override
    public TrendDirection getTrendDirection() {
        return null;
    }

    @Override
    public Double getValue() {
        return 0.0;
    }
}
