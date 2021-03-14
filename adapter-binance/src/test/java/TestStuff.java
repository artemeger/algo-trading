import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiWebSocketClient;
import com.binance.api.client.domain.market.CandlestickInterval;
import de.y3om11.algotrader.domain.entity.*;
import org.junit.jupiter.api.Test;
import java.util.HashMap;

public class TestStuff {

    @Test
    void test(){
        final CandlestickSeries testee = new CandlestickSeriesBuilder()
                .withTimeframe(Timeframe.ONE_MINUTE)
                .withCandleSticks(new HashMap<>())
                .build();
            BinanceApiWebSocketClient client = BinanceApiClientFactory.newInstance().newWebSocketClient();
            // Obtain 1m candlesticks in real-time for ETH/BTC
            client.onCandlestickEvent("ethbtc", CandlestickInterval.ONE_MINUTE, response -> {
                final Candlestick candleStick = new CandlestickBuilder()
                        .withOpen(Double.parseDouble(response.getOpen()))
                        .withClose(Double.parseDouble(response.getClose()))
                        .withHigh(Double.parseDouble(response.getHigh()))
                        .withLow(Double.parseDouble(response.getLow()))
                        .withVolume(Double.parseDouble(response.getVolume()))
                        .withNumberOfTrades(response.getNumberOfTrades())
                        .withOpenTime(response.getOpenTime())
                        .build();
                testee.addCandlestick(candleStick);
          });
        while (true);
    }
}
