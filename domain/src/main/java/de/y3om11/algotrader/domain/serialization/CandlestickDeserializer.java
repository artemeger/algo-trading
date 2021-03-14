package de.y3om11.algotrader.domain.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import de.y3om11.algotrader.domain.entity.Candlestick;
import de.y3om11.algotrader.domain.entity.CandlestickBuilder;

import java.io.IOException;

public class CandlestickDeserializer extends JsonDeserializer<Candlestick> {

    @Override
    public Candlestick deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext)
            throws IOException {
        final JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        final double open = node.get("open").asDouble();
        final double close = node.get("close").asDouble();
        final double high = node.get("high").asDouble();
        final double low = node.get("low").asDouble();
        final double volume = node.get("volume").asDouble();
        final int numberOfTrades = node.get("numberOfTrades").asInt();
        final long openTime = node.get("openTime").asLong();
        return new CandlestickBuilder()
                .withOpen(open)
                .withClose(close)
                .withHigh(high)
                .withLow(low)
                .withVolume(volume)
                .withNumberOfTrades(numberOfTrades)
                .withOpenTime(openTime)
                .build();
    }
}
