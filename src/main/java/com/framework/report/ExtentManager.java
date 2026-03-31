package com.framework.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.framework.config.ConfigReader;

public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports getInstance() {

        if (extent == null) {

            String reportPath = System.getProperty("user.dir")
                    + "/reports/extent-" + System.currentTimeMillis() + ".html";
            
            String reportPath_Jenkins = System.getProperty("user.dir") 
                    + "/reports/extent.html";

            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath_Jenkins);

            spark.config().setReportName("Automation Report");
            spark.config().setDocumentTitle("Test Execution Report");

            extent = new ExtentReports();
            extent.attachReporter(spark);

            // System Info (static only)
            extent.setSystemInfo("Environment", ConfigReader.getPropValue("env"));
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("User", System.getProperty("user.name"));
        }

        return extent;
    }
}