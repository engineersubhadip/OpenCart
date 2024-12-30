package utilities;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

	int counter = 0;
	int maxRetryLimit = 3;

	@Override
	public boolean retry(ITestResult result) {

		if (counter < maxRetryLimit) {
			counter++;
			return true;
		}
		return false;
	}

}
