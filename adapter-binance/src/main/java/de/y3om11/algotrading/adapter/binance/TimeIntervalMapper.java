package de.y3om11.algotrading.adapter.binance;

import com.binance.api.client.domain.market.CandlestickInterval;
import de.y3om11.algotrader.domain.constants.TimeInterval;
import de.y3om11.algotrader.domain.exception.AlgotradingTechnicalException;

import static java.lang.String.format;

public final class TimeIntervalMapper {

    public static CandlestickInterval map(final TimeInterval timeInterval){
        switch (timeInterval){
            case ONE_MINUTE:
                return CandlestickInterval.ONE_MINUTE;
            case FIVE_MINUTE:
                return CandlestickInterval.FIVE_MINUTES;
            default:
                throw new AlgotradingTechnicalException(format("No mapping for time interval %s found.", timeInterval.name()));
        }
    }
}
