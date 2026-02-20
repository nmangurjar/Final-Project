package components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class Events {
    private static void selectThisWeekendButton(WebDriver driver) {
        WebElement wkndBtn = Constants.waitUntil(driver, 5).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(EventComponentLocators.thisWeekendBtn)));
        Actions actions = new Actions(driver);

        actions.moveToElement(wkndBtn).click().perform();
    }

    private static void applyPriceFilter(WebDriver driver) {
        driver.findElement(By.xpath(EventComponentLocators.filterBtn)).click();
        driver.findElement(By.xpath(EventComponentLocators.filterLabel)).click();
        driver.findElement(By.xpath(EventComponentLocators.applyFilters)).click();
    }

    private static void saveEvents(WebDriver driver) {
        List<WebElement> events = Constants.waitUntil(driver, 10).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(EventComponentLocators.eventDiv)));
        for(WebElement event: events) {
            System.out.println(event.findElement(By.xpath("./parent::a")).getAttribute("href"));
            System.out.println(event.getText());
        }
    }

    public static void getEvents(WebDriver driver) {
        // Since DOM elements will be refreshed after Re-rendering and selenium requires sometime to save those changes!
        Constants.waitFor(5);
        Events.selectThisWeekendButton(driver);

        Events.applyPriceFilter(driver);

        // Since DOM elements will be refreshed after Re-rendering and selenium requires sometime to save those changes!
        Constants.waitFor(5);
        Events.saveEvents(driver);
    }
}