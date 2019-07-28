package com.backBase.TestPage;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.backBase.AppPages.AddComputer;

import automationUtility.CsvReader;
import automationUtility.DriverFactory;
import automationUtility.configReader;

public class AddComputerTest {

	WebDriver driver;

	
	@DataProvider
	public String[][] getData() {
		return CsvReader.getContent(System.getProperty("user.dir")+"\\data\\CreatComputerData.csv");
	}
	
	@DataProvider
	public String[][] getDataDate() {
		return CsvReader.getContent(System.getProperty("user.dir")+"\\data\\CreatComputerDateValidateData.csv");
	}
	
	

	

	@Test(dataProvider="getData",priority=1)
	public void createComputer(String name,String introDate,String discontinueDate,String company) throws IOException {
		AddComputer add= new AddComputer(driver);
		add.validateHomePage();
		add.gotoAddComputerPage();
		add.addTheComputer(name, introDate,discontinueDate,company);
		add.searchNewlyAddedComputer(name);
	}


	@Test(dataProvider="getDataDate" , priority=2)
	public void validateFields(String name,String introDate,String discontinueDate,String company) throws IOException {
		//Name Validation by passing name as empty string
		AddComputer add = new AddComputer(driver);
		add.gotoAddComputerPage();
		add.validateName("");
		
		//Validate Date
		add.validateDate(name, introDate, discontinueDate, company);

	}
	

	@Test(priority=3)
	public void cancelButton() throws IOException {
		
		AddComputer add = new AddComputer(driver);
		add.gotoAddComputerPage();
		add.cancelButtonClick();

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
