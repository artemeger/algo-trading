package de.y3om11.algotrading.domain.gateway;

import org.ta4j.core.Order;
import org.ta4j.core.num.Num;

public interface OrderProvider {

    boolean executeOrder(Order.OrderType orderType, Num price, Num amount);
    void closeAllOrders();
}
