package ru.trading.indicators.trend;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class MACD implements TrendIndicator {
    private final EMA emaFast; // Быстрая EMA
    private final EMA emaSlow; // Медленная EMA
    private final int fastPeriod; // Период быстрой EMA
    private final int slowPeriod; // Период медленной EMA
    private final int signalPeriod; // Период сигнальной линии
    private final Deque<Double> macdHistory; // История значений MACD
    private double macd; // Текущее значение MACD
    private double signalLine; // Текущее значение сигнальной линии
    private boolean isInitialized; // Флаг, указывающий, инициализирован ли индикатор

    public MACD(int fastPeriod, int slowPeriod, int signalPeriod) {
        if (fastPeriod <= 0 || slowPeriod <= 0 || signalPeriod <= 0) {
            throw new IllegalArgumentException("Periods must be greater than zero");
        }
        this.fastPeriod = fastPeriod;
        this.slowPeriod = slowPeriod;
        this.signalPeriod = signalPeriod;
        this.emaFast = new EMA(fastPeriod);
        this.emaSlow = new EMA(slowPeriod);
        this.macdHistory = new ArrayDeque<>(signalPeriod);
        this.isInitialized = false;
        this.macd = Double.NaN;
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

        // Обновляем значения EMA для быстрой и медленной EMA
        emaFast.addPrice(price);
        emaSlow.addPrice(price);

        // Рассчитываем текущее значение MACD
        macd = emaFast.getValue() - emaSlow.getValue();

        // Обновляем сигнальную линию, если достаточно данных
        if (macdHistory.size() < signalPeriod) {
            macdHistory.addLast(macd);
            signalLine = macd; // Инициализируем сигнальную линию первым значением MACD
        } else {
            // Рассчитываем сигнальную линию как EMA от значений MACD
            signalLine = ((macd - signalLine) * (2.0 / (signalPeriod + 1))) + signalLine;
            macdHistory.pollFirst(); // Удаляем старейшее значение из истории
            macdHistory.addLast(macd); // Добавляем текущее значение MACD в историю
        }

        isInitialized = true;
    }

    public double getSignalLine() {
        return signalLine;
    }

    public Deque<Double> getMacdHistory() {
        return new ArrayDeque<>(macdHistory); // Возвращаем копию истории значений MACD
    }

    public TrendDirection getTrendDirection() {
        if (macdHistory.size() < 2) {
            return TrendDirection.UNDEFINED; // Недостаточно данных для определения тренда
        }

        double previousMacd = macdHistory.peekFirst(); // Предыдущее значение MACD

        if (macd > signalLine && previousMacd <= signalLine) {
            return TrendDirection.UP; // Тренд восходящий при пересечении вверх
        } else if (macd < signalLine && previousMacd >= signalLine) {
            return TrendDirection.DOWN; // Тренд нисходящий при пересечении вниз
        } else {
            return TrendDirection.SIDEWAYS; // Тренд боковой, если нет пересечения
        }
    }

    @Override
    public Double getValue() {
        return macd;
    }
}

