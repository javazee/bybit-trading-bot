package ru.trading.bybit.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.trading.bybit.dto.rs.market.MarketLinesDataResponse;

import java.util.concurrent.TimeUnit;

@Service
public class Scheduler {

    private final MarketDataService marketDataService;

    public Scheduler(MarketDataService marketDataService) {
        this.marketDataService = marketDataService;
    }

    @Scheduled(fixedRate = 60, timeUnit = TimeUnit.SECONDS)
    public void askInfo() {
        final MarketLinesDataResponse response = marketDataService.getMarketLinesData();
        System.out.println(response);
    }
}
