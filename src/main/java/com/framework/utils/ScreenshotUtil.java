package com.framework.utils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.framework.driver.DriverManager;

public final class ScreenshotUtil {
	private ScreenshotUtil() {
	}
	public static String captureScreenshot(String testName) {

	    try {
	        WebDriver driver = DriverManager.getDriver();
  	        
	        File src = ((TakesScreenshot) driver)
	                .getScreenshotAs(OutputType.FILE);

	        String filePath = "screenshots/" + testName + "_" + System.currentTimeMillis() + ".png";
 
	        Files.createDirectories(Paths.get("screenshots"));
	        Files.copy(src.toPath(), Paths.get(filePath));

	        return filePath;

	    } catch (Exception e) {
	        throw new RuntimeException("Failed to capture screenshot", e);
	    }
	}
	
	public static String captureScreenshotBase64() {

	    try {
	        TakesScreenshot ts = (TakesScreenshot) DriverManager.getDriver();
	        return ts.getScreenshotAs(OutputType.BASE64);

	    } catch (Exception e) {
	        throw new RuntimeException("Failed to capture screenshot", e);
	    }
	}
}