package de.y3om11.algotrading.domain.gateway;

import de.y3om11.algotrading.domain.constants.MarketPair;
import de.y3om11.algotrading.domain.constants.TimeInterval;
import org.ta4j.core.BarSeries;

public interface MarketProvider {

    BarSeries getBarSeries(MarketPair marketPair, TimeInterval timeInterval, int maxBarCount);
}
