# Feature: FR-01 – Activities Filter and Sort
# Scenarios: TS-01, TS-02, TS-03
# Test Cases: TC-01 to TC-11

@FR01 @Activities
Feature: Activities Filter and Sort

  Background:
    Given the user is on the District home page

  # ──────────────────────────────────────────────
  # TS-01: Navigate to Activities Page and Verify Listing
  # ──────────────────────────────────────────────

  @TC01 @Smoke
  Scenario: TC-01 Verify Activities link is present in navigation
    Then the Activities link should be visible in the navigation bar

  @TC02 @Smoke
  Scenario: TC-02 Verify clicking Activities navigates to Activities page
    When the user clicks the Activities link
    Then the browser URL should contain "/activities/"

  @TC03
  Scenario: TC-03 Verify Activities page displays activity cards
    When the user navigates to the Activities page
    Then at least one activity card should be visible on the page

  @TC04
  Scenario: TC-04 Verify This Weekend filter button is present on Activities page
    When the user navigates to the Activities page
    Then the "This Weekend" filter button should be visible on the page

  # ──────────────────────────────────────────────
  # TS-02: Apply This Weekend Filter on Activities
  # ──────────────────────────────────────────────

  @TC05 @Smoke
  Scenario: TC-05 Verify clicking This Weekend filter refreshes the list
    Given the user is on the Activities page
    When the user clicks the "This Weekend" filter button
    Then the activity list should refresh and show results

  @TC06
  Scenario: TC-06 Verify activities list is not empty after This Weekend filter
    Given the user is on the Activities page
    When the user clicks the "This Weekend" filter button
    Then at least one activity card should be displayed after filtering

  @TC07
  Scenario: TC-07 Verify This Weekend filter shows weekend dates on cards
    Given the user is on the Activities page
    When the user clicks the "This Weekend" filter button
    Then the activity cards should be visible on the page

  # ──────────────────────────────────────────────
  # TS-03: Sort Activities by Price Low to High
  # ──────────────────────────────────────────────

  @TC08
  Scenario: TC-08 Verify Filters button opens sort panel
    Given the user has applied the This Weekend filter on Activities page
    When the user clicks the Filters button on Activities page
    Then the filter panel should open showing sort options

  @TC09
  Scenario: TC-09 Verify Price Low to High sort option is selectable
    Given the user has opened the Filters panel on Activities page
    When the user selects "Price : Low to High" sort option
    Then the "Price : Low to High" sort option should be selected

  @TC10 @Smoke
  Scenario: TC-10 Verify activities are reordered after applying price sort
    Given the user has opened the Filters panel on Activities page
    When the user selects "Price : Low to High" sort option
    And the user clicks Apply Filters button
    Then the Activities page should be displayed with sorted results

  @TC11
  Scenario: TC-11 Verify filter panel closes after clicking Apply Filters
    Given the user has opened the Filters panel on Activities page
    When the user selects "Price : Low to High" sort option
    And the user clicks Apply Filters button
    Then the filter panel should be closed
