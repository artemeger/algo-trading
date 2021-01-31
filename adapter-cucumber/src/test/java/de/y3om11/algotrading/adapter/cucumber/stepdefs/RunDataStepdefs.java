package de.y3om11.algotrading.adapter.cucumber.stepdefs;

import io.cucumber.java8.En;

public class RunDataStepdefs extends SpringContextTest implements En {
    public RunDataStepdefs() {
        When("Some process gets executed with the id {int}", (final Integer id) -> System.out.println(id));
    }
}
