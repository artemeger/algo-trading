Feature: First Test

  Background:
    Given Candlesticks for Market BTC_USDT from "01-02-2021 10:00" to "02-02-2021 10:00"

  Scenario: Some easy test scenario
    When Some process gets executed with the id 1
    Then Execute some evaluation with parameter "testparameter"