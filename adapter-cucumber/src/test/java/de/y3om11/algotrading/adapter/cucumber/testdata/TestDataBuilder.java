package de.y3om11.algotrading.adapter.cucumber.testdata;

import de.y3om11.algotrading.adapter.cucumber.enums.TestMarkets;
import de.y3om11.algotrading.adapter.cucumber.mock.BarSeriesProviderMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ta4j.core.BarSeries;

@Component
public class TestDataBuilder {

    @Autowired
    private BarSeriesProviderMock barSeriesProvider;

    private BarSeries barSeries;

    public void setBarSeriesFromPath(final TestMarkets market, long fromDate, long toDate) {
        barSeries = barSeriesProvider.getBarSeries(market, fromDate, toDate);
    }
}
