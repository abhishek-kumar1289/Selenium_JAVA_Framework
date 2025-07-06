package base;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import actionDrivers.ActionDriver;
import utilities.ExtentReportManager;

public class BaseClass extends ConfigurationSetup {

	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	private static ThreadLocal<ActionDriver> actionDriver = new ThreadLocal<>();

	// public WebDriver driver;

	@BeforeMethod
	public synchronized void setupDriver() {

		System.out.println("Setting up driver for: " + this.getClass().getSimpleName());
		System.out.println("Setting up driver for: " + this.getClass().getModifiers());
		System.out.println("Setting up driver for: " + this.getClass().getName());

		String browserName = prop.getProperty("browser");

		if (browserName.equalsIgnoreCase("Chrome")) {

			/*
			 * From selenium 4 we don't need to explicit mention driver path, directly it
			 * will take path
			 */
			
			ChromeOptions chromeOption = new ChromeOptions();
			chromeOption.addArguments("--headless"); //Used to run without seeing browser
			chromeOption.addArguments("--disable-gpu"); 
			chromeOption.addArguments("--no-sandbox"); //Disabling security feature of browser
			chromeOption.addArguments("--disable-notification");
			chromeOption.addArguments("--disable-dev-shm-usage"); //To prevent out of memory for certain environments		
			
			
			// driver = new ChromeDriver();
			driver.set(new ChromeDriver(chromeOption)); // This way is used because of threadLocal we have used
			ExtentReportManager.registerDriver(getDriver());

		} else if (browserName.equalsIgnoreCase("edge")) {
			
			EdgeOptions edgeOption = new EdgeOptions();
			edgeOption.addArguments("--headless");
			edgeOption.addArguments("--disable-gpu");
			edgeOption.addArguments("--no-sandbox");
			edgeOption.addArguments("disable-dev-shm-usage");
			edgeOption.addArguments("--disable-notification");

			driver.set(new EdgeDriver(edgeOption));
			ExtentReportManager.registerDriver(getDriver());
		} else {

			throw new IllegalArgumentException("Browser not supported: " + browserName);

		}
		/* This is used to set thread local set() method for action driver class */
		actionDriver.set(new ActionDriver(driver.get()));

		driver.get().manage().window().maximize();

		int implicitWait = Integer.parseInt(prop.getProperty("implicitWait"));
		driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));

		driver.get().get(prop.getProperty("URL"));

	}

	/* If driver is not null then set the driver instance */
	public static WebDriver getDriver() {
		if (driver.get() == null) {
			throw new IllegalStateException("Driver is not initialized");
		}
		return driver.get();
	}

	@AfterMethod
	public synchronized void tearDown() {
		if (driver != null) {
			driver.get().quit();
			driver.remove(); // It's required for removing thread after each execution

		}
		//--This has been implemented in TestListener
		//ExtentReportManager.endTest(); // To create the report in html format we need to close the extent report

	}
}
