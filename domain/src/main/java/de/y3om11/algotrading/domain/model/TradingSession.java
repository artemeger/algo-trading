package de.y3om11.algotrading.domain.model;

import de.y3om11.algotrading.domain.gateway.OrderProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ta4j.core.*;
import org.ta4j.core.num.Num;

import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

public class TradingSession implements Runnable {

    final static Logger log = LoggerFactory.getLogger(TradingSession.class);
    public final String tradingSessionId;
    public final AtomicBoolean isStarted;
    private final BarSeries barSeries;
    private final Strategy strategy;
    private final TradingRecord tradingRecord;
    private final OrderProvider orderProvider;
    private final Num amountPerOrder;

    public TradingSession(final TradingSessionBuilder builder) {
        verify(builder);
        tradingSessionId = builder.tradingSessionId;
        barSeries = builder.barSeries;
        strategy = builder.strategy;
        tradingRecord = builder.tradingRecord;
        orderProvider = builder.orderProvider;
        amountPerOrder = builder.amountPerOrder;
        isStarted = new AtomicBoolean();
        isStarted.set(false);
    }

    private void verify(final TradingSessionBuilder builder) {
        requireNonNull(builder.tradingSessionId, "TradingSessionId must be not null");
        requireNonNull(builder.barSeries, "BarSeriesProvider must be not null");
        requireNonNull(builder.strategy, "Strategy must be not null");
        requireNonNull(builder.tradingRecord, "TradingRecord must be not null");
        requireNonNull(builder.orderProvider, "OrderProvider must be not null");
        requireNonNull(builder.amountPerOrder, "AmountPerOrder must be not null");
    }

    @Override
    public void run() {
        if(!barSeries.getBarData().isEmpty()) {
            var name = Thread.currentThread().getName();
            var currentPrice = barSeries.getLastBar().getClosePrice();
            if (enterTradeIsPossible()) {
                boolean successEntry = orderProvider.executeOrder(Order.OrderType.BUY, currentPrice, amountPerOrder);
                if (successEntry) {
                    tradingRecord.enter(barSeries.getEndIndex(), currentPrice, amountPerOrder);
                    log.info(format("%s - New Entry Order executed: %s", name, tradingRecord.getLastEntry()));
                }
            } else if (exitTradeIsPossible()) {
                boolean successExit = orderProvider.executeOrder(Order.OrderType.SELL, currentPrice, amountPerOrder);
                if (successExit) {
                    tradingRecord.exit(barSeries.getEndIndex(), currentPrice, amountPerOrder);
                    log.info(format("%s - New Exit Order executed: %s with Profit %s", name,
                            tradingRecord.getLastExit(), tradingRecord.getCurrentTrade().getProfit()));
                }
            }
        }
    }

    public void closeAllOrders(){
        orderProvider.closeAllOrders();
        isStarted.set(false);
    }

    private boolean enterTradeIsPossible(){
        return tradingRecord.getCurrentTrade().isNew() &&
                strategy.shouldEnter(barSeries.getEndIndex());
    }

    private boolean exitTradeIsPossible(){
        return tradingRecord.getCurrentTrade().isOpened() &&
                strategy.shouldExit(barSeries.getEndIndex());
    }
}
