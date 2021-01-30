import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiWebSocketClient;
import com.binance.api.client.domain.market.CandlestickInterval;
import de.y3om11.algotrader.domain.entity.CandlestickSeries;
import de.y3om11.algotrader.domain.entity.CandlestickSeriesBuilder;
import de.y3om11.algotrader.domain.entity.Timeframe;
import org.junit.jupiter.api.Test;
import org.nd4j.linalg.factory.Nd4j;
import java.util.HashMap;

public class TestStuff {

    @Test
    void test(){
        final CandlestickSeries testee = new CandlestickSeriesBuilder()
                .withTimeframe(Timeframe.ONE_MINUTE)
                .withCandleSticks(new HashMap<>())
                .build();
        while (true) {
            BinanceApiWebSocketClient client = BinanceApiClientFactory.newInstance().newWebSocketClient();
            // Obtain 1m candlesticks in real-time for ETH/BTC
            client.onCandlestickEvent("ethbtc", CandlestickInterval.ONE_MINUTE, response -> {
                if(response.getBarFinal()) {
                    double open = Double.parseDouble(response.getOpen());
                    double close = Double.parseDouble(response.getClose());
                    double high = Double.parseDouble(response.getHigh());
                    double low = Double.parseDouble(response.getLow());
                    double volume = Double.parseDouble(response.getVolume());
                    double time = response.getOpenTime();
                    double[] vectorDouble = new double[]{open, close, high, low, volume, time};
                    testee.addCandlestick(response.getOpenTime(), Nd4j.create(vectorDouble, 6, 1));
                }
          });
        }
    }
}
