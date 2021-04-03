package de.y3om11.algotrading.adapter.cucumber.stepdefs;

import de.y3om11.algotrading.adapter.cucumber.testdata.TestData;
import de.y3om11.algotrading.adapter.cucumber.testdata.TestDataBuilder;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.ta4j.core.*;
import org.ta4j.core.analysis.criteria.TotalProfitCriterion;
import org.ta4j.core.num.DoubleNum;
import org.ta4j.core.num.Num;

public class RunDataStepdefs extends SpringContextTest {

    @Autowired
    private TestDataBuilder testDataBuilder;

    @When("the Strategy gets executed")
    public void executeStrategy(){
        final TestData testData = testDataBuilder.build();

        BarSeriesManager seriesManager = new BarSeriesManager(testData.barSeries);
        TradingRecord tradingRecord3DaySma = seriesManager.run(testData.strategy, Order.OrderType.BUY, DoubleNum.valueOf(50));

        AnalysisCriterion criterion = new TotalProfitCriterion();
        Num calculate3DaySma = criterion.calculate(testData.barSeries, tradingRecord3DaySma);

        System.out.println("Total Trades: " +  tradingRecord3DaySma.getTradeCount());
        System.out.println("Total Profit: " +  calculate3DaySma);
    }
}
