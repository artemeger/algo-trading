package de.y3om11.algotrading.adapter.cucumber.stepdefs;

import de.y3om11.algotrading.adapter.cucumber.testdata.TestData;
import de.y3om11.algotrading.adapter.cucumber.testdata.TestDataBuilder;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.ta4j.core.*;
import org.ta4j.core.analysis.criteria.AverageProfitableTradesCriterion;
import org.ta4j.core.analysis.criteria.ProfitLossPercentageCriterion;
import org.ta4j.core.num.DoubleNum;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RunDataStepdefs extends SpringContextTest {

    @Autowired
    private TestDataBuilder testDataBuilder;

    @When("The Strategy {string} gets executed we get the stats printed")
    public void executeStrategy(final String name){
        final TestData testData = testDataBuilder.build();
        var seriesManager = new BarSeriesManager(testData.barSeries, testData.transactionCostModel.get(name),
                testData.holdingCostModel.get(name));
        var tradingRecord = seriesManager.run(testData.strategy.get(name), testData.orderType.get(name),
                testData.orderSize.get(name));

        var profitLossPercentageCriterion = new ProfitLossPercentageCriterion();
        var profitLossPercentage = profitLossPercentageCriterion.calculate(testData.barSeries, tradingRecord);
        var averageProfitableTradesCriterion = new AverageProfitableTradesCriterion();
        var averageProfitableTrades = averageProfitableTradesCriterion.calculate(testData.barSeries, tradingRecord);

        var sortedTrades = tradingRecord.getTrades().stream()
                .sorted(Comparator.comparingDouble(t -> t.getProfit().doubleValue()))
                .collect(Collectors.toList());

        var worst10Trades = sortedTrades.subList(0, 10);
        var best10Trades = sortedTrades.subList(sortedTrades.size() - 10, sortedTrades.size());
        Collections.reverse(best10Trades);

        System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::");
        System.out.println(":: [[ Strategy " +  name +" ]]");
        System.out.println(":: Total trades " +  tradingRecord.getTradeCount());
        System.out.println(":: Profitable " +  averageProfitableTrades.multipliedBy(DoubleNum.valueOf(100)) + " %");
        System.out.println(":: Total profit " +  profitLossPercentage);
        System.out.println(":: ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ ");
        System.out.println("::               BEST 10 TRADES                   ");
        printTrades(best10Trades, testData.barSeries);
        System.out.println(":: ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ ");
        System.out.println("::               WORST 10 TRADES                  ");
        printTrades(worst10Trades, testData.barSeries);
        System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::");
    }

    private void printTrades(final List<Trade> trades, final BarSeries barSeries){
        var df = new DecimalFormat("#########.#########");
        trades.forEach(t -> System.out.printf(":: Entry %s (%s)  Exit %s (%s)  Fee %s  Profit %s%n",
                df.format(t.getEntry().getPricePerAsset().doubleValue()), barSeries.getBar(t.getExit().getIndex()).getEndTime(),
                df.format(t.getExit().getPricePerAsset().doubleValue()), barSeries.getBar(t.getEntry().getIndex()).getEndTime(),
                df.format(t.getTradeCost().doubleValue()),
                df.format(t.getProfit().doubleValue())));
    }
}
