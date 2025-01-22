package threadSafe;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverManagement {
	
	private static DriverManagement instance;
	private DriverManagement () {};
	private static  Lock lock = new ReentrantLock();
	private ThreadLocal<WebDriver> tLocalDriver = new ThreadLocal<>();
	
	public static DriverManagement getInstance () {
		if (instance == null) {
			lock.lock();
			if (instance == null) {
				instance = new DriverManagement();				
			}
			lock.unlock();
		}
		return instance;
	}
	
	public void setDriver (String browserName) { // this code will be invoked by a thread via the shared object.
		if (tLocalDriver.get() == null) {
			if (browserName.toLowerCase().contains("chrome")) {
				tLocalDriver.set(new ChromeDriver());
			} else if (browserName.toLowerCase().contains("edge")) {
				tLocalDriver.set(new EdgeDriver());
			} else if (browserName.toLowerCase().contains("firefox")) {
				tLocalDriver.set(new FirefoxDriver());
			} else {
				return;
			}
		}
	}
	
	public WebDriver getDriver () { // this code will be invoked by a thread via the shared object
		if (tLocalDriver.get() != null) {
			return tLocalDriver.get();
		}
		return null;
	}
	
	public void quitBrowser () { // this code will be invoked by a thread via a shared object
		if (tLocalDriver.get() != null) {
			tLocalDriver.get().quit();
			tLocalDriver.remove();
		}
	}
	
}
