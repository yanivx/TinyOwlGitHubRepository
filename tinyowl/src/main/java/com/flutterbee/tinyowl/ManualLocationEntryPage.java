package com.flutterbee.tinyowl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;

public class ManualLocationEntryPage extends Page{

	public ManualLocationEntryPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(id="com.flutterbee.tinyowl:id/location_query_text")
	WebElement enterManuallyTextBox;
	
	@FindBy(id="com.flutterbee.tinyowl:id/locality_listview")
	WebElement locationMatchListsView;
	
	@FindBy(id="com.flutterbee.tinyowl:id/restaurant_progress_and_error_view")
	WebElement locationNotActiveFrame;
	
	@FindBy(id="com.flutterbee.tinyowl:id/message_text")
	WebElement locationNotActiveMsg;
	
	@FindBy(id="com.flutterbee.tinyowl:id/message_location_name")
	WebElement locationNotActiveAddr;
	
	@FindBy(id="com.flutterbee.tinyowl:id/use_my_location_text")
	WebElement useMyLocation;
	
	public HomePage clickUseMyLocationLink()
	{
		useMyLocation.click();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		return new HomePage(driver);
	}	
	
	public boolean isLocationNotActiveFrameDisplayed()
	{
		if(isElementPresent(By.id("com.flutterbee.tinyowl:id/restaurant_progress_and_error_view"))){
    		return locationNotActiveFrame.isDisplayed() && locationNotActiveFrame.isEnabled();
    	}else{
    		return false;
    	}
	}
	
	public boolean isManualLocationEntryPageDisplayed()
	{
		if(isElementPresent(By.id("com.flutterbee.tinyowl:id/location_query_text"))){
    		return enterManuallyTextBox.isDisplayed() && enterManuallyTextBox.isEnabled();
    	}else{
    		return false;
    	}
	}
		
	public void enterLocationManually(String loc)
	{
		//enterManuallyTextBox.click();
		//enterManuallyTextBox.clear();
		enterManuallyTextBox.sendKeys(loc);
		//driver.findElement(MobileBy.AndroidUIAutomator(uiautomatorText))
	}
	
	public HomePage selectLocationManually(String loc)
	{
		//enterManuallyTextBox.sendKeys(loc);
		boolean found=false;
		List<WebElement> locList = locationMatchListsView.findElements(By.id("com.flutterbee.tinyowl:id/locality_title"));
        //List<WebElement> restaurantNamesList = dr.findElementsById("com.flutterbee.tinyowl:id/restaurant_summary_layout");
		
		// loop to find exact match
		for(WebElement location : locList)
		{
			if(location.getText().equalsIgnoreCase(loc))
			{
				location.click();
				if(!isLocationNotActiveFrameDisplayed())
				{
					found=true;
					break;
				}
				else
				{
					Reporter.log(locationNotActiveMsg.getText() + " " + locationNotActiveAddr.getText(), true);
					Assert.fail(locationNotActiveMsg.getText() + " " + locationNotActiveAddr.getText());
				}
			}
		}
		
		// loop to find partial match
		if(!found)
		{
			for(WebElement location : locList)
			{
				if(location.getText().contains(loc))
				{
					location.click();
					driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
					if(!isLocationNotActiveFrameDisplayed())
					{
						found=true;
						break;
					}
					else
					{
						Reporter.log(locationNotActiveMsg.getText() + " " + locationNotActiveAddr.getText(), true);
						Assert.fail(locationNotActiveMsg.getText() + " " + locationNotActiveAddr.getText());
					}
				}
			}
		}
		if(found)
			return new HomePage(driver);
		else
			return null;
	}
}
