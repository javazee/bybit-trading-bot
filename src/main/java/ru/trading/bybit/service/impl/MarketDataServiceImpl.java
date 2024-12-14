package ru.trading.bybit.service.impl;

import com.bybit.api.client.domain.CategoryType;
import com.bybit.api.client.domain.market.MarketInterval;
import com.bybit.api.client.domain.market.request.MarketDataRequest;
import com.bybit.api.client.restApi.BybitApiMarketRestClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.trading.bybit.dto.rs.market.MarketLinesDataResponse;
import ru.trading.bybit.mapper.MarketLinesDataMapper;
import ru.trading.bybit.service.MarketDataService;
import ru.trading.bybit.symbols.Symbol;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class MarketDataServiceImpl implements MarketDataService {

    private final BybitApiMarketRestClient bybitApiMarketRestClient;

    public MarketDataServiceImpl(final BybitApiMarketRestClient bybitApiMarketRestClient) {
        this.bybitApiMarketRestClient = bybitApiMarketRestClient;
    }

    public MarketLinesDataResponse getMarketLinesData() {
        try {
            var request = MarketDataRequest.builder()
                    .category(CategoryType.INVERSE)
                    .symbol(Symbol.BTC_USDT.value)
                    .start(LocalDateTime.now().minusDays(1).toEpochSecond(ZoneOffset.UTC) * 1000)
                    .end(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) * 1000)
                    .marketInterval(MarketInterval.HOURLY)
                    .build();
            var response = (LinkedHashMap<String, Object>) this.bybitApiMarketRestClient.getMarketLinesData(request);
            return MarketLinesDataMapper.mapToResponse(response);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return null;
        }
    }
}
