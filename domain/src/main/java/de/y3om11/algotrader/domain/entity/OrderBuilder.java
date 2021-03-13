package de.y3om11.algotrader.domain.entity;

public class OrderBuilder {

    OrderType orderType;

    double pricePerUnit = -1;

    double amount = -1;

    double fee = -1;

    long timestamp = -1;

    public OrderBuilder withOrderType(final OrderType orderType){
        this.orderType = orderType;
        return this;
    }

    public OrderBuilder withPricePerUnit(final double pricePerUnit){
        this.pricePerUnit = pricePerUnit;
        return this;
    }

    public OrderBuilder withAmount(final double amount){
        this.amount = amount;
        return this;
    }

    public OrderBuilder withFee(final double fee){
        this.fee = fee;
        return this;
    }

    public OrderBuilder withTimestamp(final long timestamp){
        this.timestamp = timestamp;
        return this;
    }

    public Order build(){
        return new Order(this);
    }
}
