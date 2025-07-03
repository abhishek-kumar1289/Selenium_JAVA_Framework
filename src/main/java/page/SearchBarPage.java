package page;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import actionDrivers.ActionDriver;

public class SearchBarPage {

	private WebDriver driver;
	private ActionDriver actionDriver;

	public SearchBarPage(WebDriver driver) {
		this.driver = driver;
		this.actionDriver = new ActionDriver(driver);
	}

	private By searchBarLocator = By.cssSelector("input[id='twotabsearchtextbox']");

	public void clickOnSearchBar() {
		actionDriver.click(searchBarLocator);
	}

	public void enterTextOnSearchBar(String searchFor) {
		actionDriver.enterText(searchBarLocator, searchFor);
		driver.findElement(searchBarLocator).sendKeys(Keys.ENTER);
		actionDriver.waitForPageLoad(10);
		String title = driver.getTitle();
		System.out.println(title);
		//Assert.assertEquals(title,"Amazon.in :" +" " + searchFor);
	}
}
