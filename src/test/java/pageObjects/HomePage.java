package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage{
	
	public HomePage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(xpath="//a[@title='My Account']") WebElement MyAccount;
	
	By myAccountOptionsLoc = By.xpath("//ul[@class='dropdown-menu dropdown-menu-right']");
	@FindBy(xpath="//li //a[contains(@href,'register')]") WebElement Register;
	
	@FindBy(xpath="//a[normalize-space()='Login']")
	WebElement Login;
	
	public void clickMyAccount() {
		MyAccount.click();
	}
	
	public void clickRegister() {
		waitForElementToAppear(myAccountOptionsLoc);
		Register.click();
	}

	public void clickLogin() {
		waitForElementToAppear(myAccountOptionsLoc);
		Login.click();
	}
}
