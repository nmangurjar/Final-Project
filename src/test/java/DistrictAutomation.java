import components.Constants;
import components.Home;
import components.Events;
import components.Movies;
import components.Login;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.JavascriptExecutor;

import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.ManageDriver;

public class DistrictAutomation {
    WebDriver driver;

    public static void main(String[] args) {
        DistrictAutomation obj = new DistrictAutomation();
        obj.driver = ManageDriver.getDriver();
        //System.out.println(((JavascriptExecutor) obj.driver).executeScript("return navigator.webdriver;"));

        obj.driver.get(Constants.URL);
        WebDriverWait loadPage = Constants.waitUntil(obj.driver, 20);
        // To stop the script until CSS, JS, Images are downloaded, if not then the required elements might not exist in DOM tree!
        loadPage.until(d -> ((JavascriptExecutor) d).executeScript("return document.readyState;").equals("complete"));
        obj.driver.manage().window().maximize();

        Home.setLocation(obj.driver);
        Home.navigateToEventPage(obj.driver);

        Events.getEvents(obj.driver);
        Movies.getAvailableLanguages(obj.driver);
        Login.attemptInvalidLogin(obj.driver, "1234567890");
        ManageDriver.tearDown(obj.driver);
    }
}