package de.y3om11.algotrading.adapter.cucumber.stepdefs;

import io.cucumber.java.en.Then;

public class QueryDataStepdefs extends SpringContextTest {

    @Then("Execute some evaluation with parameter {string}")
    public void evaluation(final String parameter){
        System.out.println(parameter);
    }
}
