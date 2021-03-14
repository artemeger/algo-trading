package de.y3om11.algotrader.domain.entity;

import java.util.Map;

public class CandlestickSeries {

    private final Timeframe timeframe;
    private final Map<Long, Candlestick> candleSticks;

    public CandlestickSeries(final CandlestickSeriesBuilder builder){
        candleSticks = builder.candleSticks;
        timeframe = builder.timeframe;
    }

    public void addCandlestick(final Candlestick candlestick){
        candleSticks.put(candlestick.getOpenTime(), candlestick);
        System.out.println(candlestick);
    }

    public Candlestick getCandlestick(final long timestamp){
        return candleSticks.get(timestamp);
    }

    public Map<Long, Candlestick> getAllCandlesticks(){
        return candleSticks;
    }

    public Timeframe getTimeframe(){
        return timeframe;
    }
}
