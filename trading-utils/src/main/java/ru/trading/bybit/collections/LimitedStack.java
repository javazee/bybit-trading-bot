package ru.trading.bybit.collections;

import java.util.Stack;

public class LimitedStack<T> extends Stack<T> {

    private final int size;

    public LimitedStack(final int size) {
        super();
        this.size = size;
    }

    @Override
    public T push(T item) {
        while (this.size() >= this.size) {
            this.remove(0);
        }
        return super.push(item);
    }
}
