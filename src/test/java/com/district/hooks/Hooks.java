package com.district.hooks;

import com.district.utils.DriverManager;
import com.district.utils.ScreenshotUtil;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;

/**
 * Cucumber Hooks:
 *  - @Before   : Initialises WebDriver before each scenario
 *  - @After    : Takes screenshot on failure, then quits driver
 *  - @BeforeAll: One-time setup (optional)
 *  - @AfterAll : One-time teardown (optional)
 */
public class Hooks {

    /**
     * Runs before every Cucumber scenario.
     * Initialises the WebDriver using DriverManager.
     */
    @Before(order = 0)
    public void setUp(Scenario scenario) {
        System.out.println("\n─────────────────────────────────────────────");
        System.out.println("▶ Starting Scenario: " + scenario.getName());
        System.out.println("  Tags: " + scenario.getSourceTagNames());
        System.out.println("─────────────────────────────────────────────");
        DriverManager.initDriver();
    }

    /**
     * Runs after every Cucumber scenario.
     * If the scenario FAILED, a screenshot is taken and embedded into the report.
     * Driver is always quit after each scenario.
     */
    @After(order = 0)
    public void tearDown(Scenario scenario) {
        System.out.println("\n─────────────────────────────────────────────");
        System.out.println("◼ Finished Scenario: " + scenario.getName());
        System.out.println("  Status: " + scenario.getStatus());

        try {
            WebDriver driver = DriverManager.getDriver();

            if (scenario.isFailed()) {
                System.out.println("  ❌ FAILED – capturing screenshot ...");

                // 1. Save to disk
                String screenshotPath = ScreenshotUtil.takeScreenshot(
                        driver, "FAILED_" + scenario.getName());
                System.out.println("  Screenshot saved: " + screenshotPath);

                // 2. Embed into Cucumber / Extent report
                byte[] screenshotBytes = ScreenshotUtil.takeScreenshotAsBytes(driver);
                scenario.attach(screenshotBytes, "image/png",
                        "Screenshot – " + scenario.getName());
            }
        } catch (Exception e) {
            System.err.println("Error during screenshot capture in @After hook: " + e.getMessage());
        } finally {
            DriverManager.quitDriver();
            System.out.println("  Driver quit.");
            System.out.println("─────────────────────────────────────────────\n");
        }
    }
}
