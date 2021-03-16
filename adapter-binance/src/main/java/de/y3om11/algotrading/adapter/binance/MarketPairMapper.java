package de.y3om11.algotrading.adapter.binance;

import de.y3om11.algotrader.domain.constants.MarketPair;
import de.y3om11.algotrader.domain.exception.AlgotradingTechnicalException;

import static java.lang.String.format;

public final class MarketPairMapper {

    public static String map(final MarketPair marketPair){
        switch (marketPair){
            case ETH_BTC:
                return "ethbtc";
            case ADA_BTC:
                return "adabtc";
            case DOT_BTC:
                return "dotbtc";
            case BNB_BTC:
                return "bnbbtc";
            case LTC_BTC:
                return "ltcbtc";
            case NEO_BTC:
                return "neobtc";
            case BTC_USDT:
                return "btcusdt";
            case ETH_USDT:
                return "ethusdt";
            default:
                throw new AlgotradingTechnicalException(format("No mapping for market %s found.", marketPair.name()));
        }
    }
}
