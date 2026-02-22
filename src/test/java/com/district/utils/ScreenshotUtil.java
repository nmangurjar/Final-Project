package com.district.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility for taking screenshots.
 */
public class ScreenshotUtil {

    private static final String SCREENSHOT_DIR = "test-output/screenshots/";

    /**
     * Takes a screenshot and saves it to the screenshots directory.
     *
     * @param driver   the WebDriver instance
     * @param fileName base file name (without extension)
     * @return the absolute path of the saved screenshot file
     */
    public static String takeScreenshot(WebDriver driver, String fileName) {
        try {
            // Ensure directory exists
            Path dir = Paths.get(SCREENSHOT_DIR);
            if (!Files.exists(dir)) {
                Files.createDirectories(dir);
            }

            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String safeFileName = fileName.replaceAll("[^a-zA-Z0-9_-]", "_");
            String fullPath = SCREENSHOT_DIR + safeFileName + "_" + timestamp + ".png";

            byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            try (FileOutputStream fos = new FileOutputStream(fullPath)) {
                fos.write(screenshotBytes);
            }
            System.out.println("Screenshot saved: " + fullPath);
            return fullPath;
        } catch (IOException e) {
            System.err.println("Failed to save screenshot: " + e.getMessage());
            return "";
        }
    }

    /**
     * Takes a screenshot and returns the raw bytes (used by Cucumber hooks for embedding).
     */
    public static byte[] takeScreenshotAsBytes(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}
