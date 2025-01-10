package ru.trading.indicators.trend;

import ru.trading.bybit.collections.LimitedStack;

import java.util.List;

import java.util.Stack;

public class EMA implements TrendIndicator {
    private Double ema; // Текущее значение EMA
    private Double previousEma;
    private final double multiplier; // Множитель для EMA// Период EMA
    private final Stack<Double> emaHistory; // Очередь для хранения последних n значений EMA
    private boolean isInitialized; // Флаг, указывающий, инициализирован ли индикатор

    public EMA(int period) {
        if (period <= 0) {
            throw new IllegalArgumentException("Period must be greater than zero");
        }
        this.multiplier = 2.0 / (period + 1);
        this.isInitialized = false;
        this.ema = Double.NaN;
        this.previousEma = Double.NaN;
        this.emaHistory = new LimitedStack<>(period);
    }

    @Override
    public void calculate(List<Double> prices) {
        if (prices == null || prices.isEmpty()) {
            throw new IllegalArgumentException("Prices list cannot be null or empty");
        }

        for (Double price : prices) {
            addPrice(price);
        }
    }

    @Override
    public void addPrice(Double price) {
        if (price == null) {
            throw new IllegalArgumentException("Price cannot be null");
        }

        if (!isInitialized) {
            // Инициализируем EMA первым значением цены
            ema = price;
            isInitialized = true;
        } else {
            // Рассчитываем новое значение EMA
            this.previousEma = ema;
            this.ema = ((price - this.ema) * multiplier) + this.ema;
        }

        emaHistory.push(ema); // Добавляем текущее значение EMA в историю
    }

    @Override
    public TrendDirection getTrendDirection() {
        if (Double.isNaN(this.ema) || Double.isNaN(this.previousEma)) {
            return TrendDirection.UNDEFINED;
        }
        if (this.ema > this.previousEma) {
            return TrendDirection.UP;
        } else if (this.ema < this.previousEma){
            return TrendDirection.DOWN;
        } else {
            return TrendDirection.SIDEWAYS;
        }
    }

    @Override
    public Double getValue() {
        return this.ema;
    }
}

