package pageTest;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.BaseClass;
import page.SearchBarPage;
import utilities.ExtentReportManager;

public class SearchBarTest extends BaseClass {

	private SearchBarPage sb;
	
	/*This @BeforeMethod is required so that we first instantiat base class webdriver and then this searchBarPages
	 * Otherwise if we directly initialize it will throw nullpointerException for webdriver
	 * Because it will first searhcBarPages tries to first set its webdriver before baseClass webdriver*/
	@BeforeMethod
	public void setupPages() {
		sb = new SearchBarPage(getDriver());
	}
	
	

	@Test
	public void searchForSamsungMobile() {
		//ExtentReportManager.startTest("SearchForSamsungMobiles"); --This has been implemented in TestListener
		sb.clickOnSearchBar();
		ExtentReportManager.logStep("Clicked on search bar");
		sb.enterTextOnSearchBar("Samsung Mobiles");
		ExtentReportManager.logStep("Entered text on search bar");
	}

	@Test
	public void searchForSamsungLaptops() {
		//ExtentReportManager.startTest("SearchForSamsungLaptops"); --This has been implemented in TestListener
		sb.clickOnSearchBar();
		ExtentReportManager.logStep("Clicked on search bar");
		sb.enterTextOnSearchBar("Samsung Laptops");
		ExtentReportManager.logStep("Entered text on search bar");
	}
}
