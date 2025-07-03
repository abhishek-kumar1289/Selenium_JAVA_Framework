package page;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import actionDrivers.ActionDriver;
import utilities.ExtentReportManager;

public class HamburgerMenu {
	
	private WebDriver driver;
	private ActionDriver actionDriver;
	
	public HamburgerMenu(WebDriver driver) {
		this.driver = driver;
		this.actionDriver = new ActionDriver(driver);
	}
	
	 
	
	private By hamburgerMenuLocator = By.id("nav-hamburger-menu");
	private By TrendingListLocator = By.xpath("//section[@Class = 'category-section' and @aria-labelledby='Trending']/ul/li");
	

	public void clickOnHamburgerName() {
		
		actionDriver.click(hamburgerMenuLocator);
	}
	
	public void getTrendingList() {
		
		List<WebElement> trendings = driver.findElements(TrendingListLocator);
		System.out.println(trendings.size());
		for(WebElement element: trendings) {
			System.out.println(element.getText());
		}
		ExtentReportManager.logStepWithScreenshot(driver, "Trending List","TrendingListScreenshot");
	}
}
