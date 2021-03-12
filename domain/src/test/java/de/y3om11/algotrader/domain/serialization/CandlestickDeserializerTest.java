package de.y3om11.algotrader.domain.serialization;

import com.adelean.inject.resources.junit.jupiter.GivenTextResource;
import com.adelean.inject.resources.junit.jupiter.TestWithResources;
import de.y3om11.algotrader.domain.entity.Candlestick;
import de.y3om11.algotrader.domain.entity.CandlestickSeries;
import de.y3om11.algotrader.domain.entity.CandlestickSeriesBuilder;
import de.y3om11.algotrader.domain.entity.Timeframe;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@TestWithResources
class CandlestickDeserializerTest {

    @GivenTextResource("payloads/candlestick.json")
    static String candlestickString;

    @GivenTextResource("payloads/candlestickSeries.json")
    static String candlestickSeriesString;

    private final GenericJsonSerializer testee = new GenericJsonSerializer();

    @Test
    void deserialize_when_validCandlestickString_then_validCandlestickObject() throws IOException {
        // act
        final Candlestick result = testee.getObjectFromString(Candlestick.class, candlestickString);
        final String resultAsString = testee.getStringFromRequestObject(result);
        final Candlestick resultFromString = testee.getObjectFromString(Candlestick.class, resultAsString);

        // assert
        assertEquals(1615553340000L, result.getOpenTime());
        assertEquals(0.031412, result.getOpen());
        assertEquals(123.347, result.getVolume());
        assertEquals(result, resultFromString);
    }

    @Test
    void deserialize_when_validCandlestickSeriesString_then_validCandlestickSeriesObject() throws IOException {
        // act
        final List<Candlestick> result = Arrays.asList(testee.getObjectFromString(Candlestick[].class, candlestickSeriesString));
        final CandlestickSeries candlestickSeries = new CandlestickSeriesBuilder().
                withCandleSticks(result.stream()
                        .collect(Collectors.toMap(Candlestick::getOpenTime, candleStick -> candleStick)))
                .withTimeframe(Timeframe.ONE_MINUTE)
                .build();
        System.out.println(result);
    }
}