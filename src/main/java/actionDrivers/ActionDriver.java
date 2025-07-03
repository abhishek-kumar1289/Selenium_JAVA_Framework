package actionDrivers;

import java.io.IOException;
import java.time.Duration;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.ExtentReportManager;

public class ActionDriver {

	public WebDriver driver;
	private WebDriverWait wait;

	/* Syntax for fluent wait */
	private Wait<WebDriver> fluentWait;

	public ActionDriver(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		fluentWait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(30))
				.pollingEvery(Duration.ofSeconds(1)).ignoring(NoSuchElementException.class, IOException.class);
	}

	public void waitForPageLoad(int timeOutInSec) {
		try {
			wait.withTimeout(Duration.ofSeconds(timeOutInSec)).until(WebDriver -> ((JavascriptExecutor) WebDriver)
					.executeScript("return document.readyState").equals("complete"));
			System.out.println("Page loaded withing given time: " + timeOutInSec);
		} catch (Exception e) {
			System.out.println(
					"Page didn't loaded in given time: " + timeOutInSec + " seconds.exception " + e.getMessage());
		}
	}

	/*Using fluent wait*/
	public void fluentWaitForElementToBeClickable(By by) {
		fluentWait.until(ExpectedConditions.elementToBeClickable(by));
	}
	
	
	public void click(By by) {
		String elementDescription = getElementDescription(by); 
		try {
			waitForElementToBeClickable(by);
			driver.findElement(by).click();
			ExtentReportManager.logStep("Click on element"+elementDescription);
		} catch (Exception e) {
			System.out.println("Unable to click element: " + e.getMessage());
			ExtentReportManager.logFailure(driver, "unable to click element", elementDescription+"_unable to click");
		}
	}

	public void enterText(By by, String text) {
		String elementDescription = getElementDescription(by); 
		try {
			waitForElementToBeVisible(by);
			WebElement element = driver.findElement(by);
			element.clear();
			element.sendKeys(text);

		} catch (Exception e) {
			System.out.println("Unable to enter text: " + e.getMessage());
			ExtentReportManager.logFailure(driver, "Unable to enter text message", elementDescription+" _UnableToEnter");
		}
	}

	public String getText(By by) {
		try {
			waitForElementToBeVisible(by);
			return driver.findElement(by).getText();
		} catch (Exception e) {
			System.out.println("Unable to get text from input field: " + e.getMessage());
			return "";
		}
	}

	public void compareText(By by, String expectedText) {
		try {
			waitForElementToBeVisible(by);
			String actualText = driver.findElement(by).getText();
			if (actualText.equals(expectedText)) {
				System.out.println("Text are matching: " + actualText + " equals " + expectedText);
			} else {
				System.out.println("Text are not matching: " + actualText + " not equals " + expectedText);
			}
		} catch (Exception e) {
			System.out.println("Unable to get text for comparison: " + e.getMessage());
		}
	}

	/* Used for scrolling to the element */
	public void scrollToElement(By by) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			WebElement element = driver.findElement(by);
			js.executeScript("arguments[0], scrollIntoView[true];", element);
		} catch (Exception e) {
			System.out.println("Unable to scroll to the element: " + e.getMessage());
		}
	}

	protected void waitForElementToBeClickable(By by) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(by));
		} catch (Exception e) {
			System.out.println("Element is not clickable: " + e.getMessage());
		}
	}

	protected void waitForElementToBeVisible(By by) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		} catch (Exception e) {
			System.out.println("Element is not visible: " + e.getMessage());
		}
	}
	
	public String getElementDescription(By locator) {
		if(driver ==null) return "driver is null";
		
		if(locator == null) return "Locator is null";
		
		try {
			WebElement element =driver.findElement(locator);
			
			String name = element.getDomAttribute("name");
			String id = element.getDomAttribute("id");
			String text = element.getText();
			String className = element.getDomAttribute("class");
			String placeholder = element.getDomAttribute("placeholder");
			
			if(isNotEmpty(name)) {
				return "Element with name: "+name;
			}else if(isNotEmpty(id)) {
				return "Element with id: "+id;
			}else if(isNotEmpty(text)) {
				return "Element with text: "+truncate(text,50);
			}else if(isNotEmpty(className)) {
				return "Element with class: "+className;
			}else if(isNotEmpty(placeholder)) {
				return "Element with placeholder: "+placeholder;
			}

		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return "Element is not identifiable";
	}
	
	private boolean isNotEmpty(String value) {
		return value !=null && !value.isEmpty();
	}
	
	private String truncate(String text, int maxLength) {
		
		if(text !=null && text.length()<maxLength ) {
			return text;
		}else {
			return text.substring(0, maxLength)+"...";
		}
		
	}
	
}
