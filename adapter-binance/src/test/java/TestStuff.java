import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiWebSocketClient;

import com.binance.api.client.domain.market.CandlestickInterval;
import de.y3om11.algotrading.domain.constants.MarketPair;
import de.y3om11.algotrading.domain.constants.TimeInterval;
import de.y3om11.algotrading.adapter.binance.MarketProviderImpl;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBar;
import org.ta4j.core.num.PrecisionNum;

import java.time.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class TestStuff {

    private final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

    @Test
    @Disabled
    void test(){
        BinanceApiWebSocketClient client = BinanceApiClientFactory.newInstance().newWebSocketClient();

        // Obtain 1m candlesticks in real-time for ETH/BTC
        client.onCandlestickEvent("ethbtc", CandlestickInterval.ONE_MINUTE, response -> {
            try {
                final BaseBar bar = BaseBar.builder()
                        .openPrice(PrecisionNum.valueOf(response.getOpen()))
                        .closePrice(PrecisionNum.valueOf(response.getClose()))
                        .highPrice(PrecisionNum.valueOf(response.getHigh()))
                        .lowPrice(PrecisionNum.valueOf(response.getLow()))
                        .volume(PrecisionNum.valueOf(response.getVolume()))
                        .amount(PrecisionNum.valueOf(response.getVolume()))
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

    @Test
    @Disabled
    void test2() throws InterruptedException {
        MarketProviderImpl testee = new MarketProviderImpl(executor);
        BarSeries resultETH = testee.getBarSeries(MarketPair.ETH_BTC, TimeInterval.ONE_MINUTE, 1000);
        BarSeries resultADA = testee.getBarSeries(MarketPair.ADA_BTC, TimeInterval.ONE_MINUTE, 1000);
        BarSeries resultDOT = testee.getBarSeries(MarketPair.DOT_BTC, TimeInterval.ONE_MINUTE, 1000);
        while (true){
            Thread.sleep(1000L);
            System.out.println("----------------------------");
            resultETH.getBarData().forEach(System.out::println);
            System.out.println("----------------------------");
            System.out.println("----------------------------");
            resultADA.getBarData().forEach(System.out::println);
            System.out.println("----------------------------");
            System.out.println("----------------------------");
            resultDOT.getBarData().forEach(System.out::println);
            System.out.println("----------------------------");
        }
    }
}
