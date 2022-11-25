package sample;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class Sample_Report {
	WebDriver driver;
	
	@Test
	public void ReportDemo() throws IOException {
		
		//Create a html report Template
		ExtentHtmlReporter reporter=new ExtentHtmlReporter("./Report/ExecutionReport.html");
		
		//Attach the report to the HTML template
		ExtentReports reports=new ExtentReports();
		reports.attachReporter(reporter);
		
		//create a test with test cases
		ExtentTest test=reports.createTest("Demo web shop Regression");
		
		//Test step
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		driver=new ChromeDriver();
		driver.get("https://demowebshop.tricentis.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		test.log(Status.PASS, "Application launched succesfully");
		
		
		test.pass("Application launch").addScreenCaptureFromPath(Capture_ScreenShot("Application launch"));
		driver.findElement(By.id("small-searchterms")).sendKeys("books");
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		test.log(Status.PASS, "Product search is succesful");
		test.log(Status.INFO,"Search is completed");
		driver.close();
		
		
		reports.flush();
		
		
	}
	//Method to capture the screenshot
	public String Capture_ScreenShot(String stepname) throws IOException {
		TakesScreenshot ts=(TakesScreenshot)driver;
		File src=ts.getScreenshotAs(OutputType.FILE);
		String destpath="./Snapshots/"+stepname+".png";
		FileHandler.copy(src,new File(destpath));
		
		return "."+destpath;
		
	}

}
