package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.opencart.Constants.AppConstants;
import com.qa.opencart.base.BaseTest;

public class LoginPageTest extends BaseTest {

    @Test
    public void loginPageTitleTest() {
        String acttitle = loginPage.getLoginPageTitle();
        Assert.assertEquals(acttitle, AppConstants.Login_Page_Title);
    }

    @Test
    public void forgetPwdLinkExist() {
        Assert.assertTrue(loginPage.isPwdLinkExist());
    }

    @Test
    public void logoExistTest() {
        Assert.assertTrue(loginPage.isLogoExist());
    }

    @Test
    public void loginPageURLTest() {
        String actURL = loginPage.getLoginURL();
        Assert.assertTrue(actURL.contains(AppConstants.Login_Page_Fraction_Url));
    }

    @Test
    public void loginTest() {
        accPage = loginPage.doLogin(prop.getProperty("uname"), prop.getProperty("pass"));
        Assert.assertEquals(accPage.getAccountsPageTitle(), AppConstants.Account_Page_Title);
        ;
    }
}
