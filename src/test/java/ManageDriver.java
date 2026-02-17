import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v142.network.Network;
import java.util.Optional;

import java.util.Scanner;

public class ManageDriver {
    static final String[] availableBrowsers = {"chrome", "edge"}; // Add available broswers

    private static boolean isBrowserAvailable(final String name) {
        boolean available = false;

        for(String browser : ManageDriver.availableBrowsers) {
            if(browser.equals(name)) {
                available = true;
                break;
            }
        }
        return available;
    }

    private static String getBrowserName(Scanner sc) {
        String name = "";

        System.out.println("Please choose a browser from the following:");
        for(String browserName: ManageDriver.availableBrowsers) {
            System.out.println(browserName);
        }

        name = sc.nextLine().trim();
		/*
		  If the control flow reaches the fun.call, the string is not null and empty so, we can convert it to lower case without any worry.
		  Because the control flow will return immediately if the string is null or empty since the order of evaluation is from left to right for 'Logical OR',
          It will never reach the fun.call to begin with!
        */
        while(name == null || name.equals("") || !name.matches("[a-zA-Z]{4,7}") || !ManageDriver.isBrowserAvailable(name.toLowerCase())) {
            System.out.println("Please enter a valid browser name: ");
            name = sc.nextLine().trim();
        }
        return name.toLowerCase();
    }

    public static WebDriver getDriver() {
        WebDriver driver = null;
        String browser = "";
        Scanner sc = new Scanner(System.in);

        browser = ManageDriver.getBrowserName(sc);
        sc.close();

        if(browser.equals("edge")) {
            EdgeOptions options = new EdgeOptions();
            options.addArguments("--disable-blink-features=AutomationControlled");
            driver = new EdgeDriver(options);
        } else if(browser.equals("chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--delete-automated-guest-settings");
            options.addArguments("--disable-blink-features=AutomationControlled");
            driver = new ChromeDriver(options);
        }
        return driver;
    }

    public static void tearDown(WebDriver driver) {
        driver.quit();
    }

    public static void clearData(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.localStorage.clear();");
        js.executeScript("window.sessionStorage.clear();");
        driver.manage().deleteAllCookies();

        DevTools devTools = ((HasDevTools) driver).getDevTools();
        devTools.createSession();

        // Enable Network domain to access cache/cookie methods
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()));

        devTools.send(Network.clearBrowserCache());
        System.out.println("Browser cache cleared.");

        devTools.send(Network.clearBrowserCookies());
        System.out.println("Browser cookies cleared.");
    }
}