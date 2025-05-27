package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.Constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class LoginPage {

    // we need to maintain private locators
    // 2. public page constructor
    // 3. public methods or actions()
    private WebDriver driver;
    private ElementUtil eleUtil;

    private By username = By.id("input-email");
    private By passwordtextbox = By.xpath("//input[@id='input-password']");
    private By loginBtn = By.xpath("//input[@type='submit']");
    private By forgetpwdlink = By.linkText("Forgotten Password");
    private By logoimg = By.cssSelector("img.img-responsive");
    private By registerLink = By.linkText("Register");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        eleUtil = new ElementUtil(driver);

    }

    // 3.
    public String getLoginPageTitle() {
        String title = eleUtil.waitForTitleContainsAndReturn(AppConstants.Login_Page_Title,
                AppConstants.Short_Time_OUT);
        System.out.println("login page title is: " + title);
        return title;
    }

    public String getLoginURL() {
        String url = eleUtil.waitForURLContainsAndReturn(AppConstants.Login_Page_Fraction_Url,
                AppConstants.Short_Time_OUT);
        // String URL = driver.getCurrentUrl();
        System.out.println("the URL is: " + url);
        return url;
    }

    public boolean isPwdLinkExist() {
        return eleUtil.isElementDisplayed(forgetpwdlink);

    }

    public boolean isLogoExist() {
        return eleUtil.isElementDisplayed(logoimg);

    }

    public AccountsPage doLogin(String userName, String pwD) {

        eleUtil.waitForElementVisible(username, AppConstants.Short_Time_OUT).sendKeys(userName);
        eleUtil.waitForElementVisible(passwordtextbox, AppConstants.Short_Time_OUT).sendKeys(pwD);
        eleUtil.doClick(loginBtn);

        // we will return accountpage class, as this is responsbile for my next actions
        return new AccountsPage(driver);

        // String title =
        // eleUtil.waitForTitleContainsAndReturn(AppConstants.Account_Page_Title,
        // AppConstants.Short_Time_OUT);
        // System.out.println("The Account page title is : " + title);
        // return title;

    }
    
    public RegisterPage navigateToRegisterPage()
    {
    	//to go to register page, we have to traverse from login page, as this register link is on login page
    	//so we used again test data driven approach and dont forget to give driver
    	eleUtil.doClick(registerLink);
    	return new RegisterPage(driver);
    }
    

}
