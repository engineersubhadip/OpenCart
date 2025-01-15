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
			HomePage homePage = new HomePage(tLocalDriver.get());
			boolean homePageStatus = homePage.checkPageStatus(tLocalProperties.get().getProperty("homePageTitle"));

			if (!homePageStatus) {
				logger.info("Could not load Home Page...");
				Assert.fail();
			}

			logger.info("Inside Home Page");
			homePage.clickMyAccount();
			logger.info("Clicked My Account");
			homePage.clickLogin();
			logger.info("Clicked Login Link");

			LoginPage loginPage = new LoginPage(tLocalDriver.get());
			boolean loginPageStatus = loginPage.checkPageStatus(tLocalProperties.get().getProperty("loginPageTitle"));

			if (!loginPageStatus) {
				logger.info("Could not load Login Page ....");
				Assert.fail();
			}

			logger.info("Inside Login Page");
			logger.info("Entering user credentials");
			loginPage.enterUserEmail(tLocalProperties.get().getProperty("email"));
			loginPage.enterUserPassword(tLocalProperties.get().getProperty("password"));
			loginPage.clickLoginButton();

			logger.info("Clicked on Continue button");

			MyAccountPage myAccount = new MyAccountPage(tLocalDriver.get());
			boolean myAccountStatus = myAccount
					.checkPageStatus(tLocalProperties.get().getProperty("myAccountPageTitle"));

			if (!myAccountStatus) {
				logger.info("Invalid Credentials.");
				Assert.fail();
			}

			logger.info("Successful login !");

			myAccount.clickLogout();

			AccountLogoutPage logoutPage = new AccountLogoutPage(tLocalDriver.get());
			logoutPage.clickContinueButton();

			Assert.assertTrue(true);

		} catch (Exception e) {
			logger.error("Error Message -> " + e.getMessage());
			Assert.fail();
		}

		logger.info("**** Finished TC003_ValidUserLoginTest execution ****");
	}
}
