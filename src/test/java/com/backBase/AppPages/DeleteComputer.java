package com.backBase.AppPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class DeleteComputer {

	private WebDriver driver;
	

	public DeleteComputer(WebDriver driver) {
		
		this.driver=driver;
		
	}

	public void deleteComputer() {
		
		WebElement deleteButtonElement=driver.findElement(By.cssSelector("input[type='submit'][value='Delete this computer']"));
		deleteButtonElement.click();
		String deleteConfirmText= driver.findElement(By.xpath("//*[@id=\"main\"]/div[1]")).getText();
		Assert.assertTrue(deleteConfirmText.contains(" has been deleted"));
		System.out.println("Delete computer successful");
	}
	
	public void gotoEditComputer(String name) {

		WebElement computerNameToBeEdited = driver.findElement(By.partialLinkText(name));
		computerNameToBeEdited.click();
		
	
	}

	public  int getComputerCount() {
		String totalResultElement=driver.findElement(By.xpath("//*[@id=\"main\"]/h1")).getText();
		String[] tokens= totalResultElement.split(" ");
		if(tokens[0].equalsIgnoreCase("one")) {
			tokens[0]="1";
		}
		if(tokens[0].equalsIgnoreCase("no")) {
			tokens[0]="0";
		}
		return Integer.parseInt(tokens[0]);
	}
	
	public String returnSearchResults(String term) {

		WebElement searchElement = driver.findElement(By.id("searchbox"));
		WebElement searhResult = null;
		searchElement.clear();

		searchElement.sendKeys(term);
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

			}else {
				searchresultText = driver.findElement(By.className("well")).getText();
			}

		
		return searchresultText;
	}


}
