package com.framework.driver;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.framework.config.ConfigReader;

public final class DriverFactory {

	private DriverFactory() {}

	public static WebDriver initDriver(String browser, String runMode, String hubUrl) {

		WebDriver driver;

		if ("remote".equalsIgnoreCase(runMode)) {
			driver = createRemoteDriver(browser, hubUrl);
		} else {
			driver = createLocalDriver(browser);
		}
		DriverManager.setDriver(driver);
		applyBasicSettings(driver);

		return driver;
	}


	// ✅ LOCAL
	private static WebDriver createLocalDriver(String browser) {
		switch (browser.toLowerCase()) {
		case "chrome":
			return new ChromeDriver(getChromeOptions());

		case "firefox":
			return new FirefoxDriver(getFirefoxOptions());

		case "edge":
			return new EdgeDriver(getEdgeOptions());

		default:
			throw new IllegalArgumentException("Invalid browser: " + browser);
		}
	}

	// ✅ REMOTE (Grid / Docker / Cloud)
	private static WebDriver createRemoteDriver(String browser, String hubUrl) {

		try {
			URL url = new URL(hubUrl);

			switch (browser.toLowerCase()) {
			case "chrome":
				return new RemoteWebDriver(url, getChromeOptions());

			case "firefox":
				return new RemoteWebDriver(url, getFirefoxOptions());

			case "edge":
				return new RemoteWebDriver(url, getEdgeOptions());

			default:
				throw new IllegalArgumentException("Invalid browser: " + browser);
			}

		} catch (MalformedURLException e) {
			throw new RuntimeException("Invalid hub URL: " + hubUrl, e);
		}
	}

	// ✅ COMMON SETTINGS
	private static void applyBasicSettings(WebDriver driver) {
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
	}
	
	 // ✅ OPTIONS (kept simple)
    private static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();

        if (Boolean.parseBoolean(ConfigReader.getPropValue("headless"))) {
            options.addArguments("--headless=new");
        }

        if (Boolean.parseBoolean(ConfigReader.getPropValue("incognito"))) {
            options.addArguments("--incognito");
        }

        return options;
    }

    private static FirefoxOptions getFirefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();

        if (Boolean.parseBoolean(ConfigReader.getPropValue("headless"))) {
            options.addArguments("-headless");
        }

        if (Boolean.parseBoolean(ConfigReader.getPropValue("incognito"))) {
            options.addArguments("-private");
        }

        return options;
    }

    private static EdgeOptions getEdgeOptions() {
        EdgeOptions options = new EdgeOptions();

        if (Boolean.parseBoolean(ConfigReader.getPropValue("headless"))) {
            options.addArguments("--headless=new");
        }

        if (Boolean.parseBoolean(ConfigReader.getPropValue("incognito"))) {
            options.addArguments("--inprivate");
        }

        return options;
    }
}