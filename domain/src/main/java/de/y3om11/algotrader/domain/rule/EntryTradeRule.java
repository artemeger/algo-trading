package de.y3om11.algotrader.domain.rule;

import de.y3om11.algotrader.domain.entity.CandlestickSeries;
import de.y3om11.algotrader.domain.gateway.TradingRule;

public abstract class EntryTradeRule implements TradingRule {

    protected CandlestickSeries candlestickSeries;

    public void update(final CandlestickSeries candlestickSeries){
        this.candlestickSeries = candlestickSeries;
    }
}
