package de.y3om11.algotrading.adapter.cucumber.stepdefs;

import de.y3om11.algotrading.adapter.cucumber.enums.TestMarkets;
import de.y3om11.algotrading.adapter.cucumber.testdata.TestDataBuilder;
import io.cucumber.java8.En;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class InputDataStepdefs extends SpringContextTest implements En {

    @Autowired
    private TestDataBuilder testDataBuilder;

    public InputDataStepdefs(){
        Given("Some test data {string} was configured", (final String testdata) -> {
            System.out.println("here");
        });

        Given("Candlesticks for Market {word} from {string} to {string}",
                (final String marketString, final String fromString, final String toString) -> {
                    final TestMarkets market = TestMarkets.valueOf(marketString);
                    final long fromDate = getLongFromDateString(fromString);
                    final long toDate = getLongFromDateString(toString);
            testDataBuilder.setBarSeriesFromPath(market, fromDate, toDate);
        });
    }

    private long getLongFromDateString(final String dateString){
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        final LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
        return dateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
    }
}
