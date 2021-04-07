package de.y3om11.algotrading.adapter.cucumber.stepdefs;

import de.y3om11.algotrading.adapter.cucumber.enums.TestMarkets;
import de.y3om11.algotrading.adapter.cucumber.testdata.TestDataBuilder;
import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;
import org.ta4j.core.Order;
import org.ta4j.core.cost.LinearTransactionCostModel;
import org.ta4j.core.cost.ZeroCostModel;
import org.ta4j.core.num.DoubleNum;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.stream.Collectors;

public class InputDataStepdefs extends SpringContextTest {

    @Autowired
    private TestDataBuilder testDataBuilder;

    @Given("Candlesticks for Market {word} from {string} to {string}")
    public void getCandleSticksForMarket(final String marketString, final String fromString, final String toString){
        final TestMarkets market = TestMarkets.valueOf(marketString);
        final long fromDate = getLongFromDateString(fromString);
        final long toDate = getLongFromDateString(toString);
        testDataBuilder.setBarSeriesFromPath(market, fromDate, toDate);
    }

    @Given("We define a new range Rule called {string} of type {string} using {string} price and a {string} Indicator with range {int}")
    public void defineRule(final String ruleName, final String ruleType, final String priceIndicator,
                           final String cachedIndicator, final int range){
        testDataBuilder.addRangeRule(ruleName, ruleType, cachedIndicator, priceIndicator, range);
    }

    @Given("We define a new indicator Rule called {string} of type {string} using {string} price and a {string} Indicator with range {int} and a {string} Indicator with range {int}")
    public void defineRule(final String ruleName, final String ruleType, final String priceIndicator,
                           final String cachedIndicator1, final int range1,
                           final String cachedIndicator2, final int range2) {
        testDataBuilder.addIndicatorRule(ruleName, ruleType, priceIndicator, cachedIndicator1, range1, cachedIndicator2, range2);
    }

        @Given("We define a new threshold Rule called {string} of type {string} using {string} price and a {string} Indicator with range {int} and a threshold of {double}")
    public void defineRule(final String ruleName, final String ruleType, final String priceIndicator,
                           final String cachedIndicator, final int range, final double threshold){
        testDataBuilder.addThresholdRule(ruleName, ruleType, cachedIndicator, priceIndicator, range, threshold);
    }

    @Given("We define a new boolean Rule called {string} of type {string}")
    public void defineRule(final String ruleName, final String ruleType){
        testDataBuilder.addBooleanRule(ruleName, ruleType);
    }

    @Given("We define a new stop loss Rule called {string} allowing {double}% slippage")
    public void weDefineANewStopLossRuleCalledStringAllowingSlippage(final String rule, final double percent) {
        testDataBuilder.addStopRule(rule, "STOP_LOSS", percent);
    }

    @Given("We define a new stop gain Rule called {string} setting a {double}% target")
    public void weDefineANewStopGainRuleCalledSettingATarget(final String rule, final double percent) {
        testDataBuilder.addStopRule(rule, "STOP_GAIN", percent);
    }

    @Given("We merge the rules {string} to a new rule called {string} using the {string} operator")
    public void combine2Rules(final String rules, final String newRuleName, final String operator){
        var ruleList = Arrays.stream(rules.split(":")).collect(Collectors.toList());
        switch(operator){
            case "AND" -> testDataBuilder.combineRuleListWithAnd(newRuleName, ruleList);
            case "OR" -> testDataBuilder.combineRuleListWithOr(newRuleName, ruleList);
        }
    }

    @Given("We build a Strategy called {string} with a delay of {int} ticks using {string} to enter and {string} to exit the trade")
    public void weBuildAStrategyCalledUsingToEnterAndToExitTheTrade(final String name, final int unstable,
                                                                    final String entryRule, final String exitRule) {
        testDataBuilder.createNewStrategy(name, entryRule, exitRule, unstable);
    }

    @Given("We use the prebuild Strategy {string}")
    public void createStrategy(final String strategyName){
        testDataBuilder.setDefaultStrategy(strategyName);
    }

    @Given("For {string} we buy with an token order size of {double}")
    public void forWeBuyWithAnOrderSizeOfToken(final String name, final double orderSize) {
        testDataBuilder.setOrderTypeAndSize(name, Order.OrderType.BUY, DoubleNum.valueOf(orderSize));
    }

    @Given("For {string} we sell with an token order size of {double}")
    public void forWeSellWithAnOrderSizeOfToken(final String name, final double orderSize) {
        testDataBuilder.setOrderTypeAndSize(name, Order.OrderType.SELL, DoubleNum.valueOf(orderSize));
    }

    @Given("For {string} the transactional cost is {double}% and the holding cost is {double}%")
    public void forTheTransactionalCostAndTheHoldingCost(final String name, final double txCost, final double holdingCost) {
        final var txCostPercent = txCost * 0.01;
        final var holdingCostPercent = holdingCost * 0.01;
        testDataBuilder.setTransactionCostModel(name, txCostPercent == 0 ?
                new ZeroCostModel() :
                new LinearTransactionCostModel(txCostPercent));
        testDataBuilder.setHoldingCostModel(name, holdingCostPercent == 0 ?
                new ZeroCostModel() :
                new LinearTransactionCostModel(holdingCostPercent));
    }

    private long getLongFromDateString(final String dateString){
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        final LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
        return dateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
    }
}
