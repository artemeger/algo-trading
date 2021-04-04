package de.y3om11.algotrading.domain;

import de.y3om11.algotrading.domain.constants.MarketPair;
import de.y3om11.algotrading.domain.constants.TimeInterval;
import de.y3om11.algotrading.domain.gateway.MarketProvider;
import de.y3om11.algotrading.domain.gateway.OrderProvider;
import de.y3om11.algotrading.domain.model.TradingSession;
import de.y3om11.algotrading.domain.model.TradingSessionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ta4j.core.BaseTradingRecord;
import org.ta4j.core.num.Num;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class TradeExecutor {

    private final ScheduledExecutorService executorService;
    private final MarketProvider marketProvider;
    private final OrderProvider orderProvider;
    private final Map<String, TradingSession> tradingSessionCache;
    private final Map<String, Future<?>> tradingProcessCache;

    @Autowired
    public TradeExecutor (final ScheduledExecutorService executorService,
                          final MarketProvider marketProvider,
                          final OrderProvider orderProvider){
        this.executorService = executorService;
        this.marketProvider = marketProvider;
        this.orderProvider = orderProvider;
        this.tradingSessionCache = new HashMap<>();
        this.tradingProcessCache = new HashMap<>();
    }

    String createTradeSession(final MarketPair marketPair, final TimeInterval timeInterval,
                              final int maxBarCount, final String strategy,
                              final Num amountPerOrder){
        var uuid = UUID.randomUUID().toString();
        var barSeries = marketProvider.getBarSeries(marketPair, timeInterval, maxBarCount);

        var tradingSession = new TradingSessionBuilder()
                .withTradingSessionId(uuid)
                .withBarSeries(barSeries)
                .withStrategy(StrategyFactory.getStrategy(strategy, barSeries))
                .withTradingRecord(new BaseTradingRecord())
                .withOrderProvider(orderProvider)
                .withAmountPerOrder(amountPerOrder)
                .build();
        tradingSessionCache.put(uuid, tradingSession);
        return uuid;
    }

    boolean runTradingSession(final String tradingSessionId) {
        if(!tradingSessionCache.containsKey(tradingSessionId)) return false;
        final TradingSession tradingSession = tradingSessionCache.get(tradingSessionId);
        tradingProcessCache.put(tradingSessionId, executorService.scheduleWithFixedDelay(tradingSession, 0, 500, TimeUnit.MILLISECONDS));
        return true;
    }

    boolean stopTradingSession(final String tradingSessionId){
        if(!tradingSessionCache.containsKey(tradingSessionId)) return false;
        tradingProcessCache.get(tradingSessionId).cancel(true);
        tradingProcessCache.remove(tradingSessionId);
        tradingSessionCache.get(tradingSessionId).closeAllOrders();
        return true;
    }
}
