package testCases;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.AccountLogoutPage;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseTest;
import utilities.DataProviders;

public class TC004_LoginDataDrivenTest extends BaseTest {
	
	@BeforeMethod
	public void navigateBack () {
		driver.navigate().to(properties.getProperty("browserURL"));
	}
	
	@Test(dataProvider="loginDataProvider", dataProviderClass=DataProviders.class)
	public void validate_login (String email, String password, String expResult) {
		
		logger.info("**** Starting TC004_LoginDataDrivenTest execution ****");
		
		try {
		HomePage homePage = new HomePage(driver);
		homePage.waitForTitleToLoad("Your Store");
		logger.info("Inside the Home Page");
		
		homePage.clickMyAccount();
		logger.info("Clicked My Account");
		homePage.clickLogin();
		logger.info("Clicked Login Link from Home Page");
		
		LoginPage loginPage = new LoginPage(driver);
		loginPage.waitForTitleToLoad("Account Login");
		logger.info("Inside the Login Page");
		
		logger.info("Entering user credentials");
		loginPage.enterUserEmail(email);
		loginPage.enterUserPassword(password);
		loginPage.clickLoginButton();
		logger.info("Clicked on Login Button and trying to Login...");
		
		logger.info("Validation begins...");
		
		if (expResult.equalsIgnoreCase("Valid")) { // valid data
			MyAccountPage myAccount = new MyAccountPage(driver);
			boolean status = myAccount.getHeaderMessage();

			if (status) { // valid data -> successful login -> Test pass
				logger.info("This combination is valid and we are successfully logged in. Test case Pass "+email);
				myAccount.clickLogout();
				AccountLogoutPage logout = new AccountLogoutPage(driver);
				logout.waitForTitleToLoad("Account Logout");
				logger.info("Inside Account Logout page");
				logout.clickContinueButton();
				Assert.assertTrue(true);
			} else { // valid data -> unsuccessful login -> Test fail
				logger.info("This combination is valid but we are not successfully logged in. Test case Failed "+email);
				Assert.assertTrue(false);
			}
		} else { // Invalid data
			MyAccountPage myAccount = new MyAccountPage(driver);
			boolean status = myAccount.getHeaderMessage();
			
			if (status == false) { // invalid data -> unsuccessful login -> Test Pass
				logger.info("This combination is invalid and we are not logged in. Test case Pass "+email);
				Assert.assertTrue(true);
			} else { // invalid data -> successful login -> Test fail
				logger.info("This combination is invalid but we are logged in. Test case failed "+email);
				myAccount.clickLogout();
				AccountLogoutPage logout = new AccountLogoutPage(driver);
				logout.waitForTitleToLoad("Account Logout");
				logger.info("Inside Account Logout page");
				logout.clickContinueButton();
				Assert.assertTrue(false);
			}
		}
		logger.info("Validation ends...");
		} catch (Exception e) {
			logger.error("Test case failed -> "+e.getMessage());
			Assert.fail();
		}
		
		logger.info("**** Finished TC004_LoginDataDrivenTest execution ****");
	}
}
