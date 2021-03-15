package de.y3om11.algotrading.adapter.serialization;

import com.adelean.inject.resources.junit.jupiter.GivenTextResource;
import com.adelean.inject.resources.junit.jupiter.TestWithResources;
import org.junit.jupiter.api.Test;
import org.ta4j.core.BaseBar;
import org.ta4j.core.num.DoubleNum;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@TestWithResources
class BaseBarDeserializerTest {

    @GivenTextResource("payloads/candlestick.json")
    static String candlestickString;

    private final GenericJsonSerializer testee = new GenericJsonSerializer();

    @Test
    void deserialize_when_validCandlestickString_then_validCandlestickObject() throws IOException {
        // act
        final BaseBar result = testee.getObjectFromString(BaseBar.class, candlestickString);
        final String resultAsString = testee.getStringFromRequestObject(result);
        final BaseBar resultFromString = testee.getObjectFromString(BaseBar.class, resultAsString);

        // assert
        assertEquals(DoubleNum.valueOf(0.031412), result.getOpenPrice());
        assertEquals(DoubleNum.valueOf(123.347), result.getVolume());
        assertEquals(12, result.getTrades());
        assertEquals(result, resultFromString);
    }
}