package de.y3om11.algotrading.adapter.cucumber.stepdefs;

import de.y3om11.algotrading.adapter.cucumber.enums.TestMarkets;
import de.y3om11.algotrading.adapter.cucumber.testdata.TestDataBuilder;
import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

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

    @Given("^we take the close price$")
    public void setClosePriceIndicator(){
        testDataBuilder.setPriceIndicatorToClosePrice();
    }

    @Given("we use a SMA Indicator with range {int}")
    public void setSmaIndicator(final Integer range){
        testDataBuilder.setPriceIndicatorToSMAIndicator(range);
    }

    @Given("^we enter the market when the Indicator is down$")
    public void enterMarketRuleUnderIndicator(){
        testDataBuilder.setEntryRuleToUnderIndicatorRule();
    }

    @Given("^we exit the market when the Indicator is up$")
    public void exitMarketRuleIndicatorOver(){
        testDataBuilder.setExitRuleToOverIndicatorRule();
    }

    @Given("^we create this Strategy$")
    public void createStrategy(){
        testDataBuilder.createStrategy();
    }

    private long getLongFromDateString(final String dateString){
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        final LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
        return dateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
    }
}
