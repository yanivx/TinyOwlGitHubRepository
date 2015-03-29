package com.flutterbee.tinyowl;

import io.appium.java_client.android.AndroidDriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LocationSettingsPage extends Page {

	public LocationSettingsPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	 /********************************************************************
     * 						Page Element Declaration
     ********************************************************************/	

	@FindBy(id="android:id/action_bar")
	WebElement locHeading;
	
	@FindBy(id="com.android.settings:id/switch_text")
	WebElement onOffLabel;
	
	@FindBy(id="com.android.settings:id/switch_widget")
	WebElement onOffWidget;
	
	@FindBy(id="android:id/alertTitle")
	WebElement improveLocationAccuracyTitle;
	
	@FindBy(id="com.google.android.gms:id/checkbox")
	WebElement doNotShowAgain;
		
	@FindBy(id="android:id/button1")
	WebElement agreeButon;
	
	@FindBy(name="Location")
	WebElement locationLink;
		
	/********************************************************************
   	 * 						Page Element Verification
   	 ********************************************************************/
	
	public boolean isImproveLocationAccuracyTitleDisplayed()
	{
		if(isElementPresent(By.id("android:id/alertTitle")) && improveLocationAccuracyTitle.getText().contains("Improve location")){
    		return improveLocationAccuracyTitle.isDisplayed() && improveLocationAccuracyTitle.isEnabled();
    	}else{
    		return false;
    	}
	}
	
	// go to Location in settings to Enable/Disable GPS (Location)
	public void goToLocation()
	{
		((AndroidDriver) driver).scrollTo("Location");
		//driver.findElement(By.name("Location")).click();
		locationLink.click();
	}
	
	// Agree Improve Location Accuracy option
	public void clickAgreeButton()
	{
		agreeButon.click();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}
	
	public boolean isLocationHeadingDisplayed()
	{
		if(isElementPresent(By.id("android:id/action_bar"))){
    		return locHeading.isDisplayed() && locHeading.isEnabled();
    	}else{
    		return false;
    	}
	}
	
	// get GPS status
	public String getSwitchStatus()
	{
		return onOffLabel.getText();
	}
	
	// quit driver used to Enable/Disable GPS (Location)
	public void quitLocationDriver()
	{
		if(driver!=null)
			driver.quit();
	}	
	
	// Enable GPS (Location) settings
	public void onSwitch()
	{
		if(getSwitchStatus().equals("Off"))
			onOffWidget.click();
	}
	
	// Disable GPS (Location) settings
	public void offSwitch()
	{
		if(getSwitchStatus().equals("On"))
			onOffWidget.click();
	}
	
	// Navigate back to home page
	public HomePage back()
	{
		driver.navigate().back();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		return new HomePage(driver);
	}
	
}
