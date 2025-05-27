package com.qa.opencart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.qa.opencart.factory.DriverFac;
import com.qa.opencart.pages.AccountsPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.RegisterPage;
import com.qa.opencart.pages.ResultsPage;

public class BaseTest {

    DriverFac df;
    WebDriver driver;
    protected LoginPage loginPage;// we wrote this so we can inherit all the methods into test login class and we
    // used protected then only we can access this methods into logintest class
    protected Properties prop;
    protected AccountsPage accPage;
    protected ResultsPage resultsPage;
    protected ProductInfoPage productInfoPage;
    protected RegisterPage registerPage;

    @Parameters("browser")
    @BeforeTest
    public void setup(String browserName) {
        df = new DriverFac();
        prop = df.initProp();

		// check if browser param is coming from testng.xml
		if (browserName != null) {
			prop.setProperty("browser", browserName);
			

		}

		
        driver = df.initdriver(prop);// we are attaching our base driver to df reference variable and with variable
        loginPage = new LoginPage(driver);// the driver we wrote inside bracket is for mainitaing one session across all
        // base,loginpage etc

    }

    @AfterTest
    public void TearDown() {
        driver.quit();
    }

}
