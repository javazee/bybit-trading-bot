package ru.trading.bybit.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.trading.bybit.dto.rs.market.CandleData;
import ru.trading.bybit.dto.rs.market.MarketDataResult;
import ru.trading.bybit.dto.rs.market.MarketLinesDataResponse;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MarketLinesDataMapper {

    public static MarketLinesDataResponse mapToResponse(final LinkedHashMap<String, Object> data) {
        final MarketDataResult marketDataResult = mapToResult((LinkedHashMap<String, Object>) data.get("result"));
        return MarketLinesDataResponse.builder()
                .retMsg((String) data.get("retMsg"))
                .retCode((Integer) data.get("retCode"))
                .retExtInfo((LinkedHashMap<String, Object>) data.get("retExtInfo"))
                .time((Long) data.get("time"))
                .result(marketDataResult)
                .build();
    }

    private static MarketDataResult mapToResult(final LinkedHashMap<String, Object> data) {
        final List<CandleData> candles = ((List<List<String>>) data.get("list")).stream()
                .map(MarketLinesDataMapper::mapToCandle)
                .sorted(Comparator.comparing(CandleData::getStartTime))
                .toList();
        return MarketDataResult.builder()
                .symbol((String) data.get("symbol"))
                .category((String) data.get("category"))
                .candles(candles)
                .build();
    }

    private static CandleData mapToCandle(final List<String> data) {
        return CandleData.builder()
                .startTime(Instant.ofEpochMilli(Long.parseLong(data.get(0))).atZone(ZoneOffset.UTC).toLocalDateTime())
                .openPrice(Double.parseDouble(data.get(1)))
                .highPrice(Double.parseDouble(data.get(2)))
                .lowPrice(Double.parseDouble(data.get(3)))
                .closePrice(Double.parseDouble(data.get(4)))
                .volume(Double.parseDouble(data.get(5)))
                .turnover(Double.parseDouble(data.get(6)))
                .build();
    }
}
