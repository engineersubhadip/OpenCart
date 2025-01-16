package testBase;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger; //Log4j
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseTest {

	public static WebDriver driver;
	public Logger logger;
	public Properties properties;
	public static ThreadLocal<WebDriver> tLocalDriver = new ThreadLocal<>();
	public ThreadLocal<Properties> tLocalProperties = new ThreadLocal<>();
	
	@BeforeClass(groups = { "sanity", "regression", "master" })
	@Parameters({ "browser", "operatingSystem" })
	public void setUp(String browser, String operatingSystem) throws IOException {

		logger = LogManager.getLogger(this.getClass()); /// this.getClass() -> will dynamically capture the current
		// class(test case) we are running.
		
		if (System.getProperty("browser") != null) {
			browser = System.getProperty("browser");
		}
		
		if (browser.toLowerCase().contains("chrome")) {
			ChromeOptions options = new ChromeOptions();
			if (browser.toLowerCase().contains("headless")) {
				options.addArguments("--headless=new");
			}
			driver = new ChromeDriver(options);
		} else if (browser.toLowerCase().contains("edge")) {
			EdgeOptions options = new EdgeOptions();
			if (browser.toLowerCase().contains("headless")) {
				options.addArguments("--headless=new");
			}
			driver = new EdgeDriver(options);
		} else if (browser.toLowerCase().contains("firefox")) {
			FirefoxOptions options = new FirefoxOptions();
			if (browser.toLowerCase().contains("headless")) {
				options.addArguments("--headless=new");
			}
			driver = new FirefoxDriver(options);
		} else {
			return;
		}

		tLocalDriver.set(driver);

		tLocalDriver.get().manage().deleteAllCookies();
		tLocalDriver.get().manage().window().setSize(new Dimension(1440,900)); // full-screen
		tLocalDriver.get().manage().window().maximize(); // maximized (Combo of full-screen and maximized view give the best possible result)
		tLocalDriver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

//		Loading Config.properties file :-
		

		properties = new Properties();
		FileInputStream file = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\test\\resources\\config.properties");

		properties.load(file);
		tLocalProperties.set(properties);
		
		String browserURL = tLocalProperties.get().getProperty("browserURL"); // reading value from properties file
		
		tLocalDriver.get().get(browserURL);
	}

	@AfterClass(groups = { "sanity", "regression", "master" })
	public void tearDown() {
		tLocalDriver.get().quit();
		tLocalDriver.remove();
		tLocalProperties.remove();
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
