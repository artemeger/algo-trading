package de.y3om11.algotrading.adapter.cucumber.stepdefs;

import de.y3om11.algotrading.adapter.cucumber.CucumberTest;
import de.y3om11.algotrading.adapter.cucumber.config.ContextConfig;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = CucumberTest.class)
@ContextConfiguration(classes = ContextConfig.class)
public class SpringContextTest {
}
