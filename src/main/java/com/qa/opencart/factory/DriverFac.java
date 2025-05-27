package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.errors.apperror;
import com.qa.opencart.exceptions.BrowserException;
import com.qa.opencart.exceptions.FrameworkException;

public class DriverFac {

	WebDriver driver;
	Properties prop;
	public static String isHighlight;
	OptionsManager optionsManager;
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();

	public WebDriver initdriver(Properties prop) {
		String browserName = prop.getProperty("browser");
		// System.out.println("browser name is : :" + browserName);
		// Log.info(prop.getProperty("testname") + " and browser name is : " +
		// browserName);

		isHighlight = prop.getProperty("highlight");

		optionsManager = new OptionsManager(prop);

		switch (browserName.toLowerCase().trim()) {
		case "chrome":
			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				// run tcs on remote/container:
				// init_remoteDriver("chrome");
			} else {
				// run tcs in local:
				tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			}
			break;
		case "firefox":
			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				// run tcs on remote/container:
				// init_remoteDriver("firefox");
			} else {
				// run tcs in local:
				tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			}
			break;
		case "edge":
			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				// run tcs on remote/container:
				// init_remoteDriver("edge");
			} else {
				// run tcs in local:
				tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			}
			break;

		case "safari":
			tlDriver.set(new SafariDriver());
			break;

		default:
			// System.out.println(AppError.INVALID_BROWSER_MESG + browserName + " is
			// invalid");
			// Log.error(AppError.INVALID_BROWSER_MESG + browserName + " is invalid");
			throw new BrowserException(apperror.Invalid_Browser_Message);
		}

		getDriver().manage().window().maximize();
		getDriver().manage().deleteAllCookies();
		getDriver().get(prop.getProperty("url"));

		return getDriver();

	}

	/**
	 * this method is returning the driver with threadlocal
	 * 
	 * @return
	 */
	public static WebDriver getDriver() {
		return tlDriver.get();
	}

	public Properties initProp() {
		prop = new Properties();
		FileInputStream ip = null;

		String envName = System.getProperty("env");

		try {
			if (envName == null) {
				ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
			} else {
				switch (envName.toLowerCase().trim()) {
				case "qa":
					ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
					break;
				case "dev":
					ip = new FileInputStream("./src/test/resources/config/dev.config.properties");
					break;
				case "stg":
					ip = new FileInputStream("./src/test/resources/config/stg.config.properties");
					break;
				case "uat":
					ip = new FileInputStream("./src/test/resources/config/uat.config.properties");
					break;
				case "prod":
					ip = new FileInputStream("./src/test/resources/config/config.properties");
					break;
				default:
					System.out.println("plz pass the right env name..." + envName);
					throw new FrameworkException("INVALID ENV NAME");
				}
			}

			prop.load(ip);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return prop;
	}
	public static String getScreenshot(String methodName) {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);// temp dir
		String path = System.getProperty("user.dir") + "/screenshot/" + methodName + "_" + System.currentTimeMillis()
				+ ".png";
		File destination = new File(path);
		try {
			FileHandler.copy(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return path;
	}
}