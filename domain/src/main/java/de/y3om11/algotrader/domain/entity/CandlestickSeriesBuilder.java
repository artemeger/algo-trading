package de.y3om11.algotrader.domain.entity;

import org.nd4j.linalg.api.ndarray.INDArray;

import java.util.List;
import java.util.Map;

public class CandlestickSeriesBuilder {

    Map<Long, INDArray> candleSticks;
    Timeframe timeframe;

    public CandlestickSeriesBuilder withCandleSticks(Map<Long, INDArray> candleSticks){
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
