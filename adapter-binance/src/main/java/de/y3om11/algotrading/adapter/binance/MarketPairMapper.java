package de.y3om11.algotrading.adapter.binance;

import de.y3om11.algotrading.domain.constants.MarketPair;

public final class MarketPairMapper {

    public static String map(final MarketPair marketPair){
        return switch (marketPair) {
            case ETH_BTC -> "ethbtc";
            case ADA_BTC -> "adabtc";
            case DOT_BTC -> "dotbtc";
            case BNB_BTC -> "bnbbtc";
            case LTC_BTC -> "ltcbtc";
            case NEO_BTC -> "neobtc";
            case BTC_USDT -> "btcusdt";
            case ETH_USDT -> "ethusdt";
        };
    }
}
