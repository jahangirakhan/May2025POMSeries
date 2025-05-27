package com.qa.opencart.tests;

import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.opencart.Constants.AppConstants;
import com.qa.opencart.base.BaseTest;

public class AccountsPageTest extends BaseTest {

    // inorder to reach account page, we need to go through loginpage isnt it?
    @BeforeClass
    public void accSetup() {
        accPage = loginPage.doLogin(prop.getProperty("uname"), prop.getProperty("pass"));
        // stroing into accpage reference variable
    }

    @Test
    public void accountPageTitleTest() {
        String acttitle = accPage.getAccountsPageTitle();
        Assert.assertEquals(acttitle, AppConstants.Account_Page_Title);
    }

    @Test
    public void isLogoutLinkExistTest() {
        Assert.assertTrue(accPage.isLogoutLinkExist());
    }

    @Test
    public void accPageHeaderCountTest() {
        Assert.assertEquals(accPage.getTotalAccountPageHeader(), AppConstants.Account_Page_Header_Count);
    }

    @Test
    public void accPageHeaderTest() {
        List<String> actualheaderslist = accPage.getAccPageHeader();
        Assert.assertEquals(actualheaderslist, AppConstants.Expected_ACC_Page_Headers_List);
    }
    
    // we use data provider when searching multiple items in website.
    @DataProvider
    public Object[][] getSearchKey()
    {
    	return new Object[][]
    			{
    		{"macbook",3},
    		{"imac",1},
    		{"samsung",2}
    			};
    }
    
    @Test(dataProvider ="getSearchKey")
    //it expects 2 variables in bracket one is what to search and count of the result
    public void searchCountTest(String searchkey, int searchcount) {
    	//we remove hard coded like macbook, imac  instead we use variable
		resultsPage = accPage.doSearch(searchkey);
		Assert.assertEquals(resultsPage.getSearchResultsCount(), searchcount);
	}
    

	@DataProvider
	public Object[][] getSearchData() {
		return new Object[][] {
			{"macbook", "MacBook Pro"},
			{"macbook", "MacBook Air"},
			{"imac", "iMac"},
			{"samsung", "Samsung SyncMaster 941BW"},
			{"samsung", "Samsung Galaxy Tab 10.1"}
		};
	}
    
	@Test(dataProvider = "getSearchData")
	public void searchTest(String searchKey, String productName) {
		resultsPage = accPage.doSearch(searchKey);
		productInfoPage = resultsPage.selectProduct(productName);
		Assert.assertEquals(productInfoPage.getProductHeader(), productName);
	}

}
