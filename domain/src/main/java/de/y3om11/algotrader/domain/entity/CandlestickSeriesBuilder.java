package de.y3om11.algotrader.domain.entity;

import java.util.Map;

public class CandlestickSeriesBuilder {

    Map<Long, Candlestick> candleSticks;
    Timeframe timeframe;

    public CandlestickSeriesBuilder withCandleSticks(Map<Long, Candlestick> candleSticks){
        this.candleSticks = candleSticks;
        return this;
    }

    public CandlestickSeriesBuilder withTimeframe(Timeframe timeframe){
        this.timeframe = timeframe;
        return this;
    }

    public CandlestickSeries build(){
        return new CandlestickSeries(this);
    }
}
