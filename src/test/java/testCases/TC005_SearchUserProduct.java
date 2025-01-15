package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.SearchPage;
import testBase.BaseTest;

public class TC005_SearchUserProduct extends BaseTest {

	@Test(groups = { "sanity" })
	public void validate_SearchUserProduct() {
		logger.info("**** Starting TC005_SearchUserProduct Execution ****");

		try {
			HomePage homePage = new HomePage(tLocalDriver.get());
			boolean homePageStatus = homePage.checkPageStatus(tLocalProperties.get().getProperty("homePageTitle"));

			if (!homePageStatus) {
				logger.info("Could not load Home Page.");
				Assert.fail();
			}

			logger.info("Inside the Home Page");
			homePage.enterProductName(tLocalProperties.get().getProperty("searchProduct"));
			homePage.clickSearchButton();

			SearchPage searchPage = new SearchPage(tLocalDriver.get());
			boolean searchPageStatus = searchPage
					.checkPageStatus("Search - " + properties.getProperty("searchProduct"));
			
			if (!searchPageStatus) {
				logger.info("Could not load Product Search Page");
				Assert.fail();
			}

			logger.info("**** Validation Begins ****");

			boolean status = searchPage.checkProductExistStatus(tLocalProperties.get().getProperty("searchProduct"));

			if (!status) {
				logger.info("Product does not exists.");
				Assert.assertTrue(false);
			}

			searchPage.clickAddToCart(tLocalProperties.get().getProperty("searchProduct"));
			logger.info("Product added to cart.");
			
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
