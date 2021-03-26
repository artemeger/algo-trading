Feature: First Test

  Background:
    Given Candlesticks for Market BTC_USDT from "01-01-2021 10:00" to "02-01-2021 10:00"

  Scenario: SMA Strategy with minute candles and range 15
    Given we take the close price
    Given we use a SMA Indicator with range 15
    Given we enter the market when the Indicator is down
    Given we exit the market when the Indicator is up
    Given we create this Strategy
    When the Strategy gets executed


  Scenario: SMA Strategy with minute candles and range 30
    Given we take the close price
    Given we use a SMA Indicator with range 30
    Given we enter the market when the Indicator is down
    Given we exit the market when the Indicator is up
    Given we create this Strategy
    When the Strategy gets executed

