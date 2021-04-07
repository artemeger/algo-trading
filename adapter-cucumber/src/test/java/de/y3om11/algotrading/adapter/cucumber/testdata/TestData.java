package de.y3om11.algotrading.adapter.cucumber.testdata;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Order;
import org.ta4j.core.Strategy;
import org.ta4j.core.cost.CostModel;
import org.ta4j.core.num.Num;

import java.util.Map;

public class TestData {

    public final BarSeries barSeries;
    public final Map<String, Strategy> strategy;
    public final Map<String, CostModel> transactionCostModel;
    public final Map<String, CostModel> holdingCostModel;
    public final Map<String, Num> orderSize;
    public final Map<String, Order.OrderType> orderType;

    public TestData(final TestDataBuilder builder){
        this.barSeries = builder.barSeries;
        this.strategy = builder.strategyMap;
        this.holdingCostModel = builder.holdingCostModelMap;
        this.transactionCostModel = builder.transactionCostModel;
        this.orderSize = builder.orderSizeMap;
        this.orderType = builder.orderTypeMap;
    }
}
