package com.framework.base;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.framework.driver.DriverManager;
import com.framework.reports.ExtentTestManager;


public class BasePage {
	private WebDriver driver;
	private WebDriverWait wait;
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public BasePage() {
		this.driver = DriverManager.getDriver();
		int timeout = Integer.parseInt(System.getProperty("timeout", "30"));
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
	}

	protected WebElement waitForElementVisible(By locator) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	protected WebElement waitForElementClickable(By locator) {
		return wait.until(ExpectedConditions.elementToBeClickable(locator));
	}

	public void click(By locator, String elementName) {
	    logger.info("Clicking on {}", elementName);
	    ExtentTestManager.getTest().info("Clicking on " + elementName);
		waitForElementClickable(locator).click();
	}

	public void sendKeys(By locator, String value, String elementName) {
		 logger.info("Entering value into {}", elementName);
		 ExtentTestManager.getTest().info("Entering value on " + elementName);
		WebElement element = waitForElementVisible(locator);
		element.clear();
		element.sendKeys(value);
	}

	public String getText(By locator) {
		return waitForElementVisible(locator).getText();
	}

	public boolean isDisplayed(By locator) {
		return waitForElementVisible(locator).isDisplayed();
	}
	public String getTitle() {
		return driver.getTitle();
	}

	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}
}
