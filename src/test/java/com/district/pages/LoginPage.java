package com.district.pages;

import com.district.utils.WaitHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Page Object for the Login Modal on district.in.
 * The modal is triggered by clicking the User Avatar button.
 *
 * Covers:
 *  - TC-26 to TC-28  : Login modal UI verification
 *  - TC-29 to TC-32  : Invalid number error
 *  - TC-33 to TC-35  : Screenshot capture
 */
public class LoginPage {

    private final WebDriver driver;
    private final WaitHelper wait;

    // ── Locators ──────────────────────────────────────────────────────────────

    // Mobile number input field (inside any open dialog)
    private final By mobileInput = By.cssSelector("[role='dialog'] input[name='mobileNumber']");

    // Country code area showing "+91" – it is a <div> with text "+91" split across two text nodes
    // Must use normalize-space(.) not normalize-space(text()) since text() only matches first text node
    private final By countryCode = By.xpath("//*[@role='dialog']//div[normalize-space(.)='+91' and not(.//*[normalize-space(.)='+91'])]");

    // Continue button inside login modal
    // It has class "dds-w-full" and contains text "Continue"
    private final By continueBtn = By.xpath(
            "//*[@role='dialog']//button[normalize-space(text())='Continue' or "
            + "normalize-space(.)='Continue']");

    // Error message paragraph with class "dds-text-red-500"
    private final By errorMessage = By.cssSelector("[role='dialog'] p.dds-text-red-500");

    // Label "Enter your mobile number"
    private final By mobileLabel = By.xpath(
            "//*[@role='dialog']//label[normalize-space(text())='Enter your mobile number']");

    // ── Constructor ───────────────────────────────────────────────────────────

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitHelper(driver);
    }

    // ── Actions ───────────────────────────────────────────────────────────────

    /** Returns true when the mobile input field is present in a dialog. */
    public boolean isLoginModalOpen() {
        return wait.isElementPresent(mobileInput);
    }

    /** Returns true when the mobile number input field is visible. */
    public boolean isMobileInputPresent() {
        return wait.isElementPresent(mobileInput);
    }

    /** Returns true when the +91 country code is displayed. */
    public boolean isCountryCodePresent() {
        try {
            wait.waitForVisible(countryCode);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /** Returns true when the Continue button is present. */
    public boolean isContinueBtnPresent() {
        return wait.isElementPresent(continueBtn);
    }

    /** Enters the given mobile number into the input field. */
    public void enterMobileNumber(String number) {
        wait.waitForVisible(mobileInput);
        wait.type(mobileInput, number);
        wait.waitSeconds(1);
    }

    /** Clicks the Continue button. */
    public void clickContinue() {
        // Remove any overlapping location dialogs via JS first
        try {
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                    "document.querySelectorAll('[role=\"dialog\"]').forEach(el => {"
                    + "  var txt = el.textContent || '';"
                    + "  if (txt.includes('Select Location') || txt.includes('All Cities')) {"
                    + "    el.remove();"
                    + "  }"
                    + "});");
            wait.waitSeconds(1);
        } catch (Exception ignored) {
        }
        // Use JS click on the Continue button
        try {
            WebElement btn = driver.findElement(continueBtn);
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        } catch (Exception e) {
            wait.jsClick(continueBtn);
        }
        wait.waitSeconds(2);
    }

    /**
     * Returns the text of the error message displayed below the input.
     * Returns empty string if no error message is found.
     */
    public String getErrorMessageText() {
        try {
            WebElement errEl = wait.waitForVisible(errorMessage);
            return errEl.getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    /** Returns true when the error message element is visible. */
    public boolean isErrorMessageDisplayed() {
        try {
            WebElement errEl = driver.findElement(errorMessage);
            return errEl.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Returns true when the mobile input field is still visible,
     * confirming the user has not navigated away from the login modal.
     */
    public boolean isMobileInputStillVisible() {
        try {
            WebElement input = driver.findElement(mobileInput);
            return input.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /** Clears the mobile number input field. */
    public void clearMobileInput() {
        try {
            WebElement input = driver.findElement(mobileInput);
            input.clear();
        } catch (Exception e) {
            // ignore
        }
    }
}
