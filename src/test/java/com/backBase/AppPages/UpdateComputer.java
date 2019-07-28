package com.backBase.AppPages;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class UpdateComputer {
	
	
	private WebDriver driver;


	public UpdateComputer(WebDriver driver) {
		this.driver = driver;

	}


	
	

	public void updateComputerNameAndDate(String updatedName,String date) throws IOException {
		
		WebElement nameElement = driver.findElement(By.id("name"));
		//If name to be updated is not empty then, clear the existing name 
		if (StringUtils.isNotEmpty(updatedName)) {
			
				nameElement.clear();
				nameElement.sendKeys(updatedName);
		}
		WebElement introduceDateElement = driver.findElement(By.id("introduced"));
		//If date to be updated is not empty then, clear the existing date 
		if (StringUtils.isNotEmpty(date)) {
			
			introduceDateElement.clear();
			introduceDateElement.sendKeys(date);
		}
		
		
		WebElement saveComputerButtonElement = driver.findElement(By.cssSelector("input[type='submit'][value='Save this computer']"));
		
		saveComputerButtonElement.click();
		
		WebElement editComputerConfirmationPageElement=driver.findElement(By.xpath("//*[@id=\"main\"]/div[1]"));
		String editComputerConfirmationPageHeading=editComputerConfirmationPageElement.getText();
		
		Assert.assertTrue(editComputerConfirmationPageHeading.contains(updatedName + " has been updated"));
		
		System.out.println("Computer Name and date updated successfully");

	}

	
	public void validateIntroDate(String date) throws IOException {

		WebElement introduceDateElement = driver.findElement(By.id("introduced"));
		//If date to be updated is not empty then, clear the existing date 
		if (StringUtils.isNotEmpty(date)) {
			
			introduceDateElement.clear();
			introduceDateElement.sendKeys(date);
		}
		WebElement saveComputerButtonElement = driver.findElement(By.cssSelector("input[type='submit'][value='Save this computer']"));
		saveComputerButtonElement.click();

		WebElement dateErrorFieldElement = driver.findElement(By.xpath("//*[@id=\"main\"]/form[1]/fieldset/div[2]/div/span"));
		String dateError= dateErrorFieldElement.getText();
		Assert.assertEquals("Date ('yyyy-MM-dd')", dateError);
		
		System.out.println("Date Validation Successfully Tested");

	}

	public String computerAddConfirmation() {
		return driver.findElement(By.xpath("//*[@id=\"main\"]/div[1]")).getText();
	}
	
	public String searchComputer(String name) {

		WebElement searchElement = driver.findElement(By.id("searchbox"));
		WebElement searhResult = null;
		searchElement.clear();

		searchElement.sendKeys(name);
		String searchresultText = "";
		WebElement searchButtomElement = driver.findElement(By.id("searchsubmit"));
		searchButtomElement.click();
		String totalResultElement = driver.findElement(By.xpath("//*[@id=\"main\"]/h1")).getText();

		String[] tokens = totalResultElement.split(" ");
		if (tokens[0].equalsIgnoreCase("one")) {
			tokens[0] = "1";
		}
		if (tokens[0].equalsIgnoreCase("no")) {
			tokens[0] = "0";
		}
		int totalResult = Integer.parseInt(tokens[0]);
		if (totalResult > 1) {

			searhResult = driver.findElement(By.xpath("//*[@id=\"main\"]/table/tbody/tr[1]/td[1]/a"));
			searchresultText = searhResult.getText();

		} else if (totalResult == 1) {
			searhResult = driver.findElement(By.xpath("//*[@id=\"main\"]/table/tbody/tr/td[1]/a"));
			searchresultText = searhResult.getText();

		} else {
			searchresultText = driver.findElement(By.className("well")).getText();
		}

		Assert.assertTrue(searchresultText.toLowerCase().contains(name));

		System.out.println("Computer found");
		return searchresultText;
	}

	public void gotoEditComputer(String name) {

		WebElement computerNameToBeEdited = driver.findElement(By.partialLinkText(name));
		computerNameToBeEdited.click();
		WebElement editPageHeadingElement = driver.findElement(By.xpath("//*[@id=\"main\"]/h1"));
		String editPageHeading = editPageHeadingElement.getText();

		Assert.assertEquals(editPageHeading, "Edit computer");

		System.out.println("Landed on Edit Computer Page");

	}

}
