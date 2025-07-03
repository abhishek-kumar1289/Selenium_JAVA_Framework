package pageTest;

import org.testng.annotations.Test;

import base.BaseClass;
import page.HamburgerMenu;
import utilities.ExtentReportManager;

public class HamburgerMenuTest extends BaseClass {
  @Test
  public void checkHamburgerMenuTrendingList() {
	  HamburgerMenu hm = new HamburgerMenu(getDriver());
	  //ExtentReportManager.startTest("Hamburger Menu Trending List test"); --This has been implemented in TestListener
	  hm.clickOnHamburgerName();
	  ExtentReportManager.logStep("Clicked on HamburgerMenu");
	  hm.getTrendingList();
	  ExtentReportManager.logStep("Generated Trending List");
  }
}
