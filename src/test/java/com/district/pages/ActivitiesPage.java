package com.district.pages;

import com.district.utils.WaitHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Page Object for the Activities page (https://www.district.in/activities/).
 * Covers:
 *  - TC-01 to TC-04  : navigation and listing
 *  - TC-05 to TC-07  : This Weekend filter
 *  - TC-08 to TC-11  : Price: Low to High sort via Filters panel
 *  - TC-12 to TC-18  : data extraction and lowest price
 */
public class ActivitiesPage {

    private final WebDriver driver;
    private final WaitHelper wait;

    // ── Locators ──────────────────────────────────────────────────────────────

    // Quick filter buttons use aria-label
    private final By thisWeekendBtn  = By.cssSelector("button[aria-label='This Weekend']");
    private final By filtersBtn      = By.cssSelector("button[aria-label='Filters']");

    // Activity cards – name is in <h2>, venue and price are <span> siblings
    private final By activityNameElements  = By.cssSelector("h2.dds-font-bold");
    private final By activityPriceElements = By.cssSelector("span.dds-font-bold.\\!dds-text-primary");

    // Generic h2 locator (used as fallback)
    private final By h2Elements = By.tagName("h2");

    // Filter panel (dialog)
    private final By filterDialog         = By.cssSelector("[role='dialog']");
    private final By priceLowToHighLabel  = By.xpath("//label[normalize-space(text())='Price : Low to High']");
    private final By applyFiltersBtn      = By.cssSelector("button[aria-label='Apply Filters']");

    // ── Constructor ───────────────────────────────────────────────────────────

    public ActivitiesPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitHelper(driver);
    }

    // ── Actions ───────────────────────────────────────────────────────────────

    public void open() {
        driver.get("https://www.district.in/activities/");
        // Wait for the Filters button (more reliable than This Weekend btn)
        wait.waitForVisible(filtersBtn);
        wait.waitSeconds(2);
        // Dismiss location dialog if it appeared
        try {
            List<WebElement> dialogs = driver.findElements(By.cssSelector("[role='dialog']"));
            if (!dialogs.isEmpty()) {
                org.openqa.selenium.interactions.Actions actions =
                        new org.openqa.selenium.interactions.Actions(driver);
                actions.sendKeys(org.openqa.selenium.Keys.ESCAPE).perform();
                wait.waitSeconds(1);
            }
        } catch (Exception ignored) {
        }
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /** Returns true if at least one activity card (h2) is visible. */
    public boolean isActivityListVisible() {
        try {
            List<WebElement> cards = driver.findElements(h2Elements);
            return !cards.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    /** Returns the count of visible activity cards. */
    public int getActivityCardCount() {
        return driver.findElements(h2Elements).size();
    }

    /** Returns true if the This Weekend quick filter button is present. */
    public boolean isThisWeekendFilterPresent() {
        return wait.isElementPresent(thisWeekendBtn);
    }

    /** Clicks the This Weekend filter button. */
    public void clickThisWeekendFilter() {
        wait.click(thisWeekendBtn);
        wait.waitSeconds(2);
    }

    /** Returns true if the This Weekend button appears selected (aria-pressed or class change). */
    public boolean isThisWeekendFilterSelected() {
        try {
            WebElement btn = driver.findElement(thisWeekendBtn);
            // The site applies a different background class when selected
            String cls = btn.getAttribute("class");
            String dataAttr = btn.getAttribute("data-selected");
            return (dataAttr != null && dataAttr.equals("true"))
                    || (cls != null && (cls.contains("dds-bg-inverse") || cls.contains("selected")));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Returns the date text of the first activity card.
     * The date span has class containing "dds-font-semibold" and "dds-text-lg".
     */
    public String getFirstCardDateText() {
        try {
            WebElement dateSpan = driver.findElement(
                    By.cssSelector("span.dds-font-semibold.dds-text-lg"));
            return dateSpan.getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    /** Returns true if the Filters button is present. */
    public boolean isFiltersBtnPresent() {
        return wait.isElementPresent(filtersBtn);
    }

    /** Clicks the Filters button to open the sort/filter panel. */
    public void clickFiltersButton() {
        wait.click(filtersBtn);
        wait.waitSeconds(2);
    }

    /**
     * Returns true if the filter panel (dialog) is open and the
     * Price: Low to High label is visible inside it.
     */
    public boolean isFilterPanelOpen() {
        try {
            List<WebElement> dialogs = driver.findElements(filterDialog);
            for (WebElement dialog : dialogs) {
                // Check opacity – dialogs start with opacity-0 while animating
                String style = dialog.getAttribute("class");
                if (style != null && !style.contains("dds-opacity-0")) {
                    return true;
                }
            }
            // Fallback: check if the label is present
            return wait.isElementPresent(priceLowToHighLabel);
        } catch (Exception e) {
            return false;
        }
    }

    /** Selects the Price: Low to High sort option. */
    public void selectPriceLowToHighSort() {
        // Use JS click to avoid overlay issues
        wait.jsClick(priceLowToHighLabel);
        wait.waitSeconds(1);
    }

    /**
     * Returns true when the Price: Low to High radio appears checked.
     */
    public boolean isPriceLowToHighSelected() {
        try {
            WebElement label = driver.findElement(priceLowToHighLabel);
            // The preceding sibling input (radio) represents the state
            WebElement radio = label.findElement(By.xpath("preceding-sibling::input"));
            return radio.isSelected();
        } catch (Exception e) {
            // Try via aria-checked span
            try {
                WebElement span = driver.findElement(
                        By.cssSelector("[role='radio'][aria-label='Price : Low to High']"));
                return "true".equals(span.getAttribute("aria-checked"));
            } catch (Exception ex) {
                return false;
            }
        }
    }

    /** Clicks the Apply Filters button. */
    public void clickApplyFilters() {
        wait.jsClick(applyFiltersBtn);
        wait.waitSeconds(3);
    }

    /**
     * Returns true if the filter panel is no longer open (dialog closed).
     */
    public boolean isFilterPanelClosed() {
        try {
            List<WebElement> dialogs = driver.findElements(filterDialog);
            // All dialogs should either not exist or be invisible
            for (WebElement dialog : dialogs) {
                String cls = dialog.getAttribute("class");
                if (cls != null && !cls.contains("dds-opacity-0") && dialog.isDisplayed()) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            return true;
        }
    }

    // ── Data Extraction ───────────────────────────────────────────────────────

    /**
     * Returns a list of Maps containing name, venue, and price for each
     * visible activity card on the page.
     *
     * Card HTML structure (simplified):
     *   <h2>  Activity Name  </h2>
     *   <span class="dds-text-2xl dds-font-semibold ...">Venue, City</span>
     *   <span class="dds-text-lg dds-font-bold ...">₹NNN onwards</span>
     */
    public List<Map<String, String>> extractActivityData() {
        List<Map<String, String>> activities = new ArrayList<>();

        List<WebElement> nameElements = driver.findElements(h2Elements);
        for (WebElement nameEl : nameElements) {
            try {
                String name = nameEl.getText().trim();
                if (name.isEmpty()) continue;

                // Navigate up to the card container
                WebElement cardContainer = nameEl;
                for (int i = 0; i < 6; i++) {
                    cardContainer = (WebElement) ((JavascriptExecutor) driver)
                            .executeScript("return arguments[0].parentElement;", cardContainer);
                    if (cardContainer == null) break;
                    List<WebElement> priceEls = cardContainer.findElements(
                            By.cssSelector("span.dds-font-bold"));
                    if (!priceEls.isEmpty()) break;
                }

                String venue = "";
                String price = "";
                if (cardContainer != null) {
                    List<WebElement> semiBold = cardContainer.findElements(
                            By.cssSelector("span.dds-font-semibold.\\!dds-text-primary"));
                    if (!semiBold.isEmpty()) {
                        // First semibold span = date, second = venue
                        if (semiBold.size() >= 2) {
                            venue = semiBold.get(1).getText().trim();
                        }
                    }
                    List<WebElement> boldSpans = cardContainer.findElements(
                            By.cssSelector("span.dds-font-bold.\\!dds-text-primary"));
                    if (!boldSpans.isEmpty()) {
                        price = boldSpans.get(0).getText().trim();
                    }
                }

                Map<String, String> activity = new HashMap<>();
                activity.put("name", name);
                activity.put("venue", venue);
                activity.put("price", price);
                activities.add(activity);

            } catch (Exception e) {
                // Skip malformed cards
            }
        }
        return activities;
    }

    /**
     * Parses numeric price value from a string like "₹176 onwards" → 176.
     * Returns Integer.MAX_VALUE if parsing fails.
     */
    public static int parsePrice(String priceText) {
        try {
            String numeric = priceText.replaceAll("[^0-9]", "").trim();
            if (numeric.isEmpty()) return Integer.MAX_VALUE;
            return Integer.parseInt(numeric);
        } catch (NumberFormatException e) {
            return Integer.MAX_VALUE;
        }
    }

    /** Finds and returns the activity with the lowest price from the list. */
    public Map<String, String> getLowestPriceActivity(List<Map<String, String>> activities) {
        return activities.stream()
                .min((a, b) -> Integer.compare(
                        parsePrice(a.get("price")),
                        parsePrice(b.get("price"))))
                .orElse(null);
    }
}
