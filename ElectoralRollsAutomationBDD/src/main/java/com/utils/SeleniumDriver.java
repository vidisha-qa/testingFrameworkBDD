package com.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.cucumber.java.ParameterType;
import io.github.bonigarcia.wdm.WebDriverManager;

public class SeleniumDriver {

	public static SeleniumDriver seleniumDriver;
	public static WebDriver driver;
	public static WebDriverWait webDriverWait;
	public static JavascriptExecutor js;
	public final static int timeOut = 100;
	public final static int pageLoadTimeOut = 250;
	public static Properties emailProperties;

	private SeleniumDriver() {

		emailProperties = new Properties();

		try {
			emailProperties.load(new FileInputStream(
					System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\ElectoralRollsData.properties"));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@ParameterType("browserName")
   	public static WebDriver openURL(String browserName) throws InterruptedException {

		WebDriverManager.chromedriver().setup();
		driver=new ChromeDriver();
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(pageLoadTimeOut, TimeUnit.SECONDS);
		driver.get(emailProperties.getProperty("ProdURL"));
		return driver;

 }

	public static WebDriver getDriver() {
		return driver;

	}

	public static WebDriverWait getExplicitWait() {
		webDriverWait = new WebDriverWait(driver, 60);
		return webDriverWait;
	}

	public static JavascriptExecutor getJavaScriptExecutor() {
		js = (JavascriptExecutor) driver;

		return js;
	}

	public static void setupDriver() {
		if (seleniumDriver == null) {
			seleniumDriver = new SeleniumDriver();
		}
	}

	public static void tearDown() {
		if (driver != null) {
			driver.close();
			driver.quit();
		}
		seleniumDriver = null;
	}
}
