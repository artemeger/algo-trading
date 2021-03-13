package de.y3om11.algotrader.domain.entity;

import static java.util.Objects.requireNonNull;

public class Order {

    private final OrderType orderType;
    private final double pricePerUnit;
    private final double amount;
    private final double fee;
    private final long timestamp;

    public Order(final OrderBuilder builder){
        verify(builder);
        this.orderType = builder.orderType;
        this.pricePerUnit = builder.pricePerUnit;
        this.amount = builder.amount;
        this.fee = builder.fee;
        this.timestamp = builder.timestamp;
    }

    private void verify(final OrderBuilder builder) {
        requireNonNull(builder.orderType, "OrderType must be not null");
        if(builder.pricePerUnit < 0 || builder.amount < 0 || builder.fee < 0 || builder.timestamp < 0){
            throw new IllegalArgumentException("All Order fields must be not null");
        }
    }
}
