package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseTest;

public class TC002_AccountRegistrationDiffPasswordTest extends BaseTest {

	@Test(groups= {"regression", "master"})
	public void validate_incorrect_password_message() throws InterruptedException {
		
		logger.info("**** Starting TC002_AccountRegistrationDiffPasswordTest execution ****");
		
		try {
		HomePage homePage = new HomePage(tLocalDriver.get());
		boolean homePageStatus = homePage.checkPageStatus(tLocalProperties.get().getProperty("homePageTitle"));
		
		if (!homePageStatus) {
			logger.info("Could not load Home Page...");
			Assert.fail();
		}
		
		logger.info("Inside Home Page.");
		homePage.clickMyAccount();
		logger.info("Clicked on My Account");
		homePage.clickRegister();
		logger.info("Clicked on Register Link");

		AccountRegistrationPage regPage = new AccountRegistrationPage(tLocalDriver.get());
		boolean registrationPageStatus = regPage.checkPageStatus(tLocalProperties.get().getProperty("accountRegisterPageTitle"));
		
		if (!registrationPageStatus) {
			logger.info("Could not load Register Account Page");
			Assert.fail();
		}
		
		logger.info("Inside Register Account Page");
		logger.info("Entering customer details....");
		
		String firstName = getRandomString();
		regPage.enterFirstName(firstName);

		String lastName = getRandomString();
		regPage.enterLastName(lastName);

		String email = getRandomString();
		regPage.enterEmail(email+"@gmail.com");

		String telephoneNumber = getRandomNumberString();
		regPage.enterTelephoneNumber(telephoneNumber);

		String userPassword = getRandomString();
		regPage.enterUserPassword(userPassword);

		String confirmPassword = getRandomString();
		regPage.enterConfirmPassword(confirmPassword);
		
		regPage.clickPrivacyAgree();
		
		regPage.clickContinue();
		
		logger.info("Entered all the details");
		
		logger.info("Starting password mismatch message verfication....");
		
		String passwordMismatchMessage = regPage.validateIncorrectPasswordMessage();
		
		if (passwordMismatchMessage.equals("Password confirmation does not match password!")) {
			logger.info("Message verfication successfull");
			Assert.assertTrue(true);
		}else {
			logger.info("Message verfication failed");
			Assert.assertTrue(false);
		}

		} catch (Exception e) {
			logger.error("Test case failed -> "+e.getMessage());
			Assert.fail();
		}
		
		logger.info("**** Finished TC002_AccountRegistrationDiffPasswordTest execution ****");
	}
}
