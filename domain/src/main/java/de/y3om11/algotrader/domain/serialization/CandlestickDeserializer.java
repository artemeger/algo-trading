package de.y3om11.algotrader.domain.serialization;

import de.y3om11.algotrader.domain.entity.Candlestick;
import de.y3om11.algotrader.domain.entity.CandleStickBuilder;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.shade.jackson.core.JsonParser;
import org.nd4j.shade.jackson.databind.DeserializationContext;
import org.nd4j.shade.jackson.databind.JsonDeserializer;
import org.nd4j.shade.jackson.databind.JsonNode;

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
        final double timestamp = node.get("timestamp").asDouble();
        double[] vectorDouble = new double[]{open, close, high, low, volume, timestamp};
        return new CandleStickBuilder()
                .withValue(Nd4j.create(vectorDouble, 6, 1))
                .build();
    }
}
