package utilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {

	private static ExtentReports extent;
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
	private static Map<Long, WebDriver> driverMap = new HashMap<>();

	/* This method is used for setting up how overall our reports will look */
	public synchronized static ExtentReports getReporter() {
		if (extent == null) {
			String reportPath = System.getProperty("user.dir") + "\\src\\test\\resources\\ExtentReport\\ExtentReport.html";
			System.out.println("Checking initialization of extentreports: "+ reportPath);
			ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
			spark.config().setReportName("Automation Test Report");
			spark.config().setDocumentTitle("Framework Setup Report");
			spark.config().setTheme(Theme.STANDARD);

			extent = new ExtentReports();
			extent.attachReporter(spark); //used to attach the report to the path given above
			 
			// Adding system information
			extent.setSystemInfo("Operating System", System.getProperty("os.name"));
			extent.setSystemInfo("Java version", System.getProperty("java.version"));
			extent.setSystemInfo("User Name", System.getProperty("user.name"));
		}
		System.out.println("Check extent: "+ extent);
		return extent;
	}

	/*
	 * This method is used to create and manage individual test entries in extent
	 * report
	 */
	public synchronized static ExtentTest startTest(String testName) {

		/* Creates a new entry in extent report by calling ExtentReports */
		ExtentTest extentTest = getReporter().createTest(testName);

		test.set(extentTest); // To store extent test in a thread local

		return extentTest; // return method to use by test method for logging steps...and all
	}

	/*
	 * This method is used to write all the reports in actual reports If this method
	 * is not called (after each test for separate report or after over all test
	 * suite execution e.g. @AfterSuite for one single report) even though all test
	 * pass but no report will get generated
	 */
	public synchronized static void endTest() {
		getReporter().flush();
	}

	public static ExtentTest getTest() {
		return test.get();
	}

	/*
	 * This method is used to get the name of the current test case in current
	 * thread
	 */
	public synchronized static String getTestName() {
		ExtentTest currentTest = getTest();
		/*
		 * if statement checks if current Test object is actually exist in thread or not
		 * otherwise it will throw NullPointerException
		 */
		if (currentTest != null) {
			/*
			 * .getModel() contains various details of the current test case i.e name,
			 * status, start time and .getName() is used to extract name of the current test
			 * case
			 */
			return currentTest.getModel().getName();
		} else {
			return "No test is active for current thread";
		}

	}

	/*
	 * This method is used to simplify the
	 * ExtentReportManager.getTest().log(status.INFO, "Message to print")
	 */
	public static void logStep(String logMessage) {
		getTest().info(logMessage);
	}

	public static void logStepWithScreenshot(WebDriver driver, String logMessage, String screenShotMessage) {
		getTest().pass(logMessage);
		attachScreenshot(driver, screenShotMessage);

	}

	public static void logFailure(WebDriver driver, String logMessage, String screenShotMessage) {
		String colorMessage = String.format("<span style ='color:red';>%s</span>", logMessage);
		getTest().fail(colorMessage);
		attachScreenshot(driver, screenShotMessage);

	}

	public static void logSkip(String logMessage) {
		getTest().skip(logMessage);
	}

	/*
	 * This method is used to capture screenshot and embed it in report after
	 * converting in Base64 string to directly include in HTML report
	 */
	public static String takeScreenshot(WebDriver driver, String screenshotName) {
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		String timeStamp = new SimpleDateFormat("yyyy-mm-dd_HH-mm-ss").format(new Date());

		String destPath = System.getProperty("user.dir") + "/src/test/resources/Screenshot/" + screenshotName + "_"
				+ timeStamp + ".png";

		try {
			FileUtils.copyFile(src, new File(destPath));
		} catch (IOException e) {

			e.printStackTrace();
		}

		// convert screenshot to base64 for embedding in extent report
		String base64Format = convertToBase64(src);
		return base64Format;
	}

	/*
	 * This method is used to convert the screenshot captured in .png format to
	 * string
	 */
	public static String convertToBase64(File screenshotFile) {

		byte[] fileContent = null;
		try {
			fileContent = FileUtils.readFileToByteArray(screenshotFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return java.util.Base64.getEncoder().encodeToString(fileContent);

	}

	/*
	 * This method is used to attach the screenshot in string format with message in
	 * report
	 */
	public synchronized static void attachScreenshot(WebDriver driver, String message) {
		try {
			String screenshotBase64 = takeScreenshot(driver, getTestName());
			// .media.. is the helper class for building media entities
			getTest().info(message, com.aventstack.extentreports.MediaEntityBuilder
					.createScreenCaptureFromBase64String(screenshotBase64).build());
		} catch (Exception e) {
			getTest().fail("Failed to attach screenshot: " + message);
			e.printStackTrace();
		}

	}

	public static void registerDriver(WebDriver driver) {
		driverMap.put(Thread.currentThread().threadId(), driver);
	}

}
