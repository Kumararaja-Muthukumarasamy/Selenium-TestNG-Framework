package com.framework.listener;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private static final int MAX_RETRY = 1;
    private int retryCount = 0;

    @Override
    public boolean retry(ITestResult result) {

        if (!result.isSuccess() && retryCount < MAX_RETRY) {
            retryCount++;

            System.out.println("🔁 Retry: " 
                    + result.getMethod().getMethodName() 
                    + " | Attempt: " + retryCount);

            return true;
        }

        return false;
    }
}