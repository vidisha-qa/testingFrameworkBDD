package com.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class WebElementActionsUtil 
{
	public static WebDriver driver;
	public WebElementActionsUtil(WebDriver driver) {
		this.driver=driver;
	}

	public WebElement getElement(String xpath) {
		WebDriverWait wait=new WebDriverWait(driver, 10);
		By loc = By.xpath(xpath);
		WebElement ele = wait.until(ExpectedConditions.presenceOfElementLocated(loc));
		return ele;
	}
	
	public static void clickElementByJS(WebDriver driver, WebElement element) {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("arguments[0].click();", element);

	}
	
	public static void drawBorder(WebDriver driver, WebElement ele) {
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("arguments[0].style.border='4px solid red'", ele);
	}
	
	public void eleClick(String xpath) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		By loc = By.xpath(xpath);
		WebElement ele = wait.until(ExpectedConditions.elementToBeClickable(loc));
		ele.click();
	}
	
	public void eleSendKeys(String xpath, String text) {
		getElement(xpath).clear();
		getElement(xpath).sendKeys(text);
	}
	
	
	public boolean eleIsDisplayed(String xpath) {
		boolean flag=false;
		getElement(xpath).isDisplayed();
		return flag;
	}

	
}
