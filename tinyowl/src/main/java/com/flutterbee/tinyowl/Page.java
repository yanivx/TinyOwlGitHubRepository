package com.flutterbee.tinyowl;

import io.appium.java_client.android.AndroidDriver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

@SuppressWarnings("unused")
public abstract class Page extends PageSupport{
	
	final static String dir = System.getProperty("user.dir");
	private static String DRIVER_URL = readConfigValue("DRIVER_URL", "default");
	private static String APP_NAME=readConfigValue("APP_NAME", "default");
	private static String DEVICE_NAME = readConfigValue("DEVICE_NAME", "default");
	private static String PLATFORM_VERSION=readConfigValue("PLATFORM_VERSION", "default");
	private static String PLATFORM_NAME=readConfigValue("PLATFORM_NAME", "default");
	private static String APP_PACKAGE=readConfigValue("APP_PACKAGE", "default");
	private static String APP_ACTIVITY=readConfigValue("APP_ACTIVITY", "default");
	
	public Page(WebDriver driver) {
		super(driver);
	}
	
	public static WebDriver getNewDriverForApp() throws MalformedURLException{
			
		File app=new File(dir+"\\Application\\"+APP_NAME);
		DesiredCapabilities capabilities=new DesiredCapabilities();
		capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
		//capabilities.setCapability("deviceName","ASUS");
		capabilities.setCapability("deviceName",DEVICE_NAME);
		capabilities.setCapability("platformVersion",PLATFORM_VERSION);
		//capabilities.setCapability("platformVersion","4.4.2");
		capabilities.setCapability("platformName",PLATFORM_NAME);
		capabilities.setCapability("app",app.getAbsolutePath());
		//System.out.println(app.getAbsolutePath());
		capabilities.setCapability("appPackage",APP_PACKAGE);
		capabilities.setCapability("appActivity",APP_ACTIVITY);
		return (new AndroidDriver(new URL(DRIVER_URL),capabilities));		
	}
	
	public static WebDriver getNewDriverForGPSSettings() throws MalformedURLException{
		
		//File app=new File(dir+"\\Application\\"+APP_NAME);
		DesiredCapabilities capabilities=new DesiredCapabilities();
		capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
		//capabilities.setCapability("deviceName","ASUS");
		capabilities.setCapability("deviceName",DEVICE_NAME);
		capabilities.setCapability("platformVersion",PLATFORM_VERSION);
		//capabilities.setCapability("platformVersion","4.4.2");
		capabilities.setCapability("platformName",PLATFORM_NAME);
		//capabilities.setCapability("app",app.getAbsolutePath());
		//System.out.println(app.getAbsolutePath());
		capabilities.setCapability("appPackage","com.android.settings");
		capabilities.setCapability("appActivity",".Settings");
		return (new AndroidDriver(new URL(DRIVER_URL),capabilities));		
	}

	public WebDriver getDriver(){
		return driver;
	}

	public void close(){
		Set<String> handles = driver.getWindowHandles();
		if (handles.size()>1){
			handles.remove(driver.getWindowHandle());
			driver.close();
			driver.switchTo().window(driver.getWindowHandles().iterator().next());
		} else {
			driver.close();
		}
	}
	
	public boolean isElementPresent(By by) {
		  try {
		    driver.findElement(by);
		    return true;
		  } catch (NoSuchElementException e) {
		    return false;
		  }
		}

}
