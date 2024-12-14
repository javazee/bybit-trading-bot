package ru.trading.bybit.symbols;

public enum Symbol {

    BTC_USDT("BTCUSDT");

    public final String value;

    Symbol(final String symbol) {
        this.value = symbol;
    }

}
