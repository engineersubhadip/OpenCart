package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.SearchPage;
import testBase.BaseTest;

public class TC005_SearchUserProduct extends BaseTest {

	@Test(groups= {"sanity"})
	public void validate_SearchUserProduct() {
		logger.info("**** Starting TC005_SearchUserProduct Execution ****");

		try {
			HomePage homePage = new HomePage(driver);
			homePage.waitForTitleToLoad(properties.getProperty("homePageTitle"));
			logger.info("Inside the Home Page");
			homePage.enterProductName(properties.getProperty("searchProduct"));
			homePage.clickSearchButton();

			SearchPage searchPage = new SearchPage(driver);
			searchPage.waitForTitleToLoad("Search - " + properties.getProperty("searchProduct"));

			logger.info("**** Validation Begins ****");

			boolean status = searchPage.checkProductExistStatus();

			if (!status) {
				logger.info("Product does not exists.");
				Assert.assertTrue(false);
			}

			searchPage.clickAddToCart();

			boolean confirmationStatus = searchPage.checkConfirmMessage();

			if (!confirmationStatus) {
				logger.info("Confirmation message did not appear");
				Assert.assertTrue(false);
			}

			logger.info("Validation successfull !");
			Assert.assertTrue(true);

		} catch (Exception e) {
			logger.error("Test case execution failed " + e.getMessage());
			Assert.fail();
		}

		logger.info("**** Finished TC005_SearchUserProduct Execution ****");
	}
}
