package com.district.pages;

import com.district.utils.WaitHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Page Object for the District home page (https://www.district.in/).
 * Covers: navigation links and User Avatar button.
 */
public class HomePage {

    private final WebDriver driver;
    private final WaitHelper wait;

    // ── Locators ──────────────────────────────────────────────────────────────

    // Navigation links – each nav item is an <a> with the link text as content
    private final By activitiesLink = By.xpath("//a[@href='/activities/']");
    private final By moviesLink     = By.xpath("//a[@href='/movies/']");

    // User Avatar – it's a <div role="button" aria-label="User Avatar">, NOT a <button>
    private final By userAvatarBtn  = By.cssSelector("[aria-label='User Avatar']");

    // Location modal dialog that sometimes appears on first load
    private final By locationDialog = By.cssSelector("[role='dialog']");

    // ── Constructor ───────────────────────────────────────────────────────────

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitHelper(driver);
    }

    // ── Helper ────────────────────────────────────────────────────────────────

    /**
     * Dismisses any open dialog (location dialog, etc.) by pressing Escape,
     * then forcibly removes dialogs and backdrop overlays via JavaScript so
     * that no stacking context blocks subsequent interactions.
     */
    public void dismissLocationDialogIfPresent() {
        try {
            // Step 1: press Escape
            org.openqa.selenium.interactions.Actions actions =
                    new org.openqa.selenium.interactions.Actions(driver);
            actions.sendKeys(org.openqa.selenium.Keys.ESCAPE).perform();
            wait.waitSeconds(1);

            // Step 2: JS – remove ALL [role=dialog] elements
            ((JavascriptExecutor) driver).executeScript(
                    "document.querySelectorAll('[role=\"dialog\"]').forEach(function(el){el.remove();});");

            // Step 3: JS – remove common backdrop/overlay divs that block pointer events
            ((JavascriptExecutor) driver).executeScript(
                    "document.querySelectorAll('[data-radix-dialog-overlay],"
                    + "[data-overlay],"
                    + ".fixed.inset-0,"
                    + "[class*=\"overlay\"],"
                    + "[class*=\"backdrop\"],"
                    + "[class*=\"Overlay\"]"
                    + "').forEach(function(el){"
                    + "  el.style.pointerEvents='none';"
                    + "  el.style.display='none';"
                    + "});");

            // Step 4: restore body scroll (dialogs often add overflow:hidden)
            ((JavascriptExecutor) driver).executeScript(
                    "document.body.style.overflow='';");

            wait.waitSeconds(1);
        } catch (Exception ignored) {
        }
    }

    // ── Actions ───────────────────────────────────────────────────────────────

    public void open() {
        driver.get("https://www.district.in/");
        // Wait until at least one nav link appears
        wait.waitForVisible(activitiesLink);
        // Dismiss location dialog if it popped up
        dismissLocationDialogIfPresent();
    }

    /** Returns true when the Activities link is present in the nav bar. */
    public boolean isActivitiesLinkPresent() {
        return wait.isElementPresent(activitiesLink);
    }

    /** Returns true when the Movies link is present in the nav bar. */
    public boolean isMoviesLinkPresent() {
        return wait.isElementPresent(moviesLink);
    }

    /** Returns true when the User Avatar button is present and visible. */
    public boolean isUserAvatarPresent() {
        try {
            WebElement btn = driver.findElement(userAvatarBtn);
            return btn.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /** Clicks the Activities link in the navigation. */
    public void clickActivities() {
        dismissLocationDialogIfPresent();
        // Use JS navigation to avoid any overlay issues
        driver.get("https://www.district.in/activities/");
        wait.waitSeconds(2);
    }

    /** Clicks the Movies link in the navigation. */
    public void clickMovies() {
        dismissLocationDialogIfPresent();
        // Use JS navigation to avoid any overlay issues
        driver.get("https://www.district.in/movies/");
        wait.waitSeconds(2);
    }

    /** Clicks the User Avatar button to open the login modal. */
    public void clickUserAvatar() {
        // Ensure dialogs/overlays are fully gone
        dismissLocationDialogIfPresent();
        wait.waitSeconds(1);
        // Find the button, scroll into view, then JS-click
        try {
            WebElement avatarBtn = wait.waitForVisible(userAvatarBtn);
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block:'center'});", avatarBtn);
            wait.waitSeconds(1);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", avatarBtn);
        } catch (Exception e) {
            // Fallback: dismiss again and retry
            dismissLocationDialogIfPresent();
            try {
                WebElement avatarBtn = driver.findElement(userAvatarBtn);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", avatarBtn);
            } catch (Exception ex) {
                wait.click(userAvatarBtn);
            }
        }
        wait.waitSeconds(2);
    }

    /** Returns current page URL. */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
