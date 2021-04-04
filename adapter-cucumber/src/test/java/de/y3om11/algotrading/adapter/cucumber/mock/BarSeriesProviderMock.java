package de.y3om11.algotrading.adapter.cucumber.mock;

import de.y3om11.algotrading.domain.gateway.BarSeriesSerializer;
import de.y3om11.algotrading.adapter.cucumber.enums.TestMarkets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeriesBuilder;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class BarSeriesProviderMock {

    @Value("classpath:data/binance_ada_usdt.json")
    private Resource ada;

    @Value("classpath:data/binance_bnb_usdt.json")
    private Resource bnb;

    @Value("classpath:data/binance_btc_usdt.json")
    private Resource btc;

    @Value("classpath:data/binance_dash_usdt.json")
    private Resource dash;

    @Value("classpath:data/binance_eos_usdt.json")
    private Resource eos;

    @Value("classpath:data/binance_etc_usdt.json")
    private Resource etc;

    @Value("classpath:data/binance_eth_usdt.json")
    private Resource eth;

    @Value("classpath:data/binance_link_usdt.json")
    private Resource link;

    @Value("classpath:data/binance_ltc_usdt.json")
    private Resource ltc;

    @Value("classpath:data/binance_neo_usdt.json")
    private Resource neo;

    @Value("classpath:data/binance_qtum_usdt.json")
    private Resource qtum;

    @Value("classpath:data/binance_trx_usdt.json")
    private Resource trx;

    @Value("classpath:data/binance_xlm_usdt.json")
    private Resource xlm;

    @Value("classpath:data/binance_xmr_usdt.json")
    private Resource xmr;

    @Value("classpath:data/binance_xrp_usdt.json")
    private Resource xrp;

    @Value("classpath:data/binance_zec_usdt.json")
    private Resource zec;

    @Autowired
    private BarSeriesSerializer barSeriesSerializer;

    public BarSeries getBarSeries(final TestMarkets marketPair, long from, long to) {

        final Optional<BarSeries> barSeries;
        switch (marketPair){
            case ADA_USDT:
                barSeries = barSeriesSerializer.getBarSeriesFromJson(asString(ada));
                break;
            case BTC_USDT:
                barSeries = barSeriesSerializer.getBarSeriesFromJson(asString(btc));
                break;
            case ETH_USDT:
                barSeries = barSeriesSerializer.getBarSeriesFromJson(asString(eth));
                break;
            case BNB_USDT:
                barSeries = barSeriesSerializer.getBarSeriesFromJson(asString(bnb));
                break;
            case EOS_USDT:
                barSeries = barSeriesSerializer.getBarSeriesFromJson(asString(eos));
                break;
            case ETC_USDT:
                barSeries = barSeriesSerializer.getBarSeriesFromJson(asString(etc));
                break;
            case LTC_USDT:
                barSeries = barSeriesSerializer.getBarSeriesFromJson(asString(ltc));
                break;
            case NEO_USDT:
                barSeries = barSeriesSerializer.getBarSeriesFromJson(asString(neo));
                break;
            case TRX_USDT:
                barSeries = barSeriesSerializer.getBarSeriesFromJson(asString(trx));
                break;
            case XLM_USDT:
                barSeries = barSeriesSerializer.getBarSeriesFromJson(asString(xlm));
                break;
            case XMR_USDT:
                barSeries = barSeriesSerializer.getBarSeriesFromJson(asString(xmr));
                break;
            case XRP_USDT:
                barSeries = barSeriesSerializer.getBarSeriesFromJson(asString(xrp));
                break;
            case ZEC_USDT:
                barSeries = barSeriesSerializer.getBarSeriesFromJson(asString(zec));
                break;
            case DASH_USDT:
                barSeries = barSeriesSerializer.getBarSeriesFromJson(asString(dash));
                break;
            case LINK_USDT:
                barSeries = barSeriesSerializer.getBarSeriesFromJson(asString(link));
                break;
            case QTUM_USDT:
                barSeries = barSeriesSerializer.getBarSeriesFromJson(asString(qtum));
                break;
            default:
                barSeries = Optional.empty();
        }

        final List<Bar> adjustedBars = barSeries.map(series -> series.getBarData().stream()
                .filter(bar -> bar.getEndTime().toInstant().toEpochMilli() >= from && bar.getBeginTime().toInstant().toEpochMilli() <= to)
                .collect(Collectors.toList()))
                .orElse(new ArrayList<>());

        return new BaseBarSeriesBuilder()
                .withBars(adjustedBars)
                .build();
    }

    private String asString(final Resource resource) {
        try (Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
