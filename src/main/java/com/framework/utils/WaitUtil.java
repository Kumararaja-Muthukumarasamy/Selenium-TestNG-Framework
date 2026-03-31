package com.framework.utils;

import com.framework.driver.DriverManager;
import com.framework.config.ConfigReader;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public final class WaitUtil {

    private WaitUtil() {}

    // 🔹 Central wait instance (configurable timeout)
    private static WebDriverWait getWait() {
        int timeout = Integer.parseInt(ConfigReader.getPropValue("timeout")); // e.g., 10
        return new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeout));
    }

    // 🔹 Wait for element visibility
    public static WebElement waitForVisibility(By locator) {
        return getWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // 🔹 Wait for element clickable
    public static WebElement waitForClickable(By locator) {
        return getWait().until(ExpectedConditions.elementToBeClickable(locator));
    }

    // 🔹 Wait for full page load (used for navigation & screenshots)
    public static void waitForPageLoad() {
        try {
            WebDriver driver = DriverManager.getDriver();

            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(webDriver ->
                            ((JavascriptExecutor) webDriver)
                                    .executeScript("return document.readyState")
                                    .equals("complete"));

        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    public static void waitForPageToBeVisible() {
        try {
            WebDriver driver = DriverManager.getDriver();

            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));

        } catch (Exception e) {
            // don't break execution
        }
    }
    
    // 🔹 Small stabilization wait (use only when needed)
    public static void smallStabilizationWait() {
        try {
            Thread.sleep(300); // minimal buffer
        } catch (InterruptedException ignored) {}
    }
}