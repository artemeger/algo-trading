import de.y3om11.algotrading.domain.constants.MarketPair;
import de.y3om11.algotrading.domain.constants.TimeInterval;
import de.y3om11.algotrading.adapter.binance.MarketProviderImpl;
import de.y3om11.algotrading.adapter.serialization.GenericJsonSerializer;
import org.junit.jupiter.api.Test;
import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class DataCollector {

    private final GenericJsonSerializer jsonSerializer = new GenericJsonSerializer();
    private final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

    @Test
    void collectMarketData() throws IOException {

        MarketProviderImpl testee = new MarketProviderImpl(executor);
        BarSeries resultETHBTC = testee.getBarSeries(MarketPair.ETH_BTC, TimeInterval.FIVE_MINUTE, 144);
        BarSeries resultDOTBTC = testee.getBarSeries(MarketPair.DOT_BTC, TimeInterval.FIVE_MINUTE, 144);
        BarSeries resultADABTC = testee.getBarSeries(MarketPair.ADA_BTC, TimeInterval.FIVE_MINUTE, 144);
        BarSeries resultLTCBTC = testee.getBarSeries(MarketPair.LTC_BTC, TimeInterval.FIVE_MINUTE, 144);
        BarSeries resultNEOBTC = testee.getBarSeries(MarketPair.NEO_BTC, TimeInterval.FIVE_MINUTE, 144);
        BarSeries resultBNBBTC = testee.getBarSeries(MarketPair.BNB_BTC, TimeInterval.FIVE_MINUTE, 144);
        BarSeries resultETHUSDT = testee.getBarSeries(MarketPair.ETH_USDT, TimeInterval.FIVE_MINUTE, 144);
        BarSeries resultBTCUSDT = testee.getBarSeries(MarketPair.BTC_USDT, TimeInterval.FIVE_MINUTE, 144);

        while(resultETHBTC.getBarCount() < 144 ||
                resultDOTBTC.getBarCount() < 144 ||
                resultADABTC.getBarCount() < 144 ||
                resultLTCBTC.getBarCount() < 144 ||
                resultNEOBTC.getBarCount() < 144 ||
                resultBNBBTC.getBarCount() < 144 ||
                resultETHUSDT.getBarCount() < 144 ||
                resultBTCUSDT.getBarCount() < 144);

        final String jsonETHBTC = jsonSerializer.getStringFromRequestObject(resultETHBTC.getBarData().toArray(Bar[]::new));
        Files.writeString(Path.of(resultETHBTC.getFirstBar().getBeginTime() + "_ETH_BTC.json"), jsonETHBTC);
        final String jsonDOTBTC = jsonSerializer.getStringFromRequestObject(resultDOTBTC.getBarData().toArray(Bar[]::new));
        Files.writeString(Path.of(resultDOTBTC.getFirstBar().getBeginTime() + "_DOT_BTC.json"), jsonDOTBTC);
        final String jsonADABTC = jsonSerializer.getStringFromRequestObject(resultADABTC.getBarData().toArray(Bar[]::new));
        Files.writeString(Path.of(resultADABTC.getFirstBar().getBeginTime() + "_ADA_BTC.json"), jsonADABTC);
        final String jsonLTCBTC = jsonSerializer.getStringFromRequestObject(resultLTCBTC.getBarData().toArray(Bar[]::new));
        Files.writeString(Path.of(resultLTCBTC.getFirstBar().getBeginTime() + "_LTC_BTC.json"), jsonLTCBTC);
        final String jsonNEOBTC = jsonSerializer.getStringFromRequestObject(resultNEOBTC.getBarData().toArray(Bar[]::new));
        Files.writeString(Path.of(resultNEOBTC.getFirstBar().getBeginTime() + "_NEO_BTC.json"), jsonNEOBTC);
        final String jsonBNBBTC = jsonSerializer.getStringFromRequestObject(resultBNBBTC.getBarData().toArray(Bar[]::new));
        Files.writeString(Path.of(resultBNBBTC.getFirstBar().getBeginTime() + "_BNB_BTC.json"), jsonBNBBTC);
        final String jsonETHUSDT = jsonSerializer.getStringFromRequestObject(resultETHUSDT.getBarData().toArray(Bar[]::new));
        Files.writeString(Path.of(resultETHUSDT.getFirstBar().getBeginTime() + "_ETH_USDT.json"), jsonETHUSDT);
        final String jsonBTCUSDT = jsonSerializer.getStringFromRequestObject(resultBTCUSDT.getBarData().toArray(Bar[]::new));
        Files.writeString(Path.of(resultBTCUSDT.getFirstBar().getBeginTime() + "_BTC_USDT.json"), jsonBTCUSDT);

    }
}
