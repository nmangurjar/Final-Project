package com.district.stepdefs;

import com.district.pages.HomePage;
import com.district.pages.LoginPage;
import com.district.utils.DriverManager;
import com.district.utils.ScreenshotUtil;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Step definitions for FR-04: Login Invalid Number Error Capture
 * Feature file: FR04_LoginErrorCapture.feature
 * Test Cases: TC-26 to TC-35
 */
public class LoginErrorCaptureSteps {

    private WebDriver driver;
    private HomePage homePage;
    private LoginPage loginPage;

    // Shared state
    private String savedScreenshotPath;

    public LoginErrorCaptureSteps() {
        this.driver = DriverManager.getDriver();
        this.homePage = new HomePage(driver);
        this.loginPage = new LoginPage(driver);
    }

    // ── Given ─────────────────────────────────────────────────────────────────

    @Given("the login modal is open")
    public void theLoginModalIsOpen() {
        homePage.open();
        homePage.clickUserAvatar();
        assertTrue("Login modal did not open", loginPage.isLoginModalOpen());
    }

    @Given("the login modal shows an error after entering {string}")
    public void theLoginModalShowsAnErrorAfterEntering(String number) {
        homePage.open();
        homePage.clickUserAvatar();
        loginPage.enterMobileNumber(number);
        loginPage.clickContinue();
        assertTrue("Error message did not appear after entering: " + number,
                loginPage.isErrorMessageDisplayed());
    }

    // ── When ──────────────────────────────────────────────────────────────────

    @When("the user clicks the User Avatar button")
    public void theUserClicksTheUserAvatarButton() {
        homePage.clickUserAvatar();
    }

    @When("the user leaves the mobile number field empty and clicks Continue")
    public void theUserLeavesFieldEmptyAndClicksContinue() {
        loginPage.clearMobileInput();
        loginPage.clickContinue();
    }

    @When("the user enters {string} in the mobile number field and clicks Continue")
    public void theUserEntersMobileNumberAndClicksContinue(String number) {
        loginPage.enterMobileNumber(number);
        loginPage.clickContinue();
    }

    @When("the automation takes a screenshot")
    public void theAutomationTakesAScreenshot() {
        savedScreenshotPath = ScreenshotUtil.takeScreenshot(driver, "login_error");
    }

    @When("the automation takes a screenshot named {string}")
    public void theAutomationTakesAScreenshotNamed(String fileName) {
        savedScreenshotPath = ScreenshotUtil.takeScreenshot(driver, fileName);
    }

    // ── Then ──────────────────────────────────────────────────────────────────

    @Then("the User Avatar button should be visible in the navigation bar")
    public void theUserAvatarButtonShouldBeVisible() {
        assertTrue("User Avatar button is not visible in the navigation bar",
                homePage.isUserAvatarPresent());
    }

    @Then("the login modal should appear with a mobile number input field")
    public void theLoginModalShouldAppear() {
        assertTrue("Login modal did not appear after clicking User Avatar",
                loginPage.isLoginModalOpen());
        System.out.println("TC-27 PASS: Login modal is open with mobile number input.");
    }

    @Then("the login modal should show the mobile number input field")
    public void theLoginModalShouldShowMobileInput() {
        assertTrue("Mobile number input field is not present in the login modal",
                loginPage.isMobileInputPresent());
    }

    @Then("the login modal should show the country code {string}")
    public void theLoginModalShouldShowCountryCode(String countryCode) {
        assertTrue("Country code '" + countryCode + "' is not shown in the login modal",
                loginPage.isCountryCodePresent());
    }

    @Then("the login modal should show the Continue button")
    public void theLoginModalShouldShowContinueButton() {
        assertTrue("Continue button is not present in the login modal",
                loginPage.isContinueBtnPresent());
        System.out.println("TC-28 PASS: Login modal has input, +91 code, and Continue button.");
    }

    @Then("an error message should be displayed in the login modal")
    public void anErrorMessageShouldBeDisplayed() {
        assertTrue("Error message was not displayed after empty mobile number submission",
                loginPage.isErrorMessageDisplayed());
        String errorText = loginPage.getErrorMessageText();
        System.out.println("TC-29 PASS: Error message displayed: \"" + errorText + "\"");
    }

    @Then("an error message should be displayed below the input field")
    public void anErrorMessageShouldBeDisplayedBelowInputField() {
        assertTrue("Error message was not displayed after entering short mobile number",
                loginPage.isErrorMessageDisplayed());
        String errorText = loginPage.getErrorMessageText();
        assertFalse("Error message text is empty", errorText.isEmpty());
        System.out.println("TC-30 PASS: Error message is: \"" + errorText + "\"");
    }

    @Then("the error message text should be captured as a non-empty string")
    public void theErrorMessageTextShouldBeCapturedAsNonEmptyString() {
        String errorText = loginPage.getErrorMessageText();
        System.out.println("Captured error message: \"" + errorText + "\"");
        assertNotNull("Error message text is null", errorText);
        assertFalse("Error message text is empty", errorText.trim().isEmpty());
        System.out.println("TC-31 PASS: Error text captured: \"" + errorText + "\"");
    }

    @Then("the mobile number input field should still be visible")
    public void theMobileNumberInputFieldShouldStillBeVisible() {
        assertTrue("Mobile number input is no longer visible – user may have navigated away",
                loginPage.isMobileInputStillVisible());
    }

    @Then("the user should not be navigated away from the login modal")
    public void theUserShouldNotBeNavigatedAway() {
        assertTrue("User was navigated away from the login modal",
                loginPage.isLoginModalOpen());
        System.out.println("TC-32 PASS: User remains on the login modal after invalid submission.");
    }

    @Then("the screenshot file should be saved to the screenshots directory")
    public void theScreenshotFileShouldBeSaved() {
        assertNotNull("Screenshot path is null", savedScreenshotPath);
        assertFalse("Screenshot path is empty", savedScreenshotPath.isEmpty());
        File screenshotFile = new File(savedScreenshotPath);
        assertTrue("Screenshot file does not exist at: " + savedScreenshotPath,
                screenshotFile.exists());
        System.out.println("TC-33 PASS: Screenshot saved at: " + savedScreenshotPath);
    }

    @Then("the screenshot file should exist at the saved path and not be empty")
    public void theScreenshotFileShouldExistAndNotBeEmpty() {
        assertNotNull("Screenshot path is null", savedScreenshotPath);
        File screenshotFile = new File(savedScreenshotPath);
        assertTrue("Screenshot file does not exist: " + savedScreenshotPath,
                screenshotFile.exists());
        assertTrue("Screenshot file is empty (0 bytes): " + savedScreenshotPath,
                screenshotFile.length() > 0);
        System.out.println("TC-34 PASS: Screenshot file exists and size = "
                + screenshotFile.length() + " bytes. Path: " + savedScreenshotPath);
    }

    @Then("the screenshot should be saved successfully without errors")
    public void theScreenshotShouldBeSavedSuccessfully() {
        assertNotNull("Screenshot path is null", savedScreenshotPath);
        assertFalse("Screenshot path is empty", savedScreenshotPath.isEmpty());
        File screenshotFile = new File(savedScreenshotPath);
        assertTrue("Screenshot was not saved successfully. Path: " + savedScreenshotPath,
                screenshotFile.exists() && screenshotFile.length() > 0);
        System.out.println("TC-35 PASS: Screenshot saved successfully at: " + savedScreenshotPath);
    }
}
