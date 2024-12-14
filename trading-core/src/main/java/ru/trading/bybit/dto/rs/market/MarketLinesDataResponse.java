package ru.trading.bybit.dto.rs.market;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.LinkedHashMap;

@Data
@Builder
@ToString
public class MarketLinesDataResponse {
    private int retCode;
    private String retMsg;
    private MarketDataResult result;
    private LinkedHashMap<String, Object> retExtInfo;
    private long time;
}
