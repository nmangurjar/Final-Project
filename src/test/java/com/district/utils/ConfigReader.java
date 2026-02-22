package com.district.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Reads values from config.properties file.
 */
public class ConfigReader {

    private static final Properties properties = new Properties();

    static {
        try {
            FileInputStream fis = new FileInputStream(
                    "src/test/resources/config.properties");
            properties.load(fis);
            fis.close();
        } catch (IOException e) {
            throw new RuntimeException("Could not load config.properties: " + e.getMessage());
        }
    }

    public static String get(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Property '" + key + "' not found in config.properties");
        }
        return value.trim();
    }

    public static String getBaseUrl() {
        return get("baseUrl");
    }

    public static String getBrowser() {
        return get("browser");
    }

    public static int getExplicitWait() {
        return Integer.parseInt(get("explicitWaitSeconds"));
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(get("headless"));
    }
}
