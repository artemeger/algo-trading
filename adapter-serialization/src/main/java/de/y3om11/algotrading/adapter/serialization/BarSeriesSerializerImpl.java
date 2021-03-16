package de.y3om11.algotrading.adapter.serialization;

import de.y3om11.algotrader.domain.gateway.BarSeriesSerializer;
import org.ta4j.core.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class BarSeriesSerializerImpl implements BarSeriesSerializer {

    private final GenericJsonSerializer jsonSerializer = new GenericJsonSerializer();

    @Override
    public Optional<BarSeries> getBarSeriesFromJson(final String jsonString) {
        try {
            final List<Bar> result = Arrays.asList(jsonSerializer.getObjectFromString(BaseBar[].class, jsonString));
            final BaseBarSeries baseBarSeries = new BaseBarSeriesBuilder()
                    .withBars(result)
                    .build();
            return Optional.of(baseBarSeries);
        } catch (IOException e) {
            return Optional.empty();
        }
    }
}
