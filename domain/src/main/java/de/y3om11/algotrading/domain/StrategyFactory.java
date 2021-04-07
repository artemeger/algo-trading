package de.y3om11.algotrading.domain;

import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseStrategy;
import org.ta4j.core.Rule;
import org.ta4j.core.Strategy;
import org.ta4j.core.indicators.*;
import org.ta4j.core.indicators.adx.ADXIndicator;
import org.ta4j.core.indicators.candles.BullishEngulfingIndicator;
import org.ta4j.core.indicators.helpers.*;
import org.ta4j.core.num.DoubleNum;
import org.ta4j.core.num.Num;
import org.ta4j.core.trading.rules.*;

import java.util.ArrayList;
import java.util.Optional;

public class StrategyFactory {

    public static Strategy getStrategy(final String strategy, final BarSeries barSeries){
        return switch(strategy){
            case "SimpleSMA15" -> {
                final var price = new ClosePriceIndicator(barSeries);
                final var sma = new SMAIndicator(price, 15);
                final var entryRule = new UnderIndicatorRule(sma, price);
                final var exitRule = new OverIndicatorRule(sma, price);
                var b = new BullishEngulfingIndicator(barSeries);
                var r = new BooleanIndicatorRule(b);
                yield new BaseStrategy(entryRule, exitRule, 2);
            }
            case "TripleEMACross" -> {
                final var closePrice = new ClosePriceIndicator(barSeries);

                // EMA 25
                final var ema25 = new EMAIndicator(closePrice, 25);
                final var priceOverEma25 = new OverIndicatorRule(closePrice, ema25);
                final var ema25RuleChain = backwardsEmaCloseOverChainRule(ema25, closePrice, 6, 15)
                        .orElse(priceOverEma25);

                // EMA 50
                final var ema50 = new EMAIndicator(closePrice, 50);
                final var priceOverEma50 = new OverIndicatorRule(closePrice, ema50);
                final var ema50RuleChain = backwardsEmaCloseOverChainRule(ema50, closePrice, 6, 15)
                        .orElse(priceOverEma50);

                // EMA 25 - 50 Channel
                final var over = new EMAIndicator(closePrice, 50);
                final var under = new EMAIndicator(closePrice, 25);
                final var swingRule = backwardsEmaInChannelChainRule(over, under, closePrice, 1, 5)
                        .orElse(priceOverEma50);

                // Gain - Loss
                final var bar = barSeries.getLastBar();
                final var differenceShort = getPercentageDiff(bar.getClosePrice(), bar.getLowPrice());
                final var differenceLong = getPercentageDiff(bar.getClosePrice(), bar.getHighPrice());
                final var stopLoss = new StopLossRule(closePrice, differenceShort.multipliedBy(DoubleNum.valueOf(2)));
                final var stopGain = new StopGainRule(closePrice, differenceLong.multipliedBy(DoubleNum.valueOf(10)));

                // Rules
                final var entryRule = priceOverEma25
                        .and(ema25RuleChain)
                        .and(priceOverEma50)
                        .and(ema50RuleChain)
                        .and(swingRule);

                final var exitRule = (stopLoss.or(stopGain));
                yield new BaseStrategy(entryRule, exitRule, 50);
            }
            case "TripleEMACrossWithADX" -> {
                final var closePrice = new ClosePriceIndicator(barSeries);
                final var ema3 = new EMAIndicator(closePrice, 3);
                final var ema25 = new EMAIndicator(closePrice, 25);
                final var ema50 = new EMAIndicator(closePrice, 50);
                final var rsi3 = new RSIIndicator(closePrice, 3);
                final var adx = new ADXIndicator(barSeries, 25);

                final var ema25UnderEma50 = new UnderIndicatorRule(ema25, ema50);
                final var ema3CrossedOverEma50 = new CrossedUpIndicatorRule(ema3, ema50);
                final var ema3CrossedOverEma25 = new OverIndicatorRule(ema3, ema25);
                final var rsi3CrossedOver75 = new CrossedUpIndicatorRule(rsi3, 85);
                final var adxOver20 = new OverIndicatorRule(adx, 25);

                final var bar = barSeries.getLastBar();
                final var differenceShort = getPercentageDiff(bar.getClosePrice(), bar.getLowPrice());
                final var stopLoss = new StopLossRule(closePrice, differenceShort);
                final var stopEmaLoss = new CrossedDownIndicatorRule(closePrice, ema50);

                final var entryRule = ema25UnderEma50
                        .and(ema3CrossedOverEma50)
                        .and(adxOver20)
                        .and(ema3CrossedOverEma25);

                final var exitRule = stopLoss
                        .or(stopEmaLoss)
                        .or(rsi3CrossedOver75);

                yield new BaseStrategy(entryRule, exitRule, 50);
            }
            default -> throw new IllegalStateException("Unexpected value: " + strategy);
        };
    }

    private static Optional<Rule> backwardsEmaCloseOverChainRule(final EMAIndicator emaIndicator, final PriceIndicator closePrice,
                                                          final int rangeFrom, final int rangeTo){
        final var result = new ArrayList<Rule>();
        for(int index = rangeFrom; index <= rangeTo; index++){
            final var prevClosePrice = new PreviousValueIndicator(closePrice, index);
            final var prevEma = new PreviousValueIndicator(emaIndicator, index);
            result.add(new OverIndicatorRule(prevClosePrice, prevEma));
        }
        return result.stream().reduce(Rule::and);
    }

    private static Optional<Rule> backwardsEmaInChannelChainRule(final EMAIndicator emaIndicatorOver, final EMAIndicator emaIndicatorUnder,
                                                                 final PriceIndicator closePrice, final int rangeFrom,
                                                                 final int rangeTo){
        final var result = new ArrayList<Rule>();
        for(int index = rangeFrom; index <= rangeTo; index++){
            final var prevClosePrice = new PreviousValueIndicator(closePrice, index);
            final var prevEmaUnder = new PreviousValueIndicator(emaIndicatorUnder, index);
            final var prevEmaOver = new PreviousValueIndicator(emaIndicatorOver, index);
            final var rule = new UnderIndicatorRule(prevClosePrice, prevEmaUnder)
                    .and(new OverIndicatorRule(prevClosePrice, prevEmaOver));
            result.add(rule);
        }
        return result.stream().reduce(Rule::or);
    }

    private static Num getPercentageDiff(final Num value, final Num delta){
        final var onePercent = value.dividedBy(DoubleNum.valueOf(100));
        final var percentDelta = delta.dividedBy(onePercent);
        return DoubleNum.valueOf(100)
                .minus(percentDelta).abs()
                .multipliedBy(DoubleNum.valueOf(0.01));
    }
}
