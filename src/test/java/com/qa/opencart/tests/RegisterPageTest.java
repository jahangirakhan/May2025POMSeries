package com.qa.opencart.tests;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.security.PublicKey;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.extractor.ExcelExtractor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.Constants.AppConstants;
import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.ExcelUtil;

public class RegisterPageTest extends BaseTest{
	
	@BeforeClass
	public void regSetup()
	{
		registerPage=loginPage.navigateToRegisterPage();
	}
	
	public String getRandomEmail()
	{
		return "uiautomation"+System.currentTimeMillis()+"@open.com";
	}
	
	
	@DataProvider
	public Object[][] getRegData() throws EncryptedDocumentException, IOException, InvalidFormatException
	{
		return ExcelUtil.getTestData(AppConstants.REG_SHEET_NAME);
	}
	
	@Test(dataProvider = "getRegData")
	public void userRegisterTest(String firstname, String lastname, String telephone, String password, String subscribe)
	{
		Assert.assertTrue(registerPage.userRegisteration(firstname,lastname,getRandomEmail(),telephone,password,subscribe));
		
	}
	

}
