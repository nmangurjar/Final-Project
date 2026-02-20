package components;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class Movies {
    public static void getAvailableLanguages(WebDriver driver) {
        driver.findElement(By.xpath(MovieComponentLocators.moviesComponent)).click();
        // Since DOM elements will be refreshed after Re-rendering and selenium requires sometime to save those changes!
        Constants.waitFor(5);

        WebElement filterSection = Constants.waitUntil(driver, 5).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(MovieComponentLocators.filterSection)));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'})", filterSection);
        driver.findElement(By.xpath(EventComponentLocators.filterBtn)).click();

        driver.findElement(By.xpath(MovieComponentLocators.languageFilter)).click();

        List<WebElement> languages = driver.findElements(By.xpath(MovieComponentLocators.language));

        for(WebElement language: languages) {
            System.out.println(language.getText());
        }

        driver.findElement(By.xpath(EventComponentLocators.applyFilters)).click();
    }
}