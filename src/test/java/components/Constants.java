package components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Constants {
    public static final String URL = "https://www.district.in/";

    public static WebDriverWait waitUntil(WebDriver driver, int seconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds));
    }
}

class HomeComponentLocators {
    public static final String selectLocationButton = "//div[@id = 'master-header']//button/div";
    public static final String searchBox = "//input[@placeholder = 'Search city, area or locality']";
    // The first button tag is the matched result!
    public static final String requiredLocation = "//div[contains(@class, 'dds-no-scrollbar')]/div/button";
    public static final String moviesComponent = "//a[@href = '/movies/']";
}

class EventComponentLocators {
    public static final String eventsComponent = "//a[@href = '/events/']";

    public static final String thisWeekendBtn = "//button[@aria-label = 'This Weekend']";
    public static final String filterBtn = "//button[@aria-label = 'Filters']";
    public static final String filterLabel = "//label[@for = 'event_price_sort_Price : Low to High']";
    public static final String applyFilters = "//button[@aria-label = 'Apply Filters']";

    public static final String eventAnchor = "//div[contains(@class,'dds-grid')]/a";
}

class MovieComponentLocators {
    public static final String moviesComponent = "//a[@href = '/movies/']";
    public static final String filterSection = "//div[@id = 'filters-content']";
    public static final String languageFilter = "//div[@class='dds-relative']//span[text()='Language']";
    public static final String language = "//span[@class = 'checkboxMaterial']/following-sibling::span";
}

class LoginLocators {
    public static final String profile = "//div[@aria-label = 'User Avatar']";
    public static final String mobileNumberField = "//input[@name = 'mobileNumber']";
    public static final String continueBtn = "//button[text() = 'Continue']";
}