package de.y3om11.algotrading.adapter.cucumber.stepdefs;

import io.cucumber.java8.En;

public class InputDataStepdefs extends SpringContextTest implements En {

    public InputDataStepdefs(){
        Given("Some test data {string} was configured", (final String testdata) -> System.out.println(testdata));
    }
}
