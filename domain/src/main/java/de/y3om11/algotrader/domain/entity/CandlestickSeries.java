package de.y3om11.algotrader.domain.entity;

import org.nd4j.linalg.api.ndarray.INDArray;

import java.util.Arrays;
import java.util.Map;

public class CandlestickSeries {

    public static final long [] SHAPE = {6, 1};
    private final Timeframe timeframe;
    private final Map<Long, INDArray> candleSticks;

    public CandlestickSeries(final CandlestickSeriesBuilder builder){
        candleSticks = builder.candleSticks;
        timeframe = builder.timeframe;
    }

    public void addCandlestick(final Long open, final INDArray candlestick){
        if(Arrays.equals(candlestick.shape(), SHAPE) && !candleSticks.containsKey(open)) {
            candleSticks.put(open, candlestick);
            System.out.println(candleSticks);
        }
    }

    public Map<Long, INDArray> getAllCandlesticks(){
        return candleSticks;
    }

    public Timeframe getTimeframe(){
        return timeframe;
    }
}
