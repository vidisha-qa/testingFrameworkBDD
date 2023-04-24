package stepdefinitions;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.pagelocators.RollsPageLocator;
import com.utils.SeleniumDriver;
import com.utils.WebElementActionsUtil;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.sourceforge.tess4j.TesseractException;


public class ElectoralRolls_TC1StepDef {
	public static WebDriver driver;
	RollsPageLocator objRollPageActions ;
	WebElementActionsUtil webelementActionObj;
	@Given("^User is on ELECTORAL ROLLS page$")
	public void user_is_on_ELECTORAL_ROLLS_page()  {
		// Write code here that turns the phrase above into concrete actions
		try {
			if(SeleniumDriver.driver == null ) {
				SeleniumDriver.setupDriver();
				driver= SeleniumDriver.openURL("chrome");

			}
			webelementActionObj = new WebElementActionsUtil(driver);
			String title = driver.getTitle();
			Assert.assertEquals(title, "Electoral Rolls");
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@When("^Select (.*) and (.*)$")
	public void select_districtName_and_AssemblyConstituency(String districtName, String assemblyConstituency) throws TesseractException {
		// Write code here that turns the phrase above into concrete actions
		objRollPageActions = new RollsPageLocator(driver);
		try {
		objRollPageActions.SelectDistrictName(districtName);
		objRollPageActions.SelectAssemblyConstituency(assemblyConstituency);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	@When("^Click on Get Polling Stations button$")
	public void click_on_Get_Polling_Stations_button()  {
		// Write code here that turns the phrase above into concrete actions
		objRollPageActions = new RollsPageLocator(driver);
		try {
			objRollPageActions.click_on_GetPollStation();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@Then("^Click on view and dowload the vote poll$")
	public void click_on_view_Download_VotePoll()  {
		objRollPageActions = new RollsPageLocator(driver);
		try {
			objRollPageActions.downloadVotePoll();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TesseractException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
