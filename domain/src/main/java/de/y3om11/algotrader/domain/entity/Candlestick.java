package de.y3om11.algotrader.domain.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.y3om11.algotrader.domain.serialization.CandlestickDeserializer;
import de.y3om11.algotrader.domain.serialization.CandlestickSerializer;

import java.util.Objects;

import static java.lang.String.format;

@JsonDeserialize(using = CandlestickDeserializer.class)
@JsonSerialize(using = CandlestickSerializer.class)
public class Candlestick {

    private final double open;
    private final double close;
    private final double high;
    private final double low;
    private final double volume;
    private final long numberOfTrades;
    private final long openTime;

    public Candlestick(final CandlestickBuilder builder){
        if(builder.open < 0 || builder.close < 0 || builder.high < 0 || builder.low < 0 ||
                builder.volume < 0 || builder.numberOfTrades < 0 || builder.openTime < 0){
            throw new IllegalArgumentException("All Candlestick fields must be set");
        }
        this.open = builder.open;
        this.close = builder.close;
        this.high = builder.high;
        this.low = builder.low;
        this.volume = builder.volume;
        this.numberOfTrades = builder.numberOfTrades;
        this.openTime = builder.openTime;
    }

    public double getOpen(){
        return this.open;
    }

    public double getClose(){
        return this.close;
    }

    public double getHigh(){
        return this.high;
    }

    public double getLow(){
        return this.low;
    }

    public double getVolume(){
        return this.volume;
    }

    public long getNumberOfTrades(){
        return this.numberOfTrades;
    }

    public long getOpenTime(){
        return this.openTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Candlestick that = (Candlestick) o;
        return Double.compare(that.open, open) == 0 &&
                Double.compare(that.close, close) == 0 &&
                Double.compare(that.high, high) == 0 &&
                Double.compare(that.low, low) == 0 &&
                Double.compare(that.volume, volume) == 0
                && numberOfTrades == that.numberOfTrades
                && openTime == that.openTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(open, close, high, low, volume, numberOfTrades, openTime);
    }

    @Override
    public String toString() {
        return format("CandleStick{Timestamp: %s - Open: %s - Close %s - High %s - Low %s - Volume %s - Number of Trades %s}",
                this.openTime,
                this.open,
                this.close,
                this.high,
                this.low,
                this.volume,
                this.numberOfTrades);
    }
}
