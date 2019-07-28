package automationUtility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class DriverFactory {
	
	// This method returns a WebDriver
	// Default is Chrome
	public static WebDriver newDriver() {
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/src/test//resources/drivers/chromedriver.exe");
		return new ChromeDriver();
	}
	
	// This method returns a WebDriver
	// User defines which browser
	// Through overloading, this method will be called when user passed the browser they wish to use
	public static WebDriver newDriver(String browserType) {
		if (browserType == "Firefox") {
			System.out.println("Using Firefox");
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"/src/test//resources/drivers/geckodriver.exe");
			return new FirefoxDriver();
		}
		else {
			System.out.println("Using Chrome Explorer");
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/src/test//resources/drivers/chromedriver.exe");
			return new ChromeDriver();
		}
		
	}

}
