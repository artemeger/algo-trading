package de.y3om11.algotrader.domain.indicators;

import com.adelean.inject.resources.junit.jupiter.GivenTextResource;
import com.adelean.inject.resources.junit.jupiter.TestWithResources;
import de.y3om11.algotrader.domain.entity.Candlestick;
import de.y3om11.algotrader.domain.entity.CandlestickSeries;
import de.y3om11.algotrader.domain.entity.CandlestickSeriesBuilder;
import de.y3om11.algotrader.domain.entity.Timeframe;
import de.y3om11.algotrader.domain.serialization.GenericJsonSerializer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@TestWithResources
class MMAIndicatorTest {

    @GivenTextResource("payloads/mmaPayload.json")
    static String candlestickSeriesPayload;

    private final GenericJsonSerializer serializer = new GenericJsonSerializer();

    @Test
    void calculate() throws IOException {
        final List<Candlestick> result = Arrays.asList(serializer.getObjectFromString(Candlestick[].class, candlestickSeriesPayload));
        final CandlestickSeries candlestickSeries = new CandlestickSeriesBuilder().
                withCandleSticks(result.stream()
                        .collect(Collectors.toMap(Candlestick::getOpenTime, candleStick -> candleStick)))
                .withTimeframe(Timeframe.ONE_MINUTE)
                .build();
        final long testTime = 1615554000000L;
        final MMAIndicator testee = new MMAIndicator(10);
        System.out.println(testee.calculate(testTime, candlestickSeries));
    }
}