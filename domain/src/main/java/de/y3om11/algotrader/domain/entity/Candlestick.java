package de.y3om11.algotrader.domain.entity;

import de.y3om11.algotrader.domain.serialization.CandlestickDeserializer;
import de.y3om11.algotrader.domain.serialization.CandlestickSerializer;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.shade.jackson.databind.annotation.JsonDeserialize;
import org.nd4j.shade.jackson.databind.annotation.JsonSerialize;

import java.util.Objects;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

@JsonDeserialize(using = CandlestickDeserializer.class)
@JsonSerialize(using = CandlestickSerializer.class)
public class Candlestick {

    public static final long [] SHAPE = {6, 1};
    private final INDArray value;

    public Candlestick(final CandleStickBuilder builder){
        requireNonNull(builder.value, "CandleStick value mut be non null");
        value = builder.value;
    }

    public double getOpen(){
        return value.getDouble(0, 1);
    }

    public double getClose(){
        return value.getDouble(1, 1);
    }

    public double getHigh(){
        return value.getDouble(2, 1);
    }

    public double getLow(){
        return value.getDouble(3, 1);
    }

    public double getVolume(){
        return value.getDouble(4, 1);
    }

    public long getOpenTime(){
        return (long) value.getDouble(5, 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Candlestick that = (Candlestick) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return format("CandleStick{Timestamp: %s - Open: %s - Close %s - High %s - Low %s - Volume %s}",
                (long) value.getDouble(5, 1),
                value.getDouble(0, 1),
                value.getDouble(1, 1),
                value.getDouble(2, 1),
                value.getDouble(3, 1),
                value.getDouble(4, 1));
    }
}
