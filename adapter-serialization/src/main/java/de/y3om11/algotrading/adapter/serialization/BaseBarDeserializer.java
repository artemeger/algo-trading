package de.y3om11.algotrading.adapter.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.ta4j.core.BaseBar;
import org.ta4j.core.num.DoubleNum;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class BaseBarDeserializer extends JsonDeserializer<BaseBar> {

    @Override
    public BaseBar deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext)
            throws IOException {
        final JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        return BaseBar.builder()
                .openPrice(DoubleNum.valueOf(node.get("open").asDouble()))
                .closePrice(DoubleNum.valueOf(node.get("close").asDouble()))
                .highPrice(DoubleNum.valueOf(node.get("high").asDouble()))
                .lowPrice(DoubleNum.valueOf(node.get("low").asDouble()))
                .volume(DoubleNum.valueOf(node.get("volume").asDouble()))
                .amount(DoubleNum.valueOf(node.get("volume").asDouble()))
                .trades(node.get("numberOfTrades").asInt())
                .endTime(ZonedDateTime.ofInstant(Instant.ofEpochMilli(node.get("endTime").asLong()), ZoneId.of("UTC")))
                .timePeriod(Duration.ofMillis(node.get("timePeriod").asLong()))
                .build();
    }
}
