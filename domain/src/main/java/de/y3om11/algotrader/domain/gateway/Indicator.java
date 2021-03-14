package de.y3om11.algotrader.domain.gateway;

import de.y3om11.algotrader.domain.entity.CandlestickSeries;

@FunctionalInterface
public interface Indicator {

    double calculate(long timestamp, CandlestickSeries candlestickSeries);
}
