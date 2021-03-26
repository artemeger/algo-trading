package de.y3om11.algotrading.adapter.cucumber.testdata;

import de.y3om11.algotrading.adapter.cucumber.enums.TestMarkets;
import de.y3om11.algotrading.adapter.cucumber.mock.BarSeriesProviderMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseStrategy;
import org.ta4j.core.Rule;
import org.ta4j.core.Strategy;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.helpers.PriceIndicator;
import org.ta4j.core.num.Num;
import org.ta4j.core.trading.rules.OverIndicatorRule;
import org.ta4j.core.trading.rules.UnderIndicatorRule;

@Component
@RequestScope
public class TestDataBuilder {

    @Autowired
    private BarSeriesProviderMock barSeriesProvider;

    BarSeries barSeries;

    PriceIndicator priceIndicator;

    CachedIndicator<Num> cachedIndicator;

    Rule entryRule;

    Rule exitRule;

    Strategy strategy;

    public void setBarSeriesFromPath(final TestMarkets market, final long fromDate, final long toDate) {
        barSeries = barSeriesProvider.getBarSeries(market, fromDate, toDate);
    }

    public void setPriceIndicatorToClosePrice(){
        priceIndicator = new ClosePriceIndicator(barSeries);
    }

    public void setPriceIndicatorToSMAIndicator(final int range){
        cachedIndicator = new SMAIndicator(priceIndicator, range);
    }

    public void setEntryRuleToUnderIndicatorRule(){
        entryRule = new UnderIndicatorRule(cachedIndicator, priceIndicator);
    }

    public void setExitRuleToOverIndicatorRule(){
        exitRule = new OverIndicatorRule(cachedIndicator, priceIndicator);
    }

    public void createStrategy(){
        strategy = new BaseStrategy(entryRule, exitRule);
    }

    public TestData build(){
        return new TestData(this);
    }
}
