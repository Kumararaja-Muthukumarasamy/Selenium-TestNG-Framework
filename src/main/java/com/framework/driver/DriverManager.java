package com.framework.driver;

import org.openqa.selenium.WebDriver;

public final class DriverManager {

	private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

	private DriverManager() {}

	public static WebDriver getDriver() {
		WebDriver driver = driverThreadLocal.get();
		if (driver == null) {
			throw new IllegalStateException("WebDriver is not initialized for this thread.");
		}
		return driver;
	}

	static void setDriver(WebDriver driver) {
		driverThreadLocal.set(driver);
	}

	public static void quitDriver() {
		WebDriver driver = driverThreadLocal.get();
		if (driver != null) {
			driver.quit();
			driverThreadLocal.remove();
		}
	}
}	
