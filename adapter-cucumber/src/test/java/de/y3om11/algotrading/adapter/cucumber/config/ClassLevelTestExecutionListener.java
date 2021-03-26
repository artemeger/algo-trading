package de.y3om11.algotrading.adapter.cucumber.config;

import org.springframework.test.context.TestContext;
import org.springframework.test.context.web.ServletTestExecutionListener;

public class ClassLevelTestExecutionListener extends ServletTestExecutionListener {

    @Override
    public void beforeTestClass(TestContext testContext) throws Exception {
        super.beforeTestMethod(testContext);
    }

    @Override
    public void afterTestClass(TestContext testContext) throws Exception {
        super.afterTestMethod(testContext);
    }
}
