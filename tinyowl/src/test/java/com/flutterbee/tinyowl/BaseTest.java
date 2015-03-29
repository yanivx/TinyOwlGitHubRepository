package com.flutterbee.tinyowl;

import io.appium.java_client.android.AndroidDriver;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;



@SuppressWarnings("unused")
public abstract class BaseTest {
	
	protected WebDriver driver;
	String className="";
	
	protected WebDriver getDriverForApp() throws MalformedURLException{
		driver = Page.getNewDriverForApp();
		
		return driver;
	}
	
	protected WebDriver getDriverForGPSSettings() throws MalformedURLException{
		driver = Page.getNewDriverForGPSSettings();
		return driver;
	}
	
	@AfterClass
	public void teardown() throws IOException{	
		takeScreenshot(getClassName());
		//((AndroidDriver)driver).resetApp();
		if(driver!=null)
			driver.quit();
		
	}
		
	public void setClassName(String className)
	{
		this.className=className;
	}
	
	public String getClassName()
	{
		return className;
	}
	
	public void takeScreenshot(String className){
		String fileName="";
		
		String text="";
		final String dir1 = System.getProperty("user.dir");
		text=new SimpleDateFormat("MMM-dd-yyyy HH-mm-ss").format(new Date())+".png";
				
		fileName=className+"-"+text;

        File screenshot=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
			FileUtils.copyFile(screenshot, new File(dir1+"\\Screenshots\\"+fileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		    
	public void checkMemoryConsumption(){
		final long MEGABYTE = 1024L * 1024L;
		long memUsageinMB;

		 // Get the Java runtime
		Runtime runtime = Runtime.getRuntime();
		
		 // Run the garbage collector
		runtime.gc();
		
		 // Calculate the used memory
		long memory = runtime.totalMemory() - runtime.freeMemory();
		memUsageinMB=memory / MEGABYTE;
		
		//System.out.println("Used memory is bytes: " + memory);
		//System.out.println("Used memory is megabytes (MB): " + memUsageinMB);
	}
		
}
