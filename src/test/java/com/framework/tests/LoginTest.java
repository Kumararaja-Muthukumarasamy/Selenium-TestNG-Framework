package com.framework.tests;


import org.testng.Assert;
import org.testng.annotations.Test;

import com.framework.base.BaseTest;
import com.framework.pages.DashboardPage;
import com.framework.pages.LoginPage;

public class LoginTest extends BaseTest {

	@Test(enabled=true, groups = {"smoke", "login"})	
	public void verifyLoginNormal() {		
		LoginPage login = new LoginPage();
		login.enterUsername("Admin");
		login.enterPassword("admin123");
		login.clickLoginButton();
	}

	@Test(enabled=false)
	public void verifyLoginWithMethodChaining() {		
		LoginPage login = new LoginPage();
		login.enterUsername("Admin").enterPassword("admin123").clickLoginButton();
	}

	@Test(enabled=true)
	public void verifyLoginByUsingDashboardObjects() throws InterruptedException {

		DashboardPage dashboard =
				new LoginPage()
				.enterUsername("Admin")
				.enterPassword("admin123")
				.clickLoginButton();

		String currentUrl = dashboard.getCurrentUrl();
		
		Thread.sleep(2000);

		Assert.assertTrue(currentUrl.contains("dashboard"), "Url doesn't contain dashboared");

		logger.info("Login successful, navigated to dashboard");
	}
	
	@Test(enabled=true)
	public void verifyInvalidLogin() {

	    LoginPage loginPage = new LoginPage();

	    loginPage.enterUsername("Admin123")
	             .enterPassword("admin123")
	             .clickLoginButton();

	    // 🔥 Validate error message
	    Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
	            "Error message is not displayed");

	    String error = loginPage.getErrorMessage();

	    Assert.assertFalse(error.contains("Invalid"),
	            "Expected error message not shown. Actual: " + error);

	    logger.info("Invalid login validation successful");
	}
}