package de.y3om11.algotrading.adapter.cucumber.testdata;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Rule;
import org.ta4j.core.Strategy;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.helpers.PriceIndicator;
import org.ta4j.core.num.Num;

public class TestData {

    public final BarSeries barSeries;

    public final PriceIndicator priceIndicator;

    public final CachedIndicator<Num> cachedIndicator;

    public final Rule entryRule;

    public final Rule exitRule;

    public final Strategy strategy;

    public TestData(final TestDataBuilder builder){
        this.barSeries = builder.barSeries;
        this.priceIndicator = builder.priceIndicator;
        this.cachedIndicator = builder.cachedIndicator;
        this.entryRule = builder.entryRule;
        this.exitRule = builder.exitRule;
        this.strategy = builder.strategy;
    }
}
