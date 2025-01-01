package pageObjects;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SearchPage extends BasePage {

	public SearchPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//div[@id='content']//div[3]//img")
	List<WebElement> productList;

	@FindBy(xpath = "//p[contains(text(),'There is no product that matches the search criter')]")
	WebElement noProductDisplayed;

	@FindBy(xpath = "//span[normalize-space()='Add to Cart']")
	WebElement addToCart;

	By confirmMessageLoc = By.xpath("//div[@class='alert alert-success alert-dismissible']");
	@FindBy(xpath = "//div[@class='alert alert-success alert-dismissible']")
	WebElement confirmMessage;

	public boolean checkProductExistStatus() {
//		2 Parts of check :-
//		1. Empty product list
//		2. All displayed products will be iPhone.

		int invalidProductCount = productList.stream().filter(currEle -> {
			String productTitle = currEle.getAttribute("title");
			if (!productTitle.toLowerCase().contains("iphone")) {
				return true;
			} else {
				return false;
			}
		}).collect(Collectors.toList()).size();

		if ((productList.size() == 0) || (invalidProductCount > 0)) {
			return false;
		} else {
			return true;
		}
	}

	public void clickAddToCart() {
		if (!productList.isEmpty()) {
			
			productList.stream().filter(currEle -> {
				String currProductName = currEle.getAttribute("title").toLowerCase();
				if (currProductName.equalsIgnoreCase("iphone")) {
					return true;
				} else {
					return false;
				}
			}).limit(1).toList().get(0).click();
			
		}
	}

	public boolean checkConfirmMessage() {
		try {
			waitForElementToAppear(confirmMessageLoc);
			return confirmMessage.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}
}
