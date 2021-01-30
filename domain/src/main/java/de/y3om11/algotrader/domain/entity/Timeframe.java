package de.y3om11.algotrader.domain.entity;

public enum Timeframe {

    ONE_MINUTE(60000L),
    FIVE_MINUTE(300000L),
    FIFTEEN_MINUTE(900000L),
    THIRTY_MINUTE(1800000L),
    ONE_HOUR(3600000L),
    TWO_HOUR(7200000L),
    FOUR_HOUR(14400000L);

    public final long value;

    Timeframe(long value) {
        this.value = value;
    }
}
