package ru.trading.indicators.trend;

import ru.trading.bybit.collections.LimitedStack;

import java.util.List;
import java.util.Stack;

public class SMA implements TrendIndicator {
    private final int period;
    private final Stack<Double> priceStack;

    private Double previousAverage;
    private Double average;

    public SMA(final int period) {
        this.period = period;
        this.priceStack = new LimitedStack<>(period);
        this.average = Double.NaN;
        this.previousAverage = Double.NaN;
    }

    @Override
    public void calculate(List<Double> prices) {
        if (prices.size() < this.period) {
            this.average = Double.NaN;
            this.previousAverage = Double.NaN;
            return;
        }
        double sum = 0;
        int startPeriod = prices.size() - this.period - 1;
        int endPeriod = prices.size() - 1;
        for (int i = startPeriod; i < endPeriod; i++) {
            sum += prices.get(i);
            this.priceStack.push(prices.get(i));
        }
        this.previousAverage = sum / this.period;
        double lastPrice = prices.get(endPeriod);
        this.priceStack.push(lastPrice);
        sum = sum - prices.get(startPeriod) + lastPrice;
        this.average = sum/this.period;
    }

    @Override
    public void addPrice(Double price) {
        this.priceStack.push(price);
        if (this.priceStack.size() < this.period) {
            return;
        }
        double sum = priceStack.stream().limit(period).mapToDouble(Double::doubleValue).sum();
        this.previousAverage = this.average;
        this.average = sum / this.period;
    }

    @Override
    public TrendDirection getTrendDirection() {
        if (Double.isNaN(this.average) || Double.isNaN(this.previousAverage)) {
            return TrendDirection.UNDEFINED;
        }
        if (this.average > this.previousAverage) {
            return TrendDirection.UP;
        } else if (this.average < this.previousAverage){
            return TrendDirection.DOWN;
        } else {
            return TrendDirection.SIDEWAYS;
        }
    }

    @Override
    public Double getValue() {
        return this.average;
    }
}
