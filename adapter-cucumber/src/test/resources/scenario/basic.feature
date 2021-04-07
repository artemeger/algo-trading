Feature: First Test

  Background:
    Given Candlesticks for Market BTC_USDT from "01-12-2020 10:00" to "01-02-2021 10:00"

  Scenario: Prebuild TripleEMACross Strategy
    Given We use the prebuild Strategy "TripleEMACross"
    Given For "TripleEMACross" we buy with an token order size of 0.001
    Given For "TripleEMACross" the transactional cost is 0.1% and the holding cost is 0%
    When The Strategy "TripleEMACross" gets executed we get the stats printed

  Scenario: EMA9RSIScalp Strategy

    Given We define a new range Rule called "HighPrice_Over_Ema9" of type "OVER" using "HIGH" price and a "EMA" Indicator with range 9
    Given We define a new threshold Rule called "ClosePrice_Rsi18_Over_40" of type "OVER" using "CLOSE" price and a "RSI" Indicator with range 18 and a threshold of 40
    Given We define a new threshold Rule called "ClosePrice_Rsi18_Over_70" of type "OVER" using "HIGH" price and a "RSI" Indicator with range 18 and a threshold of 70
    Given We define a new boolean Rule called "BullishEngulfing" of type "BULLISH_ENGULFING"

    Given We define a new stop loss Rule called "Stop_Loss" allowing 0.1% slippage
    Given We define a new stop gain Rule called "Stop_Gain" setting a 0.5% target

    Given We merge the rules "HighPrice_Over_Ema9:ClosePrice_Rsi18_Over_40:BullishEngulfing" to a new rule called "entryRule" using the "AND" operator
    Given We merge the rules "Stop_Gain:ClosePrice_Rsi18_Over_70" to a new rule called "exitRule" using the "OR" operator

    Given We build a Strategy called "EMA9RSIScalp" with a delay of 20 ticks using "entryRule" to enter and "exitRule" to exit the trade
    Given For "EMA9RSIScalp" we buy with an token order size of 0.001
    Given For "EMA9RSIScalp" the transactional cost is 0.1% and the holding cost is 0%
    When The Strategy "EMA9RSIScalp" gets executed we get the stats printed

