package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountLogoutPage extends BasePage {
	
	public AccountLogoutPage(WebDriver driver) {
		super(driver);
	}
	
	By logoutConfirmMsgLoc = By.xpath("//h1[normalize-space()='Account Logout']");
	@FindBy(xpath="//h1[normalize-space()='Account Logout']")
	WebElement logoutConfirmMessage;
	
	@FindBy(xpath="//a[normalize-space()='Continue']")
	WebElement continueButton;
	
	public boolean checkLogoutConfirmMessage () {
		try {
			waitForElementToAppear(logoutConfirmMsgLoc);
			return logoutConfirmMessage.isDisplayed();
		}catch (Exception e) {
			return false;
		}
	}
	
	public void clickContinueButton() {
		this.continueButton.click();
	}
}
