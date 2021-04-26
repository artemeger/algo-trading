package de.y3om11.algotrading.domain.indicators;

import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.helpers.HighPriceIndicator;
import org.ta4j.core.indicators.helpers.LowPriceIndicator;
import org.ta4j.core.num.NaN;
import org.ta4j.core.num.Num;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TrendlineIndicator extends CachedIndicator<Num> {

    private final int barCount;
    private final TrendlineSide trendlineSide;

    public enum TrendlineSide {
        HIGH, LOW
    }

    public TrendlineIndicator(final BarSeries barSeries, final TrendlineSide trendlineSide, final int barCount) {
        super(barSeries);
        this.barCount = barCount;
        this.trendlineSide = trendlineSide;
    }

    @Override
    protected Num calculate(int index) {
        final var barSeries = super.getBarSeries();
        final var endIndex = barSeries.getEndIndex();
        if(endIndex < index) return NaN.NaN;
        final var beginIndex = endIndex - index;
        final var priceIndicator = switch (trendlineSide){
            case HIGH -> new HighPriceIndicator(barSeries);
            case LOW -> new LowPriceIndicator(barSeries);
        };
        final var pointB = priceIndicator.getValue(endIndex);
        // True if direction is upwards
        final var direction = priceIndicator.getValue(beginIndex)
                .isLessThan(priceIndicator.getValue(endIndex));
        final List<Num> points = new ArrayList<>();
        for(int i = beginIndex; i <= endIndex; i++){
            points.add(priceIndicator.getValue(i));
        }
        final var pointA = direction ?
                points.stream().min(Comparator.comparingDouble(Num::doubleValue)) :
                points.stream().max(Comparator.comparingDouble(Num::doubleValue));

        return null;
    }
}
