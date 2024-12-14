package ru.trading.bybit.service;

import ru.trading.bybit.dto.rs.market.MarketLinesDataResponse;

public interface MarketDataService {

    MarketLinesDataResponse getMarketLinesData();
}
