package testBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger; //Log4j
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseTest {

	public static WebDriver driver;
	public Logger logger;
	public Properties properties;
	public ThreadLocal<WebDriver> tLocalDriver = new ThreadLocal<>();
	public ThreadLocal<Properties> tLocalProperties = new ThreadLocal<>();
	
	@BeforeClass(groups = { "sanity", "regression", "master" })
	@Parameters({ "browser", "operatingSystem" })

	public void setUp(String browser, String operatingSystem) throws IOException {

		logger = LogManager.getLogger(this.getClass()); /// this.getClass() -> will dynamically capture the current
		// class(test case) we are running.
		// line 21 will load the log4j2.xml file
		
		if (browser.toLowerCase().contains("chrome")) {
			driver = new ChromeDriver();
		} else if (browser.toLowerCase().contains("edge")) {
			driver = new EdgeDriver();
		} else if (browser.toLowerCase().contains("firefox")) {
			driver = new FirefoxDriver();
		} else {
			return;
		}

		tLocalDriver.set(driver);

		tLocalDriver.get().manage().deleteAllCookies();
		tLocalDriver.get().manage().window().maximize();
		tLocalDriver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

//		Loading Config.properties file :-
		

		properties = new Properties();
		FileInputStream file = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\test\\resources\\config.properties");

		properties.load(file);
		tLocalProperties.set(properties);
		
		String browserURL = tLocalProperties.get().getProperty("browserURL"); // reading value from properties file
//		String browserURL = "https://tutorialsninja.com/demo/";
		
		tLocalDriver.get().get(browserURL);
	}

	@AfterClass(groups = { "sanity", "regression", "master" })
	public void tearDown() {
//		ThreadSafeDriver.getDriver().quit(); // This will make sure that we close the browser being currently linked to the current thread.
//		ThreadSafeDriver.removeDriver();
		tLocalDriver.get().quit();
		tLocalDriver.remove();
//		tLocalLogger.remove();
		tLocalProperties.remove();
	}

//	The below @BeforeMethod is dedicated to 1. Data Driven Testing 2. Retry Mechanism :- (Due to intermittent issues if the test case fails, again re-run the test from this Home Page
//	@BeforeMethod(groups = { "sanity", "regression", "master" })
//	public void reNavigate() {
//		driver.navigate().to(properties.getProperty("browserURL"));
//	}

	public static String captureScreenshot() throws IOException {

		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

//		1. Create screenshot file Name
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		Date dt = new Date();
		String fileName = df.format(dt);

//		2. Set the target File Path
		String targetFilePath = System.getProperty("user.dir") + "/screenshots/screenshot_" + fileName + ".png";

		FileUtils.copyFile(src, new File(targetFilePath));

		return targetFilePath;
	}

	public static String getRandomString() {

		String result = "";
		for (int i = 0; i < 8; i++) {
			int currASCII = 97 + (int) (Math.random() * (122 - 97 + 1));
			char currChar = (char) currASCII;
			result += currChar;
		}

		return result;
	}

	public static String getRandomNumberString() {

		String result = "";
		for (int i = 0; i < 10; i++) {
			int currASCII = 48 + (int) (Math.random() * (57 - 48 + 1));
			char currChar = (char) currASCII;
			result += currChar;
		}

		return result;
	}

	public static String getRandomAlphanumeric() {
		String result = getRandomString() + getRandomNumberString();
		return result;
	}

}
