package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseTest;

public class TC001_AccountRegistrationTest extends BaseTest {

	@Test(groups = { "regression", "master" })
	public void verfiy_account_registration() throws InterruptedException { // This test-case is referring to 2 page
																			// object classes.

		logger.info("**** Started TC001_AccountRegistrationTest execution ****");

		try {

			HomePage homePage = new HomePage(tLocalDriver.get());
			boolean homePageStatus = homePage.checkPageStatus(tLocalProperties.get().getProperty("homePageTitle"));
			
			if (!homePageStatus) {
				logger.info("Could not load Home Page...");
				Assert.fail();
			}
			
			logger.info("Inside the Home Page...");

			homePage.clickMyAccount();
			logger.info("Clicked on My Account");
			homePage.clickRegister(); 
			logger.info("Clicked on Register Link");

			AccountRegistrationPage regPage = new AccountRegistrationPage(tLocalDriver.get());
			boolean registrationPageStatus = regPage.checkPageStatus(tLocalProperties.get().getProperty("accountRegisterPageTitle"));
			
			if (!registrationPageStatus) {
				logger.info("Could not load Register Account Page...");
				Assert.fail();
			}
			
			logger.info("Inside Register Account Page...");
			logger.info("Entering all the Customer details...");

			String firstName = getRandomString();
			regPage.enterFirstName(firstName);

			String lastName = getRandomString();
			regPage.enterLastName(lastName);

			String userEmail = getRandomString();
			regPage.enterEmail(userEmail + "@gmail.com");

			String userTelephoneNumber = getRandomNumberString();
			regPage.enterTelephoneNumber(userTelephoneNumber);

			String userPassword = getRandomAlphanumeric();
			regPage.enterUserPassword(userPassword);
			regPage.enterConfirmPassword(userPassword);

			regPage.clickSubscriptionAgree();
			regPage.clickPrivacyAgree();

			logger.info("Successfully entered all the details of the Customer...");

			regPage.clickContinue();
			logger.info("Clicked on Continue button");

			logger.info("About to start validation message");

			String confirmMessage = regPage.validateConfirmationMessage();
			
			if (confirmMessage.equals("Your Account Has Been Created!")) {
				logger.info("Validation Passed");
				Assert.assertTrue(true);
			} else {
				logger.info("Validation Failed");
				Assert.assertTrue(false);
			}

		} catch (Exception e) {
			logger.info("Test case execution failed.");
			logger.error("Reason :- "+e.getMessage());
			Assert.fail();
		}

		logger.info("**** Finished TC001_AccountRegistrationTest execution ****");
	}
}
