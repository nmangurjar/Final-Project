package com.district.pages;

import com.district.utils.WaitHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Page Object for the Movies page (https://www.district.in/movies/).
 * Covers:
 *  - TC-19 to TC-21  : navigation and listing
 *  - TC-22 to TC-25  : Filters panel and Language extraction
 */
public class MoviesPage {

    private final WebDriver driver;
    private final WaitHelper wait;

    // ── Locators ──────────────────────────────────────────────────────────────

    // Filters button on movies page
    private final By filtersBtn  = By.cssSelector("button[aria-label='Filters']");

    // Movie cards
    private final By movieCards = By.tagName("h2");

    // Language section heading inside filter panel
    // The filter panel is NOT inside a [role='dialog'] – it's a side panel
    // The Language tab is a <span> with the exact text "Language"
    private final By languageSectionSpan = By.xpath(
            "//span[normalize-space(text())='Language']");

    // Language option items – after clicking Language tab, items appear as
    // <span class="dds-text-base dds-font-semibold dds-text-primary dds-leading-normal">
    // The filter panel is NOT inside [role='dialog'] – it is a side panel
    private final By languageOptionSpans = By.cssSelector(
            "span.dds-text-base.dds-font-semibold.dds-text-primary.dds-leading-normal");

    // Apply Filters button
    private final By applyFiltersBtn = By.cssSelector("button[aria-label='Apply Filters']");

    // ── Constructor ───────────────────────────────────────────────────────────

    public MoviesPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitHelper(driver);
    }

    // ── Actions ───────────────────────────────────────────────────────────────

    public void open() {
        driver.get("https://www.district.in/movies/");
        // Wait for the Filters button to appear (more reliable than h2)
        wait.waitForVisible(filtersBtn);
        wait.waitSeconds(2);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /** Returns true when at least one movie card (h2) is visible. */
    public boolean isMovieListVisible() {
        try {
            List<WebElement> cards = driver.findElements(movieCards);
            return !cards.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    /** Returns true when the Filters button is present. */
    public boolean isFiltersBtnPresent() {
        return wait.isElementPresent(filtersBtn);
    }

    /**
     * Clicks the Filters button to open the filter panel.
     * Note: the Filters button matches two elements (Filters + Apply Filters).
     * We target only the one with aria-label="Filters".
     */
    public void clickFiltersButton() {
        try {
            List<WebElement> btns = driver.findElements(filtersBtn);
            // Click the first one that is NOT the Apply Filters button
            for (WebElement btn : btns) {
                String label = btn.getAttribute("aria-label");
                if ("Filters".equals(label)) {
                    btn.click();
                    break;
                }
            }
            wait.waitSeconds(2);
        } catch (Exception e) {
            wait.click(filtersBtn);
            wait.waitSeconds(2);
        }
    }

    /**
     * Returns true when the filter panel is open (Language span is present in dialog).
     */
    public boolean isFilterPanelOpen() {
        return wait.isElementPresent(languageSectionSpan);
    }

    /**
     * Returns true when the Language section heading is visible inside the filter panel.
     */
    public boolean isLanguageSectionPresent() {
        return wait.isElementPresent(languageSectionSpan);
    }

    /**
     * Clicks the "Language" tab inside the filter panel to reveal language options.
     */
    public void clickLanguageTab() {
        wait.jsClick(languageSectionSpan);
        wait.waitSeconds(1);
    }

    /**
     * Returns a List of language names visible in the Language filter section.
     * Expected: English, Hindi, Marathi, Gujarati, Malayalam, Punjabi, Telugu, Tamil
     */
    public List<String> extractLanguageOptions() {
        List<String> languages = new ArrayList<>();
        try {
            List<WebElement> spans = driver.findElements(languageOptionSpans);
            for (WebElement span : spans) {
                String text = span.getText().trim();
                // Exclude section headings (Genre, Language, Format, Filter by)
                if (!text.isEmpty()
                        && !text.equalsIgnoreCase("Genre")
                        && !text.equalsIgnoreCase("Language")
                        && !text.equalsIgnoreCase("Format")
                        && !text.equalsIgnoreCase("Filter by")
                        && !text.equalsIgnoreCase("Apply Filters")) {
                    languages.add(text);
                }
            }
        } catch (Exception e) {
            System.err.println("Error extracting language options: " + e.getMessage());
        }
        return languages;
    }

    /** Prints all extracted language options to the console. */
    public void printLanguageOptions(List<String> languages) {
        System.out.println("=== Language Options Available ===");
        for (String lang : languages) {
            System.out.println("  - " + lang);
        }
        System.out.println("Total language count: " + languages.size());
    }
}
