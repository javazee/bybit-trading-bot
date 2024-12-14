package ru.trading.bybit.dto.rs.market;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CandleData {
    private LocalDateTime startTime;
    private Double openPrice;
    private Double highPrice;
    private Double lowPrice;
    private Double closePrice;
    private Double volume;
    private Double turnover;
}
