package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountRegistrationPage extends BasePage{
	
	public AccountRegistrationPage (WebDriver driver) {
		super(driver);
	}
	
	@FindBy(xpath="//input[@id='input-firstname']") WebElement txtFirstName;	
	@FindBy(xpath="//input[@id='input-lastname']") WebElement txtLastName;
	@FindBy(xpath="//input[@id='input-email']") WebElement txtEmail;
	@FindBy(xpath="//input[@id='input-telephone']") WebElement txtCellNum;
	@FindBy(xpath="//input[@id='input-password']") WebElement txtPassword;
	@FindBy(xpath="//input[@id='input-confirm']") WebElement txtConfirmPassword;
	@FindBy(xpath="//label[normalize-space()='Yes']") WebElement radSubscribeYes;
	@FindBy(xpath="//input[@value='0']") WebElement radSubscribeNo;
	@FindBy(xpath="//input[@name='agree']") WebElement chkPrivacyAgree;
	@FindBy(xpath="//input[@value='Continue']") WebElement btnContinueButton;
	
	By msgConfirmationLoc = By.xpath("//h1[normalize-space()='Your Account Has Been Created!']");
	@FindBy(xpath="//h1[normalize-space()='Your Account Has Been Created!']") WebElement msgConfirmation;
	
	By passwordMismatchLoc = By.xpath("//div[contains(text(),'Password confirmation does not match password!')]");
	@FindBy(xpath="//div[contains(text(),'Password confirmation does not match password!')]")
	WebElement passwordMismatch;
	
	public void enterFirstName(String firstName) throws InterruptedException {
		Thread.sleep(200);
		this.txtFirstName.sendKeys(firstName);
	}
	
	public void enterLastName(String lastName) throws InterruptedException {
		Thread.sleep(200);
		this.txtLastName.sendKeys(lastName);
	}
	
	public void enterEmail(String userEmail) throws InterruptedException {
		Thread.sleep(200);
		this.txtEmail.sendKeys(userEmail);
	}
	
	public void enterTelephoneNumber(String userNumber) throws InterruptedException {
		Thread.sleep(200);
		this.txtCellNum.sendKeys(userNumber);
	}
	
	public void enterUserPassword(String userPassword) throws InterruptedException {
		Thread.sleep(200);
		this.txtPassword.sendKeys(userPassword);
	}
	
	public void enterConfirmPassword(String userPassword) throws InterruptedException {
		Thread.sleep(200);
		this.txtConfirmPassword.sendKeys(userPassword);
	}
	
	public void clickSubscriptionAgree() {
		this.radSubscribeYes.click();
	}
	
	public void clickSubscriptionNo() {
		this.radSubscribeNo.click();
	}
	
	public void clickPrivacyAgree() {
		this.chkPrivacyAgree.click();
	}
	
	public void clickContinue() {
		this.btnContinueButton.click();
	}
	
	public String validateIncorrectPasswordMessage() {
		try {
			waitForElementToAppear(passwordMismatchLoc);
			Thread.sleep(3000);
			return passwordMismatch.getText();
		}catch(Exception e) {
			return e.getMessage();
		}
	}
	
	public String validateConfirmationMessage() {
		try {
			waitForElementToAppear(msgConfirmationLoc);
			Thread.sleep(3000);
			return msgConfirmation.getText();			
		}catch(Exception e) {
			return e.getMessage();
		}
	}

}
