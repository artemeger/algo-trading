package de.y3om11.algotrader.domain.indicators;

import de.y3om11.algotrader.domain.entity.*;
import de.y3om11.algotrader.domain.gateway.Indicator;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class LossIndicatorTest {

    @Test
    void lossFunction_when_previousCloseGreater_then_LossGreaterZero(){
        // arrange
        final long firstTimestamp = 1615553340000L;
        final long secondTimestamp = firstTimestamp + Timeframe.ONE_MINUTE.value;
        final Candlestick firstCandle = new CandlestickBuilder()
                .withOpen(100.0)
                .withClose(115.0)
                .withHigh(120.0)
                .withLow(100.0)
                .withVolume(100.0)
                .withNumberOfTrades(10)
                .withOpenTime(firstTimestamp)
                .build();
        final Candlestick secondCandle = new CandlestickBuilder()
                .withOpen(115.0)
                .withClose(103.0)
                .withHigh(115.0)
                .withLow(103.0)
                .withVolume(100.0)
                .withNumberOfTrades(30)
                .withOpenTime(secondTimestamp)
                .build();
        final HashMap<Long, Candlestick> candlestickMap = new HashMap<>();
        candlestickMap.put(firstTimestamp, firstCandle);
        candlestickMap.put(secondTimestamp, secondCandle);
        final CandlestickSeries candlestickSeries = new CandlestickSeriesBuilder()
                .withTimeframe(Timeframe.ONE_MINUTE)
                .withCandleSticks(candlestickMap)
                .build();
        // act
        final Indicator testee = new LossIndicator();
        final double result = testee.calculate(secondTimestamp, candlestickSeries);
        // assert
        assertEquals(12.0, result);
    }
}