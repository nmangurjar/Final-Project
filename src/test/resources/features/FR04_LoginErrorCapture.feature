# Feature: FR-04 – Login Invalid Number Error Capture
# Scenarios: TS-08, TS-09, TS-10
# Test Cases: TC-26 to TC-35

@FR04 @Login
Feature: Login Invalid Number Error Capture

  Background:
    Given the user is on the District home page

  # ──────────────────────────────────────────────
  # TS-08: Open Login Modal via User Avatar
  # ──────────────────────────────────────────────

  @TC26 @Smoke
  Scenario: TC-26 Verify User Avatar button is present in navigation
    Then the User Avatar button should be visible in the navigation bar

  @TC27 @Smoke
  Scenario: TC-27 Verify clicking User Avatar opens login modal
    When the user clicks the User Avatar button
    Then the login modal should appear with a mobile number input field

  @TC28
  Scenario: TC-28 Verify login modal contains required UI elements
    When the user clicks the User Avatar button
    Then the login modal should show the mobile number input field
    And the login modal should show the country code "+91"
    And the login modal should show the Continue button

  # ──────────────────────────────────────────────
  # TS-09: Submit Invalid Mobile Number and Capture Error
  # ──────────────────────────────────────────────

  @TC29
  Scenario: TC-29 Verify error is shown for empty mobile number
    Given the login modal is open
    When the user leaves the mobile number field empty and clicks Continue
    Then an error message should be displayed in the login modal

  @TC30
  Scenario: TC-30 Verify error is shown for short mobile number
    Given the login modal is open
    When the user enters "12345" in the mobile number field and clicks Continue
    Then an error message should be displayed below the input field

  @TC31
  Scenario: TC-31 Verify error message text is captured by automation
    Given the login modal is open
    When the user enters "12345" in the mobile number field and clicks Continue
    Then the error message text should be captured as a non-empty string

  @TC32
  Scenario: TC-32 Verify user stays on login modal after invalid submission
    Given the login modal is open
    When the user enters "999" in the mobile number field and clicks Continue
    Then the mobile number input field should still be visible
    And the user should not be navigated away from the login modal

  # ──────────────────────────────────────────────
  # TS-10: Take Screenshot After Login Error is Displayed
  # ──────────────────────────────────────────────

  @TC33
  Scenario: TC-33 Verify screenshot is taken after error message appears
    Given the login modal shows an error after entering "12345"
    When the automation takes a screenshot
    Then the screenshot file should be saved to the screenshots directory

  @TC34
  Scenario: TC-34 Verify screenshot file is saved with a valid name
    Given the login modal shows an error after entering "12345"
    When the automation takes a screenshot
    Then the screenshot file should exist at the saved path and not be empty

  @TC35
  Scenario: TC-35 Verify screenshot shows the login error message
    Given the login modal shows an error after entering "12345"
    When the automation takes a screenshot named "login_error_screenshot"
    Then the screenshot should be saved successfully without errors
