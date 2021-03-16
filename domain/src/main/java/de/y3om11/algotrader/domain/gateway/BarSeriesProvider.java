package de.y3om11.algotrader.domain.gateway;

import de.y3om11.algotrader.domain.constants.MarketPair;
import de.y3om11.algotrader.domain.constants.TimeInterval;
import org.ta4j.core.BarSeries;

public interface BarSeriesProvider {

    BarSeries getBarSeries(MarketPair marketPair, TimeInterval timeInterval, int maxBarCount);
}
