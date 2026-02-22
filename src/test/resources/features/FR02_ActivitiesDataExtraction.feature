# Feature: FR-02 – Activities Data Extraction and Lowest Price
# Scenarios: TS-04, TS-05
# Test Cases: TC-12 to TC-18

@FR02 @Activities @DataExtraction
Feature: Activities Data Extraction and Lowest Price

  Background:
    Given the user is on the Activities page with This Weekend filter and Price Low to High sort applied

  # ──────────────────────────────────────────────
  # TS-04: Extract Activity Data into a Java Collection
  # ──────────────────────────────────────────────

  @TC12
  Scenario: TC-12 Verify activity name is extracted from each card
    Then activity names should be extracted as non-empty strings

  @TC13
  Scenario: TC-13 Verify activity price is extracted from each card
    Then activity prices should be extracted in rupee format from each card

  @TC14
  Scenario: TC-14 Verify activity venue is extracted from each card
    Then activity venues should be extracted as non-empty strings

  @TC15
  Scenario: TC-15 Verify all extracted data is stored in a Java collection
    Then the extracted activity data should be stored in a collection
    And the collection size should match the number of visible activity cards

  # ──────────────────────────────────────────────
  # TS-05: Identify and Print the Lowest Priced Activity
  # ──────────────────────────────────────────────

  @TC16
  Scenario: TC-16 Verify lowest price activity is the first item after sort
    Then the first activity in the list should have the lowest price

  @TC17
  Scenario: TC-17 Verify lowest price activity details are printed to console
    Then the lowest priced activity details should be printed to the console

  @TC18
  Scenario: TC-18 Verify printed activity price is a valid numeric value
    Then the lowest activity price should be a valid numeric value greater than zero
