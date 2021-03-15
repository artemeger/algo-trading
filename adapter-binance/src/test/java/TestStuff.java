import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiWebSocketClient;

import com.binance.api.client.domain.market.CandlestickInterval;
import org.junit.jupiter.api.Test;
import org.ta4j.core.BaseBar;
import org.ta4j.core.num.DoubleNum;

import java.time.*;

public class TestStuff {

    @Test
    void test(){
        BinanceApiWebSocketClient client = BinanceApiClientFactory.newInstance().newWebSocketClient();
        // Obtain 1m candlesticks in real-time for ETH/BTC
        client.onCandlestickEvent("ethbtc", CandlestickInterval.ONE_MINUTE, response -> {
            try {
                final BaseBar bar = BaseBar.builder()
                        .openPrice(DoubleNum.valueOf(response.getOpen()))
                        .closePrice(DoubleNum.valueOf(response.getClose()))
                        .highPrice(DoubleNum.valueOf(response.getHigh()))
                        .lowPrice(DoubleNum.valueOf(response.getLow()))
                        .volume(DoubleNum.valueOf(response.getVolume()))
                        .amount(DoubleNum.valueOf(response.getVolume()))
                        .trades(response.getNumberOfTrades().intValue())
                        .endTime(ZonedDateTime.ofInstant(Instant.ofEpochMilli(response.getCloseTime()), ZoneId.of("UTC")))
                        .timePeriod(Duration.ofMillis(response.getCloseTime() - response.getOpenTime()))
                        .build();
                System.out.println(bar);
            } catch (RuntimeException e){
                System.out.println(e.getMessage());
            }
        });
        while (true);
    }
}
