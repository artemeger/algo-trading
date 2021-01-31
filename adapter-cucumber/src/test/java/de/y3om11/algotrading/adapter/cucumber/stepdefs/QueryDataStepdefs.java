package de.y3om11.algotrading.adapter.cucumber.stepdefs;

import io.cucumber.java8.En;

public class QueryDataStepdefs extends SpringContextTest implements En {

    public QueryDataStepdefs() {
        Then("Execute some evaluation with parameter {string}",
                (final String parameter) -> System.out.println(parameter));
    }
}
