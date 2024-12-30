package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseTest;

public class TC003_ValidUserLoginTest extends BaseTest {
	
	@Test(groups= {"sanity","master"}, retryAnalyzer = utilities.RetryAnalyzer.class)
	public void validate_valid_user_credentials() {
		
		logger.info("**** Starting TC003_ValidUserLoginTest execution ****");
		
		try {
		HomePage homePage = new HomePage(driver);
		homePage.waitForTitleToLoad("Your Store");
		logger.info("Inside Home Page");
		homePage.clickMyAccount();
		logger.info("Clicked My Account");
		homePage.clickLogin();
		logger.info("Clicked Login Link");
		
		LoginPage loginPage = new LoginPage(driver);
		loginPage.waitForTitleToLoad("Account Login");
		logger.info("Inside Login Page");
		logger.info("Entering user credentials");
		
		loginPage.enterUserEmail(properties.getProperty("email"));
		loginPage.enterUserPassword(properties.getProperty("password"));
		loginPage.clickLoginButton();
		
		logger.info("Clicked on Continue button");

//		Validation Part :-
		
		MyAccountPage myAccount = new MyAccountPage(driver);
		myAccount.waitForTitleToLoad("My Account");
		
		logger.info("Starting validation");
		
		boolean confirmMessage = myAccount.getHeaderMessage();
		
		if (confirmMessage) {
			logger.info("Validation successfull");
			Assert.assertTrue(true);
		}else {
			logger.error("Validation failure");
			Assert.assertTrue(false);
		}
		} catch(Exception e) {
			logger.error("Error Message -> "+e.getMessage());
			Assert.fail();
		}
		
		logger.info("**** Finished TC003_ValidUserLoginTest execution ****");
	}
}
