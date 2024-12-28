package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {
	
	@DataProvider(name="loginDataProvider")
	public String[][] getData() throws IOException {
		String filePath = "K:\\Selenium Java Automation\\OpenCart\\testData\\OpenCart LoginData.xlsx";
		String sheetName = "Sheet1";
		
		ExcelUtils excel = new ExcelUtils(filePath);
		
		int rowCount = excel.getRowCount(sheetName);
		int colCount = excel.getCellCount(sheetName, 0);
		
		String[][] data = new String[rowCount][colCount];
		
		for (int r=1; r<=rowCount; r++) {
			for (int c=0; c<colCount; c++) {
				data[r-1][c] = excel.getCellData(sheetName, r, c);
			}
		}
		
		return data;
	}
}
