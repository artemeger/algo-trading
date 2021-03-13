package de.y3om11.algotrader.domain.rule;

import de.y3om11.algotrader.domain.entity.CandlestickSeries;
import de.y3om11.algotrader.domain.entity.Order;
import de.y3om11.algotrader.domain.gateway.TradingRule;

public abstract class ExitTradeRule implements TradingRule {

    protected CandlestickSeries candlestickSeries;
    protected Order order;

    public void update(final CandlestickSeries candlestickSeries, final Order order){
        this.candlestickSeries = candlestickSeries;
        this.order = order;
    }
}
