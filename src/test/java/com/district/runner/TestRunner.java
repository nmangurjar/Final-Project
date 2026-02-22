package com.district.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * Cucumber JUnit Runner for the full test suite.
 *
 * Reports generated:
 *  - Extent Report  : test-output/ExtentReport.html  (via extent.properties)
 *  - Cucumber HTML  : test-output/cucumber-reports/index.html
 *  - Cucumber JSON  : test-output/cucumber-reports/cucumber.json
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features  = "src/test/resources/features",
        glue      = {
                "com.district.stepdefs",
                "com.district.hooks"
        },
        plugin    = {
                "pretty",
                "html:test-output/cucumber-reports/index.html",
                "json:test-output/cucumber-reports/cucumber.json",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        },
        monochrome = true,
        publish    = false
)
public class TestRunner {
    // No code needed – JUnit + Cucumber handle execution
}
