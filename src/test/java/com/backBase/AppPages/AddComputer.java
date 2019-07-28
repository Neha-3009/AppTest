package com.backBase.AppPages;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class AddComputer {
	
	private  WebDriver driver;
	



	public AddComputer(WebDriver driver) {
		this.driver=driver;
		
	}
	
	private void setCompany(String company) {
		if(null!=company &&!company.trim().isEmpty()){
		new Select(driver.findElement(By.id("company"))).selectByVisibleText(company);
		}
	}
	
	
	public void addTheComputer(String name,String introDate,String disconDate,String company) throws IOException {
		WebElement nameElement=driver.findElement(By.id("name"));
		nameElement.sendKeys(name);
		WebElement introducedDateElement=driver.findElement(By.id("introduced"));
		introducedDateElement.sendKeys(introDate);
		WebElement discontinuedDateElement=driver.findElement(By.id("discontinued"));
		discontinuedDateElement.sendKeys(introDate);
		setCompany(company.trim());
		WebElement createComputerButtonElement=driver.findElement(By.cssSelector("input[type='submit'][value='Create this computer']"));
		createComputerButtonElement.click();
		String computerAddConfirmationText=computerAddConfirmation();
		Assert.assertTrue(computerAddConfirmationText.contains("Computer "+name+" has been created"));
		System.out.println("Computer added Successfully");
		
		
		
	}
	public void validateName(String name) throws IOException {
		WebElement nameElement=driver.findElement(By.id("name"));
		nameElement.sendKeys(name);
		WebElement createComputerButtonElement=driver.findElement(By.cssSelector("input[type='submit'][value='Create this computer']"));
		createComputerButtonElement.click();
		String validationErrorOnName =driver.findElement(By.className("help-inline")).getText();
		Assert.assertEquals("Required", validationErrorOnName);
		System.out.println("Name Validation successfully Tested");
		
	}
	
	
	public void validateDate(String name,String introDate,String disconDate,String company) throws IOException {
		WebElement nameElement=driver.findElement(By.id("name"));
		nameElement.sendKeys(name);
		WebElement introducedDateElement=driver.findElement(By.id("introduced"));
		introducedDateElement.sendKeys(introDate);
		WebElement discontinuedDateElement=driver.findElement(By.id("discontinued"));
		discontinuedDateElement.sendKeys(introDate);
		setCompany(company.trim());
		WebElement createComputerButtonElement=driver.findElement(By.cssSelector("input[type='submit'][value='Create this computer']"));
		createComputerButtonElement.click();
		String validationErrortextOnDates = driver.findElement(By.xpath("//*[@id=\"main\"]/form/fieldset/div[2]/div/span")).getText();
		Assert.assertEquals("Date ('yyyy-MM-dd')", validationErrortextOnDates);
		System.out.println("Date validation successfully tested");
		
	}
	
	public void validateHomePage() {
		String addNewComputerButton=driver.findElement(By.linkText("Add a new computer")).getText();
		Assert.assertEquals("Add a new computer", addNewComputerButton);
		System.out.println("Home Page open success");
	}

	public void gotoAddComputerPage() {
		WebElement addComputerButtonElement=driver.findElement(By.linkText("Add a new computer"));
		addComputerButtonElement.click();
		String headerTextAddCompPage = driver.findElement(By.xpath("//*[@id=\"main\"]/h1")).getText();
		Assert.assertEquals("Add a computer", headerTextAddCompPage);
		System.out.println("Add Computer Page Successfully Opened");
	}
	
	
	public String computerAddConfirmation() {
		return driver.findElement(By.xpath("//*[@id=\"main\"]/div[1]")).getText();
	}
	
	public void cancelButtonClick() {
		
		WebElement cancelButtonElement=driver.findElement(By.linkText("Cancel"));
		cancelButtonElement.click();
		String homePageText = driver.findElement(By.xpath("//*[@id=\"main\"]/h1")).getText();

		Assert.assertTrue(homePageText.contains("computers found"));
		
		System.out.println("Cancel button Tested Successfully");
	}
	
	public void searchNewlyAddedComputer(String name) {

		WebElement searchElement = driver.findElement(By.id("searchbox"));
		WebElement searhResult = null;
		searchElement.clear();

		searchElement.sendKeys(name);
		String searchresultText = "";
		WebElement searchButtomElement = driver.findElement(By.id("searchsubmit"));
		searchButtomElement.click();
			String totalResultElement=driver.findElement(By.xpath("//*[@id=\"main\"]/h1")).getText();
			
			String[] tokens= totalResultElement.split(" ");
			if(tokens[0].equalsIgnoreCase("one")) {
				tokens[0]="1";
			}
			if(tokens[0].equalsIgnoreCase("no")) {
				tokens[0]="0";
			}
			int totalResult=Integer.parseInt(tokens[0]);
			if(totalResult>1) {

				searhResult = driver.findElement(By.xpath("//*[@id=\"main\"]/table/tbody/tr[1]/td[1]/a"));
				searchresultText=searhResult.getText();

			}else if(totalResult==1) {
				searhResult = driver.findElement(By.xpath("//*[@id=\"main\"]/table/tbody/tr/td[1]/a"));
				searchresultText=searhResult.getText();

			}

		
			Assert.assertEquals(name, searchresultText);
			
			System.out.println("Newly added computer successfully found");
	}
	
}
