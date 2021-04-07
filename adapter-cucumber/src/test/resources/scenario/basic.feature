Feature: First Test

  Background:
    Given Candlesticks for Market ETH_USDT from "01-12-2020 10:00" to "01-02-2021 10:00"

  Scenario: Prebuild TripleEMACrossWithADX Strategy
    Given We use the prebuild Strategy "TripleEMACrossWithADX"
    Given For "TripleEMACrossWithADX" we buy with an token order size of 1
    Given For "TripleEMACrossWithADX" the transactional cost is 0.1% and the holding cost is 0%
    When The Strategy "TripleEMACrossWithADX" gets executed we get the stats printed

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

  Scenario: RSI2 Strategy

    Given We define a new indicator Rule called "Sma5_Over_Sma200" of type "OVER" using "CLOSE" price and a "SMA" Indicator with range 5 and a "SMA" Indicator with range 200
    Given We define a new threshold Rule called "Rsi2_CrossedUnder_5" of type "CROSSED_UNDER" using "CLOSE" price and a "RSI" Indicator with range 5 and a threshold of 40
    Given We define a new range Rule called "ClosePrice_Over_Sma5" of type "OVER" using "CLOSE" price and a "SMA" Indicator with range 5

    Given We define a new indicator Rule called "Sma5_Under_Sma200" of type "UNDER" using "CLOSE" price and a "SMA" Indicator with range 5 and a "SMA" Indicator with range 200
    Given We define a new threshold Rule called "Rsi2_CrossedOver_5" of type "CROSSED_OVER" using "CLOSE" price and a "RSI" Indicator with range 5 and a threshold of 60
    Given We define a new range Rule called "ClosePrice_Under_Sma5" of type "UNDER" using "CLOSE" price and a "SMA" Indicator with range 5

    Given We define a new stop loss Rule called "Stop_Loss" allowing 0.5% slippage

    Given We merge the rules "Sma5_Over_Sma200:Rsi2_CrossedUnder_5:ClosePrice_Over_Sma5" to a new rule called "entryRule" using the "AND" operator
    Given We merge the rules "Sma5_Under_Sma200:Rsi2_CrossedOver_5:ClosePrice_Under_Sma5" to a new rule called "exitRule" using the "AND" operator
    Given We merge the rules "exitRule:Stop_Loss" to a new rule called "exitWithStopLoss" using the "OR" operator

    Given We build a Strategy called "RSI2" with a delay of 200 ticks using "entryRule" to enter and "exitWithStopLoss" to exit the trade
    Given For "RSI2" we buy with an token order size of 0.001
    Given For "RSI2" the transactional cost is 0.1% and the holding cost is 0%
    When The Strategy "RSI2" gets executed we get the stats printed
