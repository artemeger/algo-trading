package de.y3om11.algotrader.domain.entity;

import de.y3om11.algotrader.domain.rule.EntryTradeRule;
import de.y3om11.algotrader.domain.rule.ExitTradeRule;

import java.util.List;

public class TradeBuilder {

    String tradeId;
    List<EntryTradeRule> entryRuleSet;
    List<ExitTradeRule> exitRuleSet;
    CandlestickSeries candlestickSeries;

    public TradeBuilder withTradeId(final String tradeId){
        this.tradeId = tradeId;
        return this;
    }

    public TradeBuilder withEntryRuleSet(final List<EntryTradeRule> entryRuleSet){
        this.entryRuleSet = entryRuleSet;
        return this;
    }

    public TradeBuilder withExitRuleSet(final List<ExitTradeRule> exitRuleSet){
        this.exitRuleSet = exitRuleSet;
        return this;
    }

    public TradeBuilder withCandlestickSeries(final CandlestickSeries candlestickSeries){
        this.candlestickSeries = candlestickSeries;
        return this;
    }

    public Trade build(){
        return new Trade(this);
    }
}
