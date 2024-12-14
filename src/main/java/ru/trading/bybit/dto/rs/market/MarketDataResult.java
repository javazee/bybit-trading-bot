package ru.trading.bybit.dto.rs.market;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MarketDataResult {
    private String symbol;
    private String category;
    private List<CandleData> candles;
}
