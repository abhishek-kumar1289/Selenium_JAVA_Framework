package listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import base.BaseClass;
import utilities.ExtentReportManager;

public class TestListener implements ITestListener{

	@Override
	public void onTestStart(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		ExtentReportManager.startTest(testName);
		ExtentReportManager.logStep("Test started: "+ testName);
				
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		ExtentReportManager.logStepWithScreenshot(BaseClass.getDriver(), testName, "Test End: "+testName + "- Test Passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		String failureMessage = result.getThrowable().getMessage();
		ExtentReportManager.logStep(failureMessage);
		ExtentReportManager.logStepWithScreenshot(BaseClass.getDriver(), testName, "Test End: "+testName + "- Test Failed");
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		ExtentReportManager.logSkip(testName);
	}

	@Override
	public void onStart(ITestContext context) {
		ExtentReportManager.getReporter();
	}

	@Override
	public void onFinish(ITestContext context) {
		ExtentReportManager.endTest();
	}
	
	
}
