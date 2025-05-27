/**
 * 
 */
package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.Constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

/**
 * 
 */
public class ResultsPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	private By searchHeader = By.cssSelector("div#content h2");// heading only
	private By results = By.cssSelector("div.product-thumb");// total number of results after u searched.

	public ResultsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver); 
	}

	public String getSearchHeader() {
		String searchHeaderValue = eleUtil.waitForElementPresence(searchHeader, AppConstants.Short_Time_OUT).getText();
		return searchHeaderValue;
	}

//	We are searching the product
	public int getSearchResultsCount() {
		int resultCount = eleUtil.waitForElementsPresence(results, AppConstants.Short_Time_OUT).size();// Search result
																										// total count
		System.out.println("search result count is ====> " + resultCount);
		return resultCount;

	}

	// Now i want to click the one of the product from search result option
	public ProductInfoPage selectProduct(String productName) {
		eleUtil.doClick(By.linkText(productName));// by link text from the result we are giving product name and
		// clicking it, this way also we can handle it

		// Now we are redirecting to product info page, so we need to return that
		return new ProductInfoPage(driver);
	}

}
