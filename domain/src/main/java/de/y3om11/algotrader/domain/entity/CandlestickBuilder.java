package de.y3om11.algotrader.domain.entity;

public class CandlestickBuilder {

    double open = -1;
    double close = -1;
    double high = -1;
    double low = -1;
    double volume = -1;
    long numberOfTrades = -1;
    long openTime = -1;

    public CandlestickBuilder withOpen(final double open){
        this.open = open;
        return this;
    }

    public CandlestickBuilder withClose(final double close){
        this.close = close;
        return this;
    }

    public CandlestickBuilder withHigh(final double high){
        this.high = high;
        return this;
    }

    public CandlestickBuilder withLow(final double low){
        this.low = low;
        return this;
    }

    public CandlestickBuilder withVolume(final double volume){
        this.volume = volume;
        return this;
    }

    public CandlestickBuilder withNumberOfTrades(final long numberOfTrades){
        this.numberOfTrades = numberOfTrades;
        return this;
    }

    public CandlestickBuilder withOpenTime(final long openTime){
        this.openTime = openTime;
        return this;
    }

    public Candlestick build(){
        return new Candlestick(this);
    }
}
