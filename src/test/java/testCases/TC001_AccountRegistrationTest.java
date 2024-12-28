package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseTest;

public class TC001_AccountRegistrationTest extends BaseTest {

	@Test(groups= {"regression","master"})
	public void verfiy_account_registration() throws InterruptedException { // This test-case is referring to 2 page
																			// object classes.

		logger.info("**** Started TC001_AccountRegistrationTest execution ****");

		try {

			HomePage homePage = new HomePage(driver);
			homePage.waitForTitleToLoad("Your Store");

			logger.info("Inside the Home Page...");

			homePage.clickMyAccount();
			logger.info("Clicked on My Account");
			homePage.clickRegister(); // till here we are clicking on the Register link
			logger.info("Clicked on Register Link");

//		Inside Account Registration Page :

			AccountRegistrationPage regPage = new AccountRegistrationPage(driver);

			regPage.waitForTitleToLoad("Register Account");

			logger.info("Inside Registration Page");
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

//		Validation Part :-

			logger.info("About to start validation message");
			
			String confirmMessage = regPage.validateConfirmationMessage();
			
			if (confirmMessage.equals("Your Account Has Been Created!")) {
				logger.info("Validation Passed");
				Assert.assertTrue(true);
			}else {
				logger.info("validation Failed");
				logger.error("Error Logs");
				logger.debug("Debug Logs");
				Assert.assertTrue(false);
			}

		} catch (Exception e) {
			Assert.fail();
		}

		logger.info("**** Finished TC001_AccountRegistrationTest execution ****");
	}
}
