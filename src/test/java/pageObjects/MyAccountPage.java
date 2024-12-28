package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyAccountPage extends BasePage {
	
	public MyAccountPage(WebDriver driver) {
		super(driver);
	}
	
	
	@FindBy(xpath="//a[@class='list-group-item'][normalize-space()='Logout']")
	WebElement logoutLink;
	
	By myAccountHeaderMsgLoc = By.xpath("//h2[normalize-space()='My Account']");
	
	@FindBy(xpath="//h2[normalize-space()='My Account']")
	WebElement headerMessage;
	
	
	public boolean getHeaderMessage() {
		try {
			waitForTitleToLoad("My Account");
			waitForElementToAppear(myAccountHeaderMsgLoc);
			return headerMessage.isDisplayed();
		} catch(Exception e) {
			return false;
		}
	}
	
	
	public void clickLogout() {
		logoutLink.click();
	}
}
