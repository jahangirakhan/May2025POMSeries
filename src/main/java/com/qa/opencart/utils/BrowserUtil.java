package com.qa.opencart.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.qa.opencart.errors.apperror;
import com.qa.opencart.exceptions.BrowserException;

public class BrowserUtil {

    WebDriver driver;

    public WebDriver initdriver(String browsername) {
        System.out.println(" The Browser Name is : " + browsername);
        switch (browsername.trim().toLowerCase()) {
            case "chrome":
                driver = new ChromeDriver();
                break;
            case "firefox":
                driver = new FirefoxDriver();
                break;

            default:
                System.out.println(apperror.Invalid_Browser_Message);
                throw new BrowserException(apperror.Invalid_Browser_Message);
            // break;
        }
        return driver;
    }

    public void launchurl(String url) {
        if (url.indexOf("http") != 0 || url.length() == 0) {
            throw new BrowserException("URL must start with 'http://' or 'https://'");
        }

        driver.get(url);
    }

    public void openUrl(String url) {
        if (url == null || url.isEmpty()) {
            System.out.println("URL is null or empty");
            return;
        }
        if (!url.startsWith("http")) {
            url = "https://" + url;
        }

        driver.navigate().to(url);
        System.out.println("Navigated to: " + url);
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public void closeBrowser() {
        driver.close();
    }

    public void quitBrowser() {
        driver.quit();
    }

}
