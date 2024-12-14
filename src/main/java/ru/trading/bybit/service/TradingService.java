package ru.trading.bybit.service;

import com.bybit.api.client.domain.trade.request.TradeOrderRequest;
import com.bybit.api.client.restApi.BybitApiAsyncTradeRestClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TradingService {

    private final BybitApiAsyncTradeRestClient restClient;


    public TradingService(BybitApiAsyncTradeRestClient restClient) {
        this.restClient = restClient;
    }

    public void doTrading() {
    }
}
