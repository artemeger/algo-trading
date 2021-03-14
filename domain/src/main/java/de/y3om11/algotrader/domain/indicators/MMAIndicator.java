package de.y3om11.algotrader.domain.indicators;

import de.y3om11.algotrader.domain.entity.Candlestick;
import de.y3om11.algotrader.domain.entity.CandlestickSeries;
import de.y3om11.algotrader.domain.gateway.Indicator;

public class MMAIndicator implements Indicator {

    private final double multiplier;

    public MMAIndicator(final long candleCount){
        this.multiplier = 1.0 / candleCount;
    }

    @Override
    public double calculate(final long timestamp, final CandlestickSeries candlestickSeries) {
        final Candlestick currentCandle = candlestickSeries.getCandlestick(timestamp);
        final Candlestick previousCandle = candlestickSeries.getCandlestick(timestamp - candlestickSeries.getTimeframe().value);
        if(currentCandle == null) return 0.0;
        if(previousCandle == null) return currentCandle.getClose();
        return (currentCandle.getClose() - previousCandle.getClose()) * multiplier + currentCandle.getClose();
    }
}
