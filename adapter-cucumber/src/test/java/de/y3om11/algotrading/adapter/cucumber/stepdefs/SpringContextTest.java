package de.y3om11.algotrading.adapter.cucumber.stepdefs;

import de.y3om11.algotrading.adapter.cucumber.CucumberTest;
import de.y3om11.algotrading.adapter.cucumber.config.ClassLevelTestExecutionListener;
import de.y3om11.algotrading.adapter.cucumber.config.ContextConfig;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;

@CucumberContextConfiguration
@SpringBootTest(classes = CucumberTest.class)
@ContextConfiguration(classes = ContextConfig.class)
@TestExecutionListeners(ClassLevelTestExecutionListener.class)
public class SpringContextTest {
}
