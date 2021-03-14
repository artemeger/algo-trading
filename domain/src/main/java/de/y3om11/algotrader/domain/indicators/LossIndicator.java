package de.y3om11.algotrader.domain.indicators;

import de.y3om11.algotrader.domain.entity.Candlestick;
import de.y3om11.algotrader.domain.entity.CandlestickSeries;
import de.y3om11.algotrader.domain.gateway.Indicator;

public class LossIndicator implements Indicator {

    @Override
    public double calculate(final long timestamp, final CandlestickSeries candlestickSeries) {
        final Candlestick previousCandle = candlestickSeries.getCandlestick(timestamp - candlestickSeries.getTimeframe().value);
        if(previousCandle == null) return 0.0;
        final Candlestick currentCandle = candlestickSeries.getCandlestick(timestamp);
        final double result = previousCandle.getClose() - currentCandle.getClose();
        return Math.max(result, 0.0);
    }
}
