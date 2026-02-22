# Feature: FR-03 – Movies Language Filter Extraction
# Scenarios: TS-06, TS-07
# Test Cases: TC-19 to TC-25

@FR03 @Movies
Feature: Movies Language Filter Extraction

  Background:
    Given the user is on the District home page

  # ──────────────────────────────────────────────
  # TS-06: Navigate to Movies Page and Verify Listing
  # ──────────────────────────────────────────────

  @TC19 @Smoke
  Scenario: TC-19 Verify Movies link is present in navigation
    Then the Movies link should be visible in the navigation bar

  @TC20 @Smoke
  Scenario: TC-20 Verify clicking Movies navigates to Movies page
    When the user clicks the Movies link
    Then the browser URL should contain "/movies/"

  @TC21
  Scenario: TC-21 Verify Movies page displays movie cards
    When the user navigates to the Movies page
    Then at least one movie card should be visible on the page

  # ──────────────────────────────────────────────
  # TS-07: Open Filters Panel and Extract Language Options
  # ──────────────────────────────────────────────

  @TC22 @Smoke
  Scenario: TC-22 Verify Filters button opens filter panel on Movies page
    Given the user is on the Movies page
    When the user clicks the Filters button on Movies page
    Then the filter panel should open showing Genre Language and Format sections

  @TC23
  Scenario: TC-23 Verify Language section is present in Movies filter panel
    Given the user has opened the filter panel on Movies page
    Then the Language section heading should be visible in the filter panel

  @TC24 @Smoke
  Scenario: TC-24 Verify language options are extracted from the filter panel
    Given the user has opened the filter panel on Movies page
    When the user clicks the Language tab in the filter panel
    Then a list of language options should be captured with at least 2 entries

  @TC25
  Scenario: TC-25 Verify extracted language list is printed to console
    Given the user has opened the filter panel on Movies page
    When the user clicks the Language tab in the filter panel
    Then all language options should be printed to the console with no empty entries
