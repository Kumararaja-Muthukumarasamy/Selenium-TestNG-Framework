package com.framework.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.framework.config.ConfigReader;
import com.framework.driver.DriverFactory;
import com.framework.driver.DriverManager;
import com.framework.utils.ScreenshotUtil;

public class BaseTest {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Parameters({"browser", "runMode", "hubUrl"})
	@BeforeMethod
	public void setup(@Optional String browser,
			@Optional String runMode,
			@Optional String hubUrl) {

		// ✅ Resolve values (TestNG → ConfigReader fallback)
		String finalBrowser = (browser != null) ? browser : ConfigReader.getPropValue("browser");
		String finalRunMode = (runMode != null) ? runMode : ConfigReader.getPropValue("runMode");
		String finalHubUrl  = (hubUrl  != null) ? hubUrl  : ConfigReader.getPropValue("hubUrl");

		// ✅ Initialize driver
		DriverFactory.initDriver(finalBrowser, finalRunMode, finalHubUrl);

		// ✅ Open application
		DriverManager.getDriver().get(ConfigReader.getEnvironmentUrl());

	}

	@AfterMethod
	public void tearDown(ITestResult result) {

		String testName = result.getTestClass().getRealClass().getSimpleName()
				+ "_" + result.getMethod().getMethodName();
		
		if (result.getStatus() == ITestResult.FAILURE) {
			String screenshotPath = ScreenshotUtil.captureScreenshot(testName);

			logger.error("Test FAILED: {}", testName);
			logger.error("Reason:", result.getThrowable());
			logger.error("Screenshot captured at: {}", screenshotPath);
		}

		DriverManager.quitDriver();
	}
}