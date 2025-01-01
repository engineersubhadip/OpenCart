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

//	@BeforeMethod
//	public void navigateBack() {
//		System.out.println("Inside Before Method...");
//		driver.navigate().to(properties.getProperty("browserURL"));
//	}

	@Test(dataProvider = "loginDataProvider", dataProviderClass = DataProviders.class)
	public void validate_login(String email, String password, String expResult) {

		logger.info("**** Starting TC004_LoginDataDrivenTest execution ****");

		try {
			HomePage homePage = new HomePage(driver);
			boolean homePageStatus = homePage.checkPageStatus(properties.getProperty("homePageTitle"));

			if (!homePageStatus) {
				logger.info("Could not home Page.");
				Assert.fail();
			}

			logger.info("Inside the Home Page");

			homePage.clickMyAccount();
			logger.info("Clicked My Account");
			homePage.clickLogin();
			logger.info("Clicked Login Link from Home Page");

			LoginPage loginPage = new LoginPage(driver);
			boolean loginPageStatus = loginPage.checkPageStatus(properties.getProperty("loginPageTitle"));

			if (!loginPageStatus) {
				logger.info("Could not load Login Page.");
				Assert.fail();
			}

			logger.info("Inside the Login Page");
			logger.info("Entering user credentials");
			loginPage.enterUserEmail(email);
			loginPage.enterUserPassword(password);
			loginPage.clickLoginButton();
			logger.info("Clicked on Login Button and trying to Login...");
			logger.info("Validation begins...");

			if (expResult.equalsIgnoreCase("Valid")) { // valid data
				MyAccountPage myAccount = new MyAccountPage(driver);
				boolean myAccountPageStatus = myAccount.checkPageStatus(properties.getProperty("myAccountPageTitle"));

				if (myAccountPageStatus) { // valid data -> successful login -> Test pass
					logger.info("This combination is valid and we are successfully logged in. Test case Pass " + email);
					myAccount.clickLogout();
					AccountLogoutPage logout = new AccountLogoutPage(driver);
					logger.info("Inside Account Logout page");
					logout.clickContinueButton();
					Assert.assertTrue(true);
				} else { // valid data -> unsuccessful login -> Test fail
					logger.info("This combination is valid but we are not successfully logged in. Test case Failed "
							+ email);
					Assert.assertTrue(false);
				}
			} else { // Invalid data
				MyAccountPage myAccount = new MyAccountPage(driver);
				boolean myAccountPageStatus = myAccount.checkPageStatus(properties.getProperty("myAccountPageTitle"));

				if (! myAccountPageStatus) { // invalid data -> unsuccessful login -> Test Pass
					logger.info("This combination is invalid and we are not logged in. Test case Pass " + email);
					Assert.assertTrue(true);
				} else { // invalid data -> successful login -> Test fail
					logger.info("This combination is invalid but we are logged in. Test case failed " + email);
					myAccount.clickLogout();
					AccountLogoutPage logout = new AccountLogoutPage(driver);
					logger.info("Inside Account Logout page");
					logout.clickContinueButton();
					Assert.assertTrue(false);
				}
			}
			logger.info("Validation ends...");
		} catch (Exception e) {
			logger.error("Test case failed -> " + e.getMessage());
			Assert.fail();
		}

		logger.info("**** Finished TC004_LoginDataDrivenTest execution ****");
	}
}
