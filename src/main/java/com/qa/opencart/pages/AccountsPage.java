package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.Constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;
import org.openqa.selenium.WebElement;

public class AccountsPage {
    private WebDriver driver;
    private ElementUtil eleUtil;

    private By LogoutLink = By.linkText("Logout");
    private By headers = By.cssSelector("div#content h2");
    private By search = By.name("search");
    private By searchIcon = By.cssSelector("div#search button");

    // 2nd step create constructors
    public AccountsPage(WebDriver driver) {
        this.driver = driver;
        eleUtil = new ElementUtil(driver);

    }

    // 3rd step creating all methods

    public String getAccountsPageTitle() {
        String title = eleUtil.waitForTitleContainsAndReturn(AppConstants.Account_Page_Title,
                AppConstants.Short_Time_OUT);
        System.out.println("Account page title is: " + title);
        return title;
    }

    public boolean isLogoutLinkExist() {
        return eleUtil.isElementDisplayed(LogoutLink);

    }

    public int getTotalAccountPageHeader() {
        return eleUtil.waitForElementsVisible(headers, AppConstants.Medium_Time_OUT).size();
    }

    public List<String> getAccPageHeader() {
        List<WebElement> headerslist = eleUtil.waitForElementsVisible(headers, AppConstants.Medium_Time_OUT);
        List<String> headersValueList = new ArrayList<String>();// converitng from webelement to arraylist and this
                                                                // blank array list
        for (WebElement e : headerslist)// it iteraters the webelement and give us all texts
        {
            String header = e.getText();
            headersValueList.add(header);// it adds one by one
        }
        return headersValueList;
    }

    public ResultsPage doSearch(String searchKey) {
		System.out.println("Search Key ==>" + searchKey);
		WebElement searchEle = eleUtil.waitForElementVisible(search, AppConstants.Medium_Time_OUT);
		eleUtil.doSendKeys(searchEle, searchKey);
		eleUtil.doClick(searchIcon);
		return new ResultsPage(driver);
	}
    
    
    

	

}
