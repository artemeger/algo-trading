package de.y3om11.algotrading.domain;

import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseStrategy;
import org.ta4j.core.Strategy;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.trading.rules.OverIndicatorRule;
import org.ta4j.core.trading.rules.UnderIndicatorRule;

public class StrategyFactory {

    public static Strategy getStrategy(final String strategy, final BarSeries barSeries){
        return switch(strategy){
            case "SimpleSMA15" -> {
                var priceIndicator = new ClosePriceIndicator(barSeries);
                var smaIndicator = new SMAIndicator(priceIndicator, 15);
                var entryRule = new UnderIndicatorRule(smaIndicator, priceIndicator);
                var exitRule = new OverIndicatorRule(smaIndicator, priceIndicator);
                yield new BaseStrategy(entryRule, exitRule, 2);
            }
            default -> throw new IllegalStateException("Unexpected value: " + strategy);
        };
    }
}
