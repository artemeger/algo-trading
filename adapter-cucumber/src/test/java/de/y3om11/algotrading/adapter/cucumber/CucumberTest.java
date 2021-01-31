package de.y3om11.algotrading.adapter.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"classpath:scenario"},
        plugin = {"pretty"},
        glue = {"de.y3om11.algotrading.adapter.cucumber.stepdefs"})
public class CucumberTest {
}
