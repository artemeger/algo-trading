package de.y3om11.algotrading.adapter.binance;

import com.binance.api.client.domain.market.CandlestickInterval;
import de.y3om11.algotrading.domain.constants.TimeInterval;

import static java.lang.String.format;

public final class TimeIntervalMapper {

    public static CandlestickInterval map(final TimeInterval timeInterval){
        return switch (timeInterval) {
            case ONE_MINUTE -> CandlestickInterval.ONE_MINUTE;
            case FIVE_MINUTE -> CandlestickInterval.FIVE_MINUTES;
        };
    }
}
