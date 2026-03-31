package com.framework.pages;


import org.openqa.selenium.By;

import com.framework.base.BasePage;
import com.framework.utils.WaitUtil;

public class LoginPage extends BasePage{

	private final By username = By.xpath("//input[@placeholder='Username']");
	private final By password = By.xpath("//input[@placeholder='Password']");
	private final By submit = By.xpath("//button[normalize-space()='Login']");
	private final By errorMessage = By.xpath("//p[contains(@class,'oxd-alert-content-text')]");

	public LoginPage enterUsername(String uName) {
	   	sendKeys(username, uName, "Username Field");
		return this;
	}

	public LoginPage enterPassword(String pwd) {
	  	sendKeys(password, pwd, "Password Field");
		return this;
	}

	public DashboardPage clickLoginButton() {
		click(submit, "Login Button");
		return new DashboardPage();
	}
	
	public String getErrorMessage() {
	    return WaitUtil.waitForVisibility(errorMessage).getText();
	}

	public boolean isErrorMessageDisplayed() {
	    return WaitUtil.waitForVisibility(errorMessage).isDisplayed();
	}

	// Business-level method (recommended)
	public void login(String usernameValue, String passwordValue) {
		enterUsername(usernameValue);
		enterPassword(passwordValue);
		clickLoginButton();
	}	

	// We can follow this approach also
	public DashboardPage loginx(String usernameValue, String passwordValue) {
		enterUsername(usernameValue);
		enterPassword(passwordValue);
		return clickLoginButton();
	}
}
