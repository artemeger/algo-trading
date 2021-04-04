package de.y3om11.algotrading.adapter.binance;

import de.y3om11.algotrading.domain.gateway.OrderProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.ta4j.core.Order;
import org.ta4j.core.num.Num;

import static java.lang.String.format;

@Service
public class OrderProviderImpl implements OrderProvider {

    final static Logger log = LoggerFactory.getLogger(OrderProviderImpl.class);

    @Override
    public boolean executeOrder(final Order.OrderType orderType, final Num amount, final Num price) {
        log.info(format("Execute Order with type %s. Amount: %s - Price: %s", orderType.name(), amount, price));
        return true;
    }

    @Override
    public void closeAllOrders() { }
}
