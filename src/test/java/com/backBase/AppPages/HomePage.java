package com.backBase.AppPages;

import javax.naming.directory.SearchResult;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class HomePage {

	WebDriver driver;

	private WebElement addComputerElement;

	public HomePage(WebDriver driver) {
		this.driver = driver;
	}

	public String getHomepageConfirmation() {
		return driver.findElement(By.linkText("Add a new computer")).getText();
	}

	public String gotoAddComputer() {
		driver.findElement(By.linkText("Add a new computer")).click();
		String buttonText = driver.findElement(By.xpath("//*[@id=\"main\"]/h1")).getText();

		System.out.println("buttonText : " + buttonText);
		return buttonText;
	}

	public String computerAddConfirmation() {
		return driver.findElement(By.xpath("//*[@id=\"main\"]/div[1]")).getText();
	}

	public String returnSearchResults(String term) {

		WebElement searchElement = driver.findElement(By.id("searchbox"));
		WebElement searhResult = null;
		searchElement.clear();

		searchElement.sendKeys(term);
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

		return searchresultText;
	}

	public void nextButtonClick() {
		String paginationElement = driver.findElement(By.xpath("//*[@id=\"pagination\"]/ul/li[2]/a")).getText();

		String[] tokens = paginationElement.split(" ");
		int resultCountStart = Integer.parseInt(tokens[1]);
		int totalResult = Integer.parseInt(tokens[5]);

		if (totalResult > 10) {
			WebElement nextButtonElement = driver.findElement(By.xpath("//*[@id=\"pagination\"]/ul/li[3]/a"));
			nextButtonElement.click();
			paginationElement = driver.findElement(By.xpath("//*[@id=\"pagination\"]/ul/li[2]/a")).getText();
			String[] tokensAfterNextClick = paginationElement.split(" ");
			int resultCountStartAfterNextClick = Integer.parseInt(tokensAfterNextClick[1]);
			Assert.assertEquals(resultCountStart + 10, resultCountStartAfterNextClick);
			
			System.out.println("Next Button tested successfully");
		}

	}

}
