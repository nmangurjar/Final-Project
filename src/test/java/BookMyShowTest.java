import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BookMyShowTest {
    public WebDriver driver;
    public static void main(String[] args) {
        BookMyShowTest obj = new BookMyShowTest();
        obj.driver = ManageDriver.getDriver();
        System.out.println(((JavascriptExecutor) obj.driver).executeScript("return navigator.webdriver;"));

        obj.driver.get("https://in.bookmyshow.com");
        WebDriverWait loadPage = new WebDriverWait(obj.driver, Duration.ofSeconds(30));
        // To stop the script until CSS, JS, Images are downloaded, if not then the required elements might not exist in DOM tree!
        loadPage.until(d -> ((JavascriptExecutor) d).executeScript("return document.readyState;").equals("complete"));
        obj.driver.manage().window().maximize();

        WebDriverWait wait = new WebDriverWait(obj.driver, Duration.ofSeconds(10));
        // To find details of sports events for this weekend
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[text()='Mumbai']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Sports']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[text()='This Weekend'])[2]"))).click();
        List<WebElement> events = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div/div[@class and @style]/a")));
        for(WebElement event: events) {
            System.out.println(event.getText());
        }
        //System.out.println("Len: " +events.toArray().length);

        // To find languages available for the selected city
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Movies']"))).click();
        List<WebElement> languages = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@height]/div/div[2]/div")));
        for(WebElement language: languages) {
            System.out.println(language.getText());
        }

        ManageDriver.clearData(obj.driver);

        obj.driver.findElement(By.xpath("//button[@aria-label = 'Sign in']")).click();
        ((JavascriptExecutor) obj.driver).executeScript("arguments[0].click();", obj.driver.findElement(By.xpath("//div[@data-testid]//div[text() = 'Continue with Google']")));
        //ManageDriver.tearDown(obj.driver);
    }
}