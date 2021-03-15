package de.y3om11.algotrader.domain.gateway;

import org.ta4j.core.BarSeries;

import java.util.Optional;

public interface BarSeriesProducer {

    Optional<BarSeries> getBarSeriesFromJson(String jsonString);
}
