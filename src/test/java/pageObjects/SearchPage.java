package pageObjects;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SearchPage extends BasePage {
	
	public SearchPage (WebDriver driver) {
		super(driver);
	}
	
	@FindBy(xpath = "//div[@id='content']")
	WebElement productList;
	
	public boolean checkProductExistStatus () {
//		2 Parts of check :-
//		1. Empty product list
//		2. All displayed products will be iPhone.
		
		try {
			List<WebElement> products = productList.findElements(By.xpath("./div[3]//img"));
			
			int productCount = products.stream().filter(currEle -> {
				String productTitle = currEle.getAttribute("title");
				if (productTitle.toLowerCase().contains("iphone")) {
					return true;
				} else {
					return false;
				}
			}).collect(Collectors.toList()).size();
			
			if (productCount == products.size()) {
				return true;
			} else {
				return false;
			}
		}catch (Exception e) {
			return false;
		}
	}
}
