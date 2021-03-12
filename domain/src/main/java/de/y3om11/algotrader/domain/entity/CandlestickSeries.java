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
        if(!candleSticks.containsKey(candlestick.getOpenTime())) {
            candleSticks.put(candlestick.getOpenTime(), candlestick);
            System.out.println(candlestick);
        }
    }

    public Map<Long, Candlestick> getAllCandlesticks(){
        return candleSticks;
    }

    public Timeframe getTimeframe(){
        return timeframe;
    }
}
