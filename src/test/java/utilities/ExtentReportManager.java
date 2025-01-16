package utilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseTest;

public class ExtentReportManager implements ITestListener {

	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;
	public ThreadLocal<ExtentTest> tLocalExtentTest = new ThreadLocal<>();

	public static boolean reportStructureGenerated = false;
	public static Lock lock = new ReentrantLock();

	public static boolean browserUpdate = false;

	public List<String> browserName = new ArrayList<>();

	public void onStart(ITestContext context) {

		lock.lock();

		if (reportStructureGenerated == false) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
			Date dt = new Date();
			String fileName = df.format(dt);

			sparkReporter = new ExtentSparkReporter(
					System.getProperty("user.dir") + "/reports/file_" + fileName + ".html");

			sparkReporter.config().setDocumentTitle("Automation report");
			sparkReporter.config().setReportName("Open Cart Report");
			sparkReporter.config().setTheme(Theme.DARK);

			extent = new ExtentReports();

			extent.attachReporter(sparkReporter); // configure the UI in the report

			extent.setSystemInfo("Application", "Open Cart");
			extent.setSystemInfo("Tester Name", System.getProperty("user.name"));
			extent.setSystemInfo("Environment", "QA");
			extent.setSystemInfo("OS", context.getCurrentXmlTest().getParameter("operatingSystem"));

			List<String> groupName = context.getCurrentXmlTest().getIncludedGroups();
			if (groupName.isEmpty() == false) {
				extent.setSystemInfo("Groups", groupName.toString());
			}
			reportStructureGenerated = true;
		}
		lock.unlock();

		browserName.add(context.getCurrentXmlTest().getParameter("browser"));
	}

	public void onTestSuccess(ITestResult result) {
//		1. Create an entry in the report corresponding to the particular @Test method/class.
		test = extent.createTest(result.getTestClass().getName());
		tLocalExtentTest.set(test);
//		2. Assign group to the particular entry
		tLocalExtentTest.get().assignCategory(result.getMethod().getGroups());
//		3. Update the status in the report
		tLocalExtentTest.get().log(Status.PASS, result.getMethod().getMethodName());
		tLocalExtentTest.get().log(Status.INFO, result.getMethod().getMethodName());
	}

	public void onTestFailure(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName());
		tLocalExtentTest.set(test);
		tLocalExtentTest.get().assignCategory(result.getMethod().getGroups());
		tLocalExtentTest.get().log(Status.FAIL, result.getMethod().getMethodName());
		tLocalExtentTest.get().log(Status.INFO, result.getThrowable());
		try {
			String screenShotPath = captureScreenshot();
			tLocalExtentTest.get().addScreenCaptureFromPath(screenShotPath);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void onTestSkipped(ITestResult result) {
//		1. Create an entry in the report corresponding to the particular @Test method/ Class
		test = extent.createTest(result.getTestClass().getName());
		tLocalExtentTest.set(test);
//		2. Assign group to the particular entry
		tLocalExtentTest.get().assignCategory(result.getMethod().getGroups());
//		3. Update the status
		tLocalExtentTest.get().log(Status.SKIP, result.getName());
		tLocalExtentTest.get().log(Status.INFO, result.getThrowable().getMessage());
	}

	public void onFinish(ITestContext context) {
		lock.lock();
		if (browserUpdate == false) {
			extent.setSystemInfo("Browser", browserName.toString());
			browserUpdate = true;
		}
		extent.flush();
		lock.unlock();
	}
	
	private static String captureScreenshot() throws IOException, InterruptedException {
		
		File src = ((TakesScreenshot) BaseTest.tLocalDriver.get()).getScreenshotAs(OutputType.FILE);
		
		lock.lock();
//		1. Create screenshot file Name
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		Date dt = new Date();
		String fileName = df.format(dt);

//		2. Set the target File Path
		String targetFilePath = System.getProperty("user.dir") + "/screenshots/screenshot_" + fileName + ".png";

		FileUtils.copyFile(src, new File(targetFilePath));
		
		lock.unlock();
		
		return targetFilePath;
	}

	public void removeTest() {
		tLocalExtentTest.remove();
	}
}
