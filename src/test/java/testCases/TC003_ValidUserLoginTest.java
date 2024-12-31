package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountLogoutPage;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseTest;

public class TC003_ValidUserLoginTest extends BaseTest {

	@Test(groups = { "sanity", "master" })
	public void validate_valid_user_credentials() {

		logger.info("**** Starting TC003_ValidUserLoginTest execution ****");

		try {
			HomePage homePage = new HomePage(driver);
			boolean homePageStatus = homePage.checkPageStatus(properties.getProperty("homePageTitle"));

			if (!homePageStatus) {
				logger.info("Could not load Home Page...");
				Assert.fail();
			}

			logger.info("Inside Home Page");
			homePage.clickMyAccount();
			logger.info("Clicked My Account");
			homePage.clickLogin();
			logger.info("Clicked Login Link");

			LoginPage loginPage = new LoginPage(driver);
			boolean loginPageStatus = loginPage.checkPageStatus(properties.getProperty("loginPageTitle"));

			if (!loginPageStatus) {
				logger.info("Could not load Login Page ....");
				Assert.fail();
			}

			logger.info("Inside Login Page");
			logger.info("Entering user credentials");
			loginPage.enterUserEmail(properties.getProperty("email"));
			loginPage.enterUserPassword(properties.getProperty("password"));
			loginPage.clickLoginButton();

			logger.info("Clicked on Continue button");

			MyAccountPage myAccount = new MyAccountPage(driver);
			boolean myAccountStatus = myAccount.checkPageStatus(properties.getProperty("myAccountPageTitle"));
			
			if (!myAccountStatus) {
				logger.info("Invalid Credentials.");
				Assert.fail();
			}
			
			logger.info("Successful login !");

			myAccount.clickLogout();
			
			AccountLogoutPage logoutPage = new AccountLogoutPage(driver);
			logoutPage.clickContinueButton();
			
			Assert.assertTrue(true);
			
		} catch (Exception e) {
			logger.error("Error Message -> " + e.getMessage());
			Assert.fail();
		}

		logger.info("**** Finished TC003_ValidUserLoginTest execution ****");
	}
}
