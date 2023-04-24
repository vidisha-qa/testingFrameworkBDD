package runner;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.utils.EmailBackUp;
import com.utils.ScreenRecordingUtil;
import com.utils.SeleniumDriver;
import com.utils.StaticVariables;
import com.utils.UnZipFile;

import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import io.cucumber.testng.TestNGCucumberRunner;


@CucumberOptions(
		
        plugin = {"pretty", "html:target/cucumber-reports/cucumber-pretty","json:target/cucumber-reports/CucumberTestReport.json"},
        features = "src/test/resources/features",
        glue = "stepdefinitions",
        //@SmokeTest
        monochrome=true
     
		)
public class RunCuke {
	private TestNGCucumberRunner testNGCucumberRunner;
    private String featureName;

    @BeforeSuite
    public static void beforeSuiteAction() throws IOException{
    	 Date dt = new Date();
 		SimpleDateFormat format = new SimpleDateFormat("MMdd_HHmm_aa");
 		StaticVariables.strTimeStamp = format.format(dt);
 		
 		File file = new File("./test-recordings");
     	if(file.exists()) {
     		System.out.println("Previous Recording file exits : "+file.exists());
     		boolean deleted = true;
     		for (int i = 0; i < file.listFiles().length; i++) {
     			System.out.println(file.listFiles()[i].getName());
     			file.listFiles()[i].delete();
     		}
     		if (deleted) {
         		System.out.println("Previous Recording file deleted : "+file.delete());
     		} else {
     			System.out.println("Test Recording cannot be deleted because of some technical issues. Please check manually.");
     		}
     	}
     	try {
     		ScreenRecordingUtil.startRecord("Execution_QA");
     	} catch (Exception e) {
     		// TODO Auto-generated catch block
     		e.printStackTrace();
     	}
    }
	 
    @BeforeClass(alwaysRun = true)
    public void setUpClass() throws Exception {
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());

		}
    
    
    @Test(groups = "cucumber", description = "Runs Cucumber Scenarios", dataProvider = "scenarios")
    public void scenario(PickleWrapper pickle, FeatureWrapper cucumberFeature){
    	
        testNGCucumberRunner.runScenario(pickle.getPickle());
    }
    
 
    @DataProvider
    public Object[][] scenarios() {
        return testNGCucumberRunner.provideScenarios();
    }
    
    @AfterMethod
    public void close () {
    	SeleniumDriver.tearDown();    	
    }
   
 
    @AfterClass(alwaysRun = true)
    public void tearDownClass() throws Exception {
        testNGCucumberRunner.finish();
        
    }
    
    @AfterSuite
    public void extentflush() {
    	try {
			ScreenRecordingUtil.stopRecord();	
			 
			EmailBackUp email = new EmailBackUp();
	        email.sendReport();
    	}
    	catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
