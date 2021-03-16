package de.y3om11.algotrading.adapter.binance;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiWebSocketClient;
import de.y3om11.algotrader.domain.constants.MarketPair;
import de.y3om11.algotrader.domain.constants.TimeInterval;
import de.y3om11.algotrader.domain.gateway.BarSeriesProvider;
import org.ta4j.core.*;
import org.ta4j.core.num.PrecisionNum;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class BarSeriesProviderImpl implements BarSeriesProvider {

    final Map<MarketPair, BarSeries> barSeriesMap = new ConcurrentHashMap<>();
    final Map<MarketPair, Long> closeTimeCache = new ConcurrentHashMap<>();
    final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

    @Override
    public BarSeries getBarSeries(final MarketPair marketPair, final TimeInterval timeInterval, final int maxBarCount) {

        if(barSeriesMap.containsKey(marketPair)) return barSeriesMap.get(marketPair);

        final List<Bar> barList = new ArrayList<>();
        final BaseBarSeries baseBarSeries = new BaseBarSeriesBuilder()
                .withBars(barList)
                .withMaxBarCount(maxBarCount)
                .withName(MarketPairMapper.map(marketPair))
                .build();
        barSeriesMap.put(marketPair, baseBarSeries);

        executor.submit(() -> {
            final BinanceApiWebSocketClient client = BinanceApiClientFactory.newInstance().newWebSocketClient();
            client.onCandlestickEvent(MarketPairMapper.map(marketPair), TimeIntervalMapper.map(timeInterval), response -> {
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
                            .timePeriod(Duration.ofMillis(response.getCloseTime() - response.getOpenTime() + 1))
                            .build();

                    if(!closeTimeCache.containsKey(marketPair)) closeTimeCache.put(marketPair, response.getCloseTime());

                    if(closeTimeCache.get(marketPair).equals(response.getCloseTime())){
                        baseBarSeries.addBar(bar, true);
                    } else {
                        baseBarSeries.addBar(bar);
                        closeTimeCache.put(marketPair, response.getCloseTime());
                    }

                } catch (RuntimeException e){
                    System.out.println(e.getMessage());
                }
            });
        });
        return baseBarSeries;
    }
}
