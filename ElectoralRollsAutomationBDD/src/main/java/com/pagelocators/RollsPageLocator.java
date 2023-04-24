package com.pagelocators;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.imageio.ImageIO;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.utils.WebElementActionsUtil;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;


public class RollsPageLocator {

	@FindBy(id="ctl00_ContentPlaceHolder1_ddlDist")
	public WebElement districtNameSelect;

	@FindBy(tagName = "h2")
	public WebElement h2Header;
	
	@FindBy(id = "ctl00_ContentPlaceHolder1_ddlAC")
	public WebElement assemblyConstituencySelect;

	@FindBy(id= "ctl00_ContentPlaceHolder1_btnlogin")
	public WebElement btnGetPollStation;

	@FindBy(xpath="//tbody/tr[2]/td[5]/a[text()='View']")
	public WebElement link_view;

	@FindBy(id="btnSubmit")
	public WebElement btn_Submit;

	@FindBy(xpath="//img[@id='Image2']")
	public WebElement imgCode;

	@FindBy(id="txtVerificationCode")
	public WebElement inputCode;

	public WebDriver driver;	

	public RollsPageLocator(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}
	public void SelectDistrictName(String districtName) {
		WebElementActionsUtil.drawBorder(driver, h2Header);
		System.out.println(districtName);
		WebElementActionsUtil.drawBorder(driver, districtNameSelect);
		districtNameSelect.click();

		List<WebElement> actionsList = districtNameSelect.findElements(By.xpath("//option"));

		//Traversing through dropdown list to select 'District Name'
		for (WebElement districtNameEle : actionsList) {
			if(districtNameEle.getText().contains(districtName)) {
				//Click on District Name 
				districtNameEle.click();
				break;			
			}
		}
	}

	public void SelectAssemblyConstituency(String assemblyConstituency)  {
		System.out.println(assemblyConstituency);
		WebElementActionsUtil.drawBorder(driver, assemblyConstituencySelect);

		assemblyConstituencySelect.click();
		List<WebElement> actionsList = assemblyConstituencySelect.findElements(By.xpath("//option"));

		//Traversing through dropdown list to select 'Assembly Constituency'
		for (WebElement assemblyConstituencyEle : actionsList) {
			if(assemblyConstituencyEle.getText().contains(assemblyConstituency)) {
				//Click on Assembly Constituency
				assemblyConstituencyEle.click();
				break;			
			}
		}
	}

	public void click_on_GetPollStation() throws InterruptedException {
		WebElementActionsUtil.drawBorder(driver, btnGetPollStation);
		btnGetPollStation.click();
		Thread.sleep(1000);
	}

	public void downloadVotePoll() throws InterruptedException, TesseractException, IOException {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(link_view));
		link_view.click();

		Thread.sleep(3000);

		//It will return list of all open windows
		Set multipleWindows = driver.getWindowHandles();
		// Now iterate using Iterator
		Iterator<String> it = multipleWindows.iterator();
		String ParentWindow = it.next();
		String child_window = it.next();

		System.out.println("Child : "+child_window);
		driver.switchTo().window(child_window);
/*
 * *Below code is to read captcha, Need to work on it little more.
 */
		
/*		
		// Along with driver pass element also in takeScreenshot() method.

		Screenshot screenshot = new AShot().coordsProvider(new WebDriverCoordsProvider()).takeScreenshot(driver,imgCode);

		ImageIO.write(screenshot.getImage(), "PNG", new File("code.png"));
		BufferedImage image = ImageIO.read(new FileInputStream("code.png"));

		// Create instance of OCR API
		Tesseract tesseract = new Tesseract();
		tesseract.setDatapath("data");
		tesseract.setLanguage("eng");
		String result = tesseract.doOCR(image);
		System.out.println("Result: "+result);

		//Entering hard-coded value because image reading is not working
		inputCode.sendKeys("9987");
		btn_Submit.click();
		
*/		
		driver.close();

		//switch to the parent window
		driver.switchTo().window(ParentWindow);


	}


}
