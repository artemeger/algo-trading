package de.y3om11.algotrading.domain.model;

import de.y3om11.algotrading.domain.gateway.OrderProvider;
import org.ta4j.core.BarSeries;
import org.ta4j.core.Strategy;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.num.Num;

public class TradingSessionBuilder {

    String tradingSessionId;
    BarSeries barSeries;
    Strategy strategy;
    TradingRecord tradingRecord;
    OrderProvider orderProvider;
    Num amountPerOrder;

    public TradingSessionBuilder withTradingSessionId(final String tradingSessionId){
        this.tradingSessionId = tradingSessionId;
        return this;
    }

    public TradingSessionBuilder withBarSeries(final BarSeries barSeries){
        this.barSeries = barSeries;
        return this;
    }

    public TradingSessionBuilder withStrategy(final Strategy strategy){
        this.strategy = strategy;
        return this;
    }

    public TradingSessionBuilder withTradingRecord(final TradingRecord tradingRecord){
        this.tradingRecord = tradingRecord;
        return this;
    }

    public TradingSessionBuilder withOrderProvider(final OrderProvider orderProvider){
        this.orderProvider = orderProvider;
        return this;
    }

    public TradingSessionBuilder withAmountPerOrder(final Num amountPerOrder){
        this.amountPerOrder = amountPerOrder;
        return this;
    }

    public TradingSession build(){
        return new TradingSession(this);
    }
}
