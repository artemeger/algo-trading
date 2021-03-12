package de.y3om11.algotrader.domain.serialization;

import de.y3om11.algotrader.domain.entity.Candlestick;
import org.nd4j.shade.jackson.core.JsonGenerator;
import org.nd4j.shade.jackson.databind.JsonSerializer;
import org.nd4j.shade.jackson.databind.SerializerProvider;

import java.io.IOException;

public class CandlestickSerializer extends JsonSerializer<Candlestick> {

    @Override
    public void serialize(final Candlestick candlestick, final JsonGenerator jsonGenerator,
                          final SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("timestamp", candlestick.getOpenTime());
        jsonGenerator.writeNumberField("open", candlestick.getOpen());
        jsonGenerator.writeNumberField("close", candlestick.getClose());
        jsonGenerator.writeNumberField("high", candlestick.getHigh());
        jsonGenerator.writeNumberField("low", candlestick.getLow());
        jsonGenerator.writeNumberField("volume", candlestick.getVolume());
        jsonGenerator.writeEndObject();
    }
}
