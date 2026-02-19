package components;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.ConfigFileManager;

public class Home {

    public static void setLocation(WebDriver driver) {
        ConfigFileManager configFile = new ConfigFileManager();
        configFile.loadConfig();
        String location = configFile.getCity();

        WebDriverWait wait = Constants.waitUntil(driver, 10);
        WebElement selectLocation = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(HomeComponentLocators.selectLocationButton)));

        try {
            // Since District is setting the location by default so we should wait until it completes-
            // If not, then our preferred location will be overwritten or it would not be selected at all!
            Thread.sleep(5_000);
        } catch(InterruptedException ie) {
            System.out.println(ie.getMessage());
        }

        selectLocation.click();
        driver.findElement(By.xpath(HomeComponentLocators.searchBox)).sendKeys(location);

        WebDriverWait waitForLocations = Constants.waitUntil(driver, 5);
        waitForLocations.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(HomeComponentLocators.requiredLocation))).click();
    }

    public static void navigateToEventPage(WebDriver driver) {
        WebElement events = driver.findElement(By.xpath(EventComponentLocators.eventsComponent));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", events);
    }
}