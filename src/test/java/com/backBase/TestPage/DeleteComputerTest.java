package com.backBase.TestPage;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.backBase.AppPages.AddComputer;
import com.backBase.AppPages.DeleteComputer;
import com.backBase.AppPages.HomePage;
import com.backBase.AppPages.UpdateComputer;

import automationUtility.CsvReader;
import automationUtility.DriverFactory;
import automationUtility.configReader;

public class DeleteComputerTest {

	WebDriver driver;

	@DataProvider
	public String[][] getData() {
		return CsvReader.getContent(System.getProperty("user.dir")+"\\data\\delete.csv");
	}

	

	@Test(dataProvider = "getData" ,priority=1)
	public void deleteComputer(String name) throws IOException {

		// Search computer to be deleted

		DeleteComputer delete = new DeleteComputer(driver);
		// get the search result
		String expectedTestResult = delete.returnSearchResults(name);
		// if search result is present than click on edit link and edit
		if (!"Nothing to display".equalsIgnoreCase(expectedTestResult)) {
			// go to edit page
			delete.gotoEditComputer(expectedTestResult);
			// update computer details
			delete.deleteComputer();

		}
	}


	@Test(dataProvider = "getData", priority=2)
	public void verifyComputerCount(String term) throws IOException {


		DeleteComputer delete = new DeleteComputer(driver);
		
		
		int intitalCount=delete.getComputerCount();
		String searchedComputerName = delete.returnSearchResults(term);
		// if search result is present than click on edit link and edit
		if (!"Nothing to display".equalsIgnoreCase(searchedComputerName)) {
			// go to edit page
			delete.gotoEditComputer(searchedComputerName);
			
			// update computer details
			delete.deleteComputer();
			System.out.println("Count before delete :"+intitalCount);
			int afterDeleteCount=delete.getComputerCount();
			System.out.println("Count after delete :"+afterDeleteCount);
			// validate results
			Assert.assertEquals(afterDeleteCount, intitalCount-1);
			
			System.out.println("Count after delete successfully tested");

		}
	}



	@BeforeClass
	public void openBrowser() {
		try {
			Properties properties = configReader.readFromProperties();
			String browser =properties.getProperty("browser");
			driver = DriverFactory.newDriver(browser);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@BeforeMethod
	public void setUp() {

		try {
			Properties properties = configReader.readFromProperties();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			String url =  properties.getProperty("url");
			driver.get(url);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@AfterMethod
	public void tearDown(ITestResult result) throws IOException
	{
		
		if(!result.isSuccess()){
			TakesScreenshot ts = (TakesScreenshot)driver;
			File srcFile = ts.getScreenshotAs(OutputType.FILE);
			File path = new File(System.getProperty("user.dir")+File.separator+"screenshots"+File.separator+result.getName()+".png");
			FileUtils.copyFile(srcFile, path);
			System.out.println("ScreenShot captured");
			System.out.println(path);
		}
		
	}
	
	@AfterClass
	public void shutDown() {
		driver.close();
	}
	
}
