package com.framework.pages;

import org.openqa.selenium.By;

import com.framework.base.BasePage;

public class DashboardPage extends BasePage{

	private final By dashboardHeader = By.xpath("//h6[text()='Dashboard']");
	private final By userDropdown = By.xpath("//span[@class='oxd-userdropdown-tab']");
	private final By logoutButton = By.xpath("//a[text()='Logout']");

	public boolean isDashboardLoaded() {
		return isDisplayed(dashboardHeader);
	}

	public void openUserMenu() {
		click(userDropdown, "User Menu");
	}

	public void logout() {
		openUserMenu();
		click(logoutButton, "Logout Button");
	}
}