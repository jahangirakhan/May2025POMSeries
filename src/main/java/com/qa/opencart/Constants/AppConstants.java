package com.qa.opencart.Constants;

import java.util.List;

public class AppConstants {

    public static final int Short_Time_OUT = 5;
    public static final int Medium_Time_OUT = 10;
    public static final int LShort_Time_OUT = 20;

    public static final String Login_Page_Title = "Account Login";
    public static final String Account_Page_Title = "My Account";

    public static final int Account_Page_Header_Count = 4;

    public static final String Login_Page_Fraction_Url = "https://naveenautomationlabs.com/opencart/index.php?route=account/login";

    public static final List<String> Expected_ACC_Page_Headers_List = List.of("My Account", "My Orders",
            "My Affiliate Account", "Newsletter");
    
    public static final String USER_REGISTER_SUCCESS_MESSG="Your Account Has Been Created!";
    
    
    
    //****** SheetName
    public static final String REG_SHEET_NAME="register";
    
}
