package components;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;

import java.io.File;
import java.io.IOException;

public class Login {
    public static void attemptInvalidLogin(WebDriver driver, String mobileNumber) {
        if(mobileNumber == null || mobileNumber.equals("")) {
            mobileNumber = "1234567890";
        }

        driver.findElement(By.xpath(LoginLocators.profile)).click();
        driver.findElement(By.xpath(LoginLocators.mobileNumberField)).sendKeys(mobileNumber);
        driver.findElement(By.xpath(LoginLocators.continueBtn)).click();

        try {
            File sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String destinationPath = "Images/Invalid_Login_Attempt.png";
            File destinationFile = new File(destinationPath);
            FileUtils.copyFile(sourceFile, destinationFile);
            System.out.println("Screenshot saved to: " + destinationFile.getAbsolutePath());
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }
}