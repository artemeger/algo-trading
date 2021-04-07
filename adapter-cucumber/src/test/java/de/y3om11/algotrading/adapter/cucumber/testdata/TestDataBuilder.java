package de.y3om11.algotrading.adapter.cucumber.testdata;

import de.y3om11.algotrading.adapter.cucumber.enums.TestMarkets;
import de.y3om11.algotrading.adapter.cucumber.mock.BarSeriesProviderMock;
import de.y3om11.algotrading.domain.StrategyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.ta4j.core.*;
import org.ta4j.core.cost.CostModel;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.EMAIndicator;
import org.ta4j.core.indicators.RSIIndicator;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.candles.BearishEngulfingIndicator;
import org.ta4j.core.indicators.candles.BullishEngulfingIndicator;
import org.ta4j.core.indicators.helpers.*;
import org.ta4j.core.num.DoubleNum;
import org.ta4j.core.num.Num;
import org.ta4j.core.trading.rules.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequestScope
public class TestDataBuilder {

    @Autowired
    private BarSeriesProviderMock barSeriesProvider;

    BarSeries barSeries;

    Map<String, Strategy> strategyMap = new HashMap<>();

    Map<String, Rule> ruleMap = new HashMap<>();

    Map<String, CostModel> transactionCostModel = new HashMap<>();

    Map<String, CostModel> holdingCostModelMap = new HashMap<>();

    Map<String, Num> orderSizeMap = new HashMap<>();

    Map<String, Order.OrderType> orderTypeMap = new HashMap<>();

    public void setBarSeriesFromPath(final TestMarkets market, final long fromDate, final long toDate) {
        barSeries = barSeriesProvider.getBarSeries(market, fromDate, toDate);
    }

    public void addRangeRule(final String name, final String rule, final String cachedIndicator,
                        final String priceIndicator, final int range){
        ruleMap.put(name, getRangeRuleFromString(rule, cachedIndicator, priceIndicator, range));
    }

    public void addBooleanRule(final String name, final String rule){
        ruleMap.put(name, getBooleanRuleFromString(rule));
    }

    public void addThresholdRule(final String name, final String rule, final String cachedIndicator,
                             final String priceIndicator, final int range, final double threshold){
        ruleMap.put(name, getThresholdRuleFromString(rule, cachedIndicator, priceIndicator, range, threshold));
    }

    public void addStopRule(final String name, final String rule, final double percent){
        ruleMap.put(name, getStopRuleFromString(rule, percent));
    }

    public void combineRuleListWithAnd(final String name, final List<String> rules){
        final var newRule = rules.stream().map(ruleMap::get).reduce(Rule::and).orElse(null);
        ruleMap.put(name, newRule);
    }

    public void combineRuleListWithOr(final String name, final List<String> rules){
        final var newRule = rules.stream().map(ruleMap::get).reduce(Rule::or).orElse(null);
        ruleMap.put(name, newRule);
    }

    public void setHoldingCostModel(final String name, final CostModel costModel){
        holdingCostModelMap.put(name, costModel);
    }

    public void setTransactionCostModel(final String name, final CostModel costModel){
        transactionCostModel.put(name, costModel);
    }

    public void setOrderTypeAndSize(final String name, final Order.OrderType orderType, final Num amount){
        orderSizeMap.put(name, amount);
        orderTypeMap.put(name, orderType);
    }

    public void createNewStrategy(final String name, final String entryRule,
                                  final String exitRule, final int unstable){
        strategyMap.put(name, new BaseStrategy(ruleMap.get(entryRule), ruleMap.get(exitRule), unstable));
    }

    public void setDefaultStrategy(final String defaultStrategy){
        strategyMap.put(defaultStrategy, StrategyFactory.getStrategy(defaultStrategy, barSeries));
    }

    public TestData build(){
        return new TestData(this);
    }

    private PriceIndicator getPriceIndicatorFromString(final String priceIndicator){
        return switch(priceIndicator){
            case "HIGH" -> new HighPriceIndicator(barSeries);
            case "CLOSE" -> new ClosePriceIndicator(barSeries);
            case "LOW" -> new LowPriceIndicator(barSeries);
            case "OPEN" -> new OpenPriceIndicator(barSeries);
            default -> throw new IllegalStateException("Unexpected value: " + priceIndicator);
        };
    }

    private CachedIndicator<Num> getCachedRangeIndicatorFromString(final String cachedIndicator,
                                                                   final String priceIndicator,
                                                                   final int range){
        return switch(cachedIndicator){
            case "EMA" -> new EMAIndicator(getPriceIndicatorFromString(priceIndicator), range);
            case "SMA" -> new SMAIndicator(getPriceIndicatorFromString(priceIndicator), range);
            case "RSI" -> new RSIIndicator(getPriceIndicatorFromString(priceIndicator), range);
            default -> throw new IllegalStateException("Unexpected value: " + priceIndicator);
        };
    }

    private Rule getRangeRuleFromString(final String rule, final String cachedIndicator,
                                        final String priceIndicator, final int range){
        return switch(rule){
            case "UNDER" -> new UnderIndicatorRule(getPriceIndicatorFromString(priceIndicator),
                    getCachedRangeIndicatorFromString(cachedIndicator, priceIndicator, range));
            case "OVER" -> new OverIndicatorRule(getPriceIndicatorFromString(priceIndicator),
                    getCachedRangeIndicatorFromString(cachedIndicator, priceIndicator, range));
            case "CROSSED_UNDER" -> new CrossedDownIndicatorRule(getPriceIndicatorFromString(priceIndicator),
                    getCachedRangeIndicatorFromString(cachedIndicator, priceIndicator, range));
            case "CROSSED_OVER" -> new CrossedUpIndicatorRule(getPriceIndicatorFromString(priceIndicator),
                    getCachedRangeIndicatorFromString(cachedIndicator, priceIndicator, range));
            default -> throw new IllegalStateException("Unexpected value: " + priceIndicator);
        };
    }

    private Rule getBooleanRuleFromString(final String rule){
        return switch(rule){
            case "BULLISH_ENGULFING" -> new BooleanIndicatorRule(new BullishEngulfingIndicator(barSeries));
            case "BEARISH_ENGULFING" -> new BooleanIndicatorRule(new BearishEngulfingIndicator(barSeries));
            default -> throw new IllegalStateException("Unexpected value: " + rule);
        };
    }

    private Rule getThresholdRuleFromString(final String rule, final String cachedIndicator,
                                            final String priceIndicator, final int range,
                                            final double threshold){
        return switch(rule){
            case "UNDER" -> new UnderIndicatorRule(getCachedRangeIndicatorFromString
                    (cachedIndicator, priceIndicator, range), threshold);
            case "OVER" -> new OverIndicatorRule(getCachedRangeIndicatorFromString
                    (cachedIndicator, priceIndicator, range), threshold);
            case "CROSSED_UNDER" -> new CrossedDownIndicatorRule
                    (getCachedRangeIndicatorFromString(cachedIndicator, priceIndicator, range), threshold);
            case "CROSSED_OVER" -> new CrossedUpIndicatorRule
                    (getCachedRangeIndicatorFromString(cachedIndicator, priceIndicator, range), threshold);
            default -> throw new IllegalStateException("Unexpected value: " + priceIndicator);
        };
    }

    private Rule getStopRuleFromString(final String rule, final double percent){
        return switch(rule){
            case "STOP_LOSS" -> new StopLossRule(new ClosePriceIndicator(barSeries), DoubleNum.valueOf(percent * 0.01));
            case "STOP_GAIN" -> new StopGainRule(new ClosePriceIndicator(barSeries), DoubleNum.valueOf(percent * 0.01));
            default -> throw new IllegalStateException("Unexpected value: " + rule);
        };
    }
}
