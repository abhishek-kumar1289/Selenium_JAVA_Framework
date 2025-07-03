package pageTest;

import org.testng.annotations.Test;

import base.BaseClass;
import utilities.ExtentReportManager;

public class launchApplication extends BaseClass{
	

  @Test
  public void f() {		
	  //ExtentReportManager.startTest("Check launch of application"); --This has been implemented in TestListener
	  String title = getDriver().getTitle();
	  assert title.equals("Online Shopping site in India: Shop Online for Mobiles, Books, Watches, Shoes and More - Amazon.in"):"Title doesn't matched "+title;
	  ExtentReportManager.logStep("Title is correctly matched: "+ title);
	  System.out.println("Title matches");
	  ExtentReportManager.logFailure(getDriver(), "Checking log failure colour", title);
	  
  }
}
