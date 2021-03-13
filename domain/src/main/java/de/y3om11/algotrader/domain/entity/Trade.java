package de.y3om11.algotrader.domain.entity;

import de.y3om11.algotrader.domain.gateway.TradingRule;
import de.y3om11.algotrader.domain.rule.EntryTradeRule;
import de.y3om11.algotrader.domain.rule.ExitTradeRule;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class Trade {

    private final String tradeId;
    private final List<EntryTradeRule> entryRuleSet;
    private final List<ExitTradeRule> exitRuleSet;
    private final CandlestickSeries candlestickSeries;
    private Order entryOrder;
    private Order exitOrder;

    public Trade(final TradeBuilder builder){
        verify(builder);
        this.tradeId = builder.tradeId;
        this.entryRuleSet = builder.entryRuleSet;
        this.exitRuleSet = builder.exitRuleSet;
        this.candlestickSeries = builder.candlestickSeries;
        entryRuleSet.forEach(tradingRule -> tradingRule.update(this.candlestickSeries));
    }

    private void verify(final TradeBuilder builder) {
        requireNonNull(builder.tradeId, "TradeId must be not null");
        requireNonNull(builder.entryRuleSet, "Entry Rule Set must be not null");
        requireNonNull(builder.exitRuleSet, "Exit Rule Set must be not null");
        requireNonNull(builder.candlestickSeries, "Candlestick Series must be not null");
    }

    public void addCandlestick(final Candlestick candlestick){
        this.candlestickSeries.addCandlestick(candlestick);
    }

    public boolean enterTrade(){
        return entryRuleSet.stream()
                .anyMatch(TradingRule::isSatisfied);
    }

    public boolean exitTrade(){
        return exitRuleSet.stream()
                .anyMatch(TradingRule::isSatisfied);
    }

    public Optional<Order> getEntryOrder() {
        return Optional.ofNullable(entryOrder);
    }

    public void setEntryOrder(final Order entryOrder) {
        this.entryOrder = entryOrder;
        exitRuleSet.forEach(tradingRule -> tradingRule.update(this.candlestickSeries, this.entryOrder));
    }

    public Optional<Order> getExitOrder() {
        return Optional.ofNullable(exitOrder);
    }

    public void setExitOrder(final Order exitOrder) {
        this.exitOrder = exitOrder;
    }

    public String getTradeId() {
        return tradeId;
    }
}
