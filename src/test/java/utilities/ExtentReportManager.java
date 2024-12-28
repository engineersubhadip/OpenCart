package utilities;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
//	Responsible for :-
//	1. Setting the Location of the Report
//	2. Setting the Document title and Report Name
//	3. Configuring the UI of the Report
	public ExtentSparkReporter sparkReporter;
//	Responsible for :-
//	1. Creating a blank report
//	2. Populating the common data on the report
	public ExtentReports extent;
//	Responsible for :-
//	1. Creating an entry corresponding to a @Test method/class
//	2. Assigning groups to that entry
//	3. Updating the status on the report
	public ExtentTest test;
	
//	"onStart" is specific to the particular test module
//	It is executed before any of the Annotations in the current test module gets executed.
//	"context" refers to the <test> module
	public void onStart(ITestContext context) {
		
//		1. Setting the report name dynamically :-
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		Date dt = new Date();
		String fileName = df.format(dt);
		
//		2. Setting the location of the report :-
		sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir")+"/reports/file_"+fileName+".html");
		
//		3. Setting the Document title and Report Name :-
		sparkReporter.config().setDocumentTitle("Automation report");
		sparkReporter.config().setReportName("Open Cart Report");
		sparkReporter.config().setTheme(Theme.DARK);
		
//		4. Create a blank report :-
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter); // configure the UI in the report
		
//		5. Populating the Common data :-
		extent.setSystemInfo("Application", "Open Cart");
		extent.setSystemInfo("Tester Name", System.getProperty("user.name"));
		extent.setSystemInfo("Environment", "QA");
		extent.setSystemInfo("Browser", context.getCurrentXmlTest().getParameter("browser"));
		extent.setSystemInfo("OS", context.getCurrentXmlTest().getParameter("operatingSystem"));
		
//		6. Current list of groups being executed :-
		
		List<String> groupName = context.getCurrentXmlTest().getIncludedGroups();
		if (groupName.isEmpty() == false) {
			extent.setSystemInfo("Groups", groupName.toString());
		}
	}

//	"onTestSuccess" will be executed after the successful execution of a @Test method
//	"result" denotes current @Test
	public void onTestSuccess(ITestResult result) {
//		1. Create an entry in the report corresponding to the particular @Test method/class.
		test = extent.createTest(result.getTestClass().getName());
//		2. Assign group to the particular entry
		test.assignCategory(result.getMethod().getGroups());
//		3. Update the status in the report
		test.log(Status.PASS, result.getMethod().getMethodName());
		test.log(Status.INFO, result.getMethod().getMethodName());
	}

//	"onTestFailure" will be executed after the failure of a @Test method
//	"result" denotes current @Test
	public void onTestFailure(ITestResult result) {
//		1. Create an entry corresponding to the current @Test Method/Class
		test = extent.createTest(result.getTestClass().getName());
//		2. Assign category to the particular entry
		test.assignCategory(result.getMethod().getGroups());
//		3. Update status of the entry
		test.log(Status.FAIL,result.getMethod().getMethodName());
		test.log(Status.INFO, result.getThrowable());
//		4. Attach screenshot to the corresponding entry
		try {
			String screenShotPath = BaseTest.captureScreenshot();
			test.addScreenCaptureFromPath(screenShotPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	"onTestSkipped" will be executed after the @Test method gets skipped.
//	"result" denotes current @Test.
	public void onTestSkipped(ITestResult result) {
//		1. Create an entry in the report corresponding to the particular @Test method/ Class
		test = extent.createTest(result.getTestClass().getName());
//		2. Assign group to the particular entry
		test.assignCategory(result.getMethod().getGroups());
//		3. Update the status
		test.log(Status.SKIP, result.getName());
		test.log(Status.INFO, result.getThrowable().getMessage());
	}

	public void onFinish(ITestContext context) {
//		Updating the changes we made to the report finally
		extent.flush();
	}
}
