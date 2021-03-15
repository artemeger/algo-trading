package de.y3om11.algotrading.adapter.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.ta4j.core.BaseBar;

import java.io.IOException;

public class BaseBarSerializer extends JsonSerializer<BaseBar> {

    @Override
    public void serialize(final BaseBar bar, final JsonGenerator jsonGenerator,
                          final SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("endTime", bar.getEndTime().toInstant().toEpochMilli());
        jsonGenerator.writeNumberField("open", bar.getOpenPrice().doubleValue());
        jsonGenerator.writeNumberField("close", bar.getClosePrice().doubleValue());
        jsonGenerator.writeNumberField("high", bar.getHighPrice().doubleValue());
        jsonGenerator.writeNumberField("low", bar.getLowPrice().doubleValue());
        jsonGenerator.writeNumberField("volume", bar.getVolume().doubleValue());
        jsonGenerator.writeNumberField("numberOfTrades", bar.getTrades());
        jsonGenerator.writeNumberField("timePeriod", bar.getTimePeriod().toMillis());
        jsonGenerator.writeEndObject();
    }
}
