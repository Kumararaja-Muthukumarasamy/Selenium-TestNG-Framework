package com.framework.listener;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.framework.driver.DriverManager;
import com.framework.reports.ExtentManager;
import com.framework.reports.ExtentTestManager;
import com.framework.utils.ScreenshotUtil;
public class ReportListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {

        String testName = result.getMethod().getMethodName();

        String browser = result.getTestContext()
                .getCurrentXmlTest()
                .getParameter("browser");

        ExtentTest extentTest = ExtentManager.getInstance()
                .createTest(testName + " [" + browser + "]");

        ExtentTestManager.setTest(extentTest);

        // 🔥 Assign groups (FIXED)
        for (String group : result.getMethod().getGroups()) {
            ExtentTestManager.getTest().assignCategory(group);
        }

        // 🔥 Add dynamic info
        ExtentTestManager.getTest().info("🚀 Test Started");
        ExtentTestManager.getTest().info("Browser: " + browser);
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        ExtentTestManager.getTest().pass("✅ Test Passed");
        ExtentTestManager.getTest().info("Execution completed successfully");

        ExtentTestManager.getTest().info("URL: "
                + DriverManager.getDriver().getCurrentUrl());
    }

    @Override
    public void onTestFailure(ITestResult result) {

        String testName = result.getMethod().getMethodName();

        String browser = result.getTestContext()
                .getCurrentXmlTest()
                .getParameter("browser");

        String base64Screenshot = ScreenshotUtil.captureScreenshotBase64();

        ExtentTestManager.getTest().fail(result.getThrowable());

        // 🔥 INLINE IMAGE FIX
        ExtentTestManager.getTest().fail("Test Failed: " 
                + testName + " [" + browser + "]",
                MediaEntityBuilder
                        .createScreenCaptureFromBase64String(base64Screenshot)
                        .build()
        );
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTestManager.getTest().skip("⚠ Test Skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentManager.getInstance().flush();
    }
}