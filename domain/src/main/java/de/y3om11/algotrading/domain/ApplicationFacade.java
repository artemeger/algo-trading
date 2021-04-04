package de.y3om11.algotrading.domain;

import de.y3om11.algotrading.domain.constants.MarketPair;
import de.y3om11.algotrading.domain.constants.TimeInterval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ta4j.core.num.Num;

@Service
public class ApplicationFacade {

    private final TradeExecutor tradeExecutor;

    @Autowired
    public ApplicationFacade(final TradeExecutor tradeExecutor){
        this.tradeExecutor = tradeExecutor;
    }

    public String createTradeSession(final MarketPair marketPair, final TimeInterval timeInterval,
                                     final int maxBarCount, final String strategy,
                                     final Num amountPerOrder){
        return tradeExecutor.createTradeSession(marketPair, timeInterval, maxBarCount, strategy, amountPerOrder);
    }

    public boolean runTradingSession(final String tradingSessionId) {
        return tradeExecutor.runTradingSession(tradingSessionId);
    }

    public boolean stopTradingSession(final String tradingSessionId){
        return tradeExecutor.stopTradingSession(tradingSessionId);
    }
}
