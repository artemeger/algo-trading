package de.y3om11.algotrading.adapter.serialization;

import com.adelean.inject.resources.junit.jupiter.GivenTextResource;
import com.adelean.inject.resources.junit.jupiter.TestWithResources;
import de.y3om11.algotrader.domain.gateway.BarSeriesSerializer;
import org.junit.jupiter.api.Test;
import org.ta4j.core.BarSeries;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestWithResources
class BarSeriesSerializerImplTest {

    @GivenTextResource("payloads/candlestickSeries.json")
    static String baseBarSeriesString;

    private final BarSeriesSerializer testee = new BarSeriesSerializerImpl();

    @Test
    void getBarSeriesFromJson_when_validCandlestickSeriesString_then_validCandlestickSeriesObject() {
        // act
        final Optional<BarSeries> baseBarSeries = testee.getBarSeriesFromJson(baseBarSeriesString);

        // assert
        assertTrue(baseBarSeries.isPresent());
        assertEquals(2, baseBarSeries.get().getBarData().size());
    }
}