package de.y3om11.algotrader.domain.entity;

import org.nd4j.linalg.api.ndarray.INDArray;

import java.util.Arrays;

import static java.lang.String.format;

public class CandleStickBuilder {

    INDArray value;

    public CandleStickBuilder withValue(final INDArray value){
        if(Arrays.equals(value.shape(), Candlestick.SHAPE)) {
            this.value = value;
        } else {
            throw new IllegalArgumentException(format("CandleStick value has wrong shape %s. %s was expected",
                    Arrays.toString(value.shape()), Arrays.toString(Candlestick.SHAPE)));
        }
        return this;
    }

    public Candlestick build(){
        return new Candlestick(this);
    }
}
