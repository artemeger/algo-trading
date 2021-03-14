package de.y3om11.algotrader.domain.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import de.y3om11.algotrader.domain.entity.Candlestick;
import java.io.IOException;

public class CandlestickSerializer extends JsonSerializer<Candlestick> {

    @Override
    public void serialize(final Candlestick candlestick, final JsonGenerator jsonGenerator,
                          final SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("openTime", candlestick.getOpenTime());
        jsonGenerator.writeNumberField("open", candlestick.getOpen());
        jsonGenerator.writeNumberField("close", candlestick.getClose());
        jsonGenerator.writeNumberField("high", candlestick.getHigh());
        jsonGenerator.writeNumberField("low", candlestick.getLow());
        jsonGenerator.writeNumberField("volume", candlestick.getVolume());
        jsonGenerator.writeNumberField("numberOfTrades", candlestick.getNumberOfTrades());
        jsonGenerator.writeEndObject();
    }
}
