package de.y3om11.algotrading.adapter.cucumber.stepdefs;

import de.y3om11.algotrading.adapter.cucumber.testdata.TestData;
import de.y3om11.algotrading.adapter.cucumber.testdata.TestDataBuilder;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.ta4j.core.*;
import org.ta4j.core.analysis.criteria.AverageProfitableTradesCriterion;
import org.ta4j.core.analysis.criteria.ProfitLossPercentageCriterion;
import org.ta4j.core.num.DoubleNum;

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

        System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::");
        System.out.println(":: [[ Strategy " +  name +" ]]");
        System.out.println(":: Total trades " +  tradingRecord.getTradeCount());
        System.out.println(":: Profitable " +  averageProfitableTrades.multipliedBy(DoubleNum.valueOf(100)) + " %");
        System.out.println(":: Total profit " +  profitLossPercentage);
        System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::");
    }
}
