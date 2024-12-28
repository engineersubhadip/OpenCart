package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {
	
	public LoginPage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(xpath="//input[@id='input-email']")
	WebElement emailAddress;
	
	@FindBy(xpath="//input[@id='input-password']")
	WebElement password;
	
	@FindBy(xpath="//input[@value='Login']")
	WebElement loginButton;
	
	By confirmationMessageLoginLoc = By.xpath("//h2[normalize-space()='My Account']");
	@FindBy(xpath="//h2[normalize-space()='My Account']")
	WebElement confirmationMessageLogin;
	
	public void enterUserEmail(String email) {
		emailAddress.sendKeys(email);
	}
	
	public void enterUserPassword(String password) {
		this.password.sendKeys(password);
	}
	
	public void clickLoginButton() {
		this.loginButton.click();
	}
	
}
