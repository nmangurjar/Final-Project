package com.district.stepdefs;

import com.district.pages.HomePage;
import com.district.pages.MoviesPage;
import com.district.utils.DriverManager;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Step definitions for FR-03: Movies Language Filter Extraction
 * Feature file: FR03_MoviesLanguageFilter.feature
 * Test Cases: TC-19 to TC-25
 */
public class MoviesLanguageFilterSteps {

    private WebDriver driver;
    private HomePage homePage;
    private MoviesPage moviesPage;

    // Shared state
    private List<String> extractedLanguages;

    public MoviesLanguageFilterSteps() {
        this.driver = DriverManager.getDriver();
        this.homePage = new HomePage(driver);
        this.moviesPage = new MoviesPage(driver);
    }

    // ── Given ─────────────────────────────────────────────────────────────────

    @Given("the user is on the Movies page")
    public void theUserIsOnTheMoviesPage() {
        moviesPage.open();
    }

    @Given("the user has opened the filter panel on Movies page")
    public void theUserHasOpenedFilterPanelOnMoviesPage() {
        moviesPage.open();
        moviesPage.clickFiltersButton();
    }

    // ── When ──────────────────────────────────────────────────────────────────

    @When("the user clicks the Movies link")
    public void theUserClicksTheMoviesLink() {
        homePage.clickMovies();
    }

    @When("the user navigates to the Movies page")
    public void theUserNavigatesToTheMoviesPage() {
        moviesPage.open();
    }

    @When("the user clicks the Filters button on Movies page")
    public void theUserClicksTheFiltersBtnOnMoviesPage() {
        moviesPage.clickFiltersButton();
    }

    @When("the user clicks the Language tab in the filter panel")
    public void theUserClicksTheLanguageTab() {
        moviesPage.clickLanguageTab();
        extractedLanguages = moviesPage.extractLanguageOptions();
    }

    // ── Then ──────────────────────────────────────────────────────────────────

    @Then("the Movies link should be visible in the navigation bar")
    public void theMoviesLinkShouldBeVisible() {
        assertTrue("Movies link is not visible in the navigation bar",
                homePage.isMoviesLinkPresent());
    }

    @Then("at least one movie card should be visible on the page")
    public void atLeastOneMovieCardShouldBeVisible() {
        assertTrue("No movie cards found on the Movies page",
                moviesPage.isMovieListVisible());
    }

    @Then("the filter panel should open showing Genre Language and Format sections")
    public void theFilterPanelShouldOpenShowingSections() {
        assertTrue("Filter panel did not open on Movies page",
                moviesPage.isFilterPanelOpen());
        System.out.println("TC-22 PASS: Filter panel is open with section tabs.");
    }

    @Then("the Language section heading should be visible in the filter panel")
    public void theLanguageSectionHeadingShouldBeVisible() {
        assertTrue("Language section heading is not visible in the filter panel",
                moviesPage.isLanguageSectionPresent());
        System.out.println("TC-23 PASS: Language section heading is present.");
    }

    @Then("a list of language options should be captured with at least 2 entries")
    public void aListOfLanguageOptionsShouldBeCaptured() {
        assertNotNull("Language list is null", extractedLanguages);
        assertTrue("Language list has fewer than 2 entries. Found: " + extractedLanguages,
                extractedLanguages.size() >= 2);
        System.out.println("TC-24 PASS: " + extractedLanguages.size()
                + " language options extracted: " + extractedLanguages);
    }

    @Then("all language options should be printed to the console with no empty entries")
    public void allLanguageOptionsShouldBePrintedToConsole() {
        assertNotNull("Language list is null", extractedLanguages);
        assertFalse("Language list is empty", extractedLanguages.isEmpty());

        moviesPage.printLanguageOptions(extractedLanguages);

        for (String lang : extractedLanguages) {
            assertFalse("Empty language entry found in list", lang == null || lang.trim().isEmpty());
        }
        System.out.println("TC-25 PASS: All " + extractedLanguages.size()
                + " language options printed. No empty entries.");
    }
}
