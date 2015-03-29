package com.flutterbee.tinyowl;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RetryOrManualEntryOptionPage extends Page {

	public RetryOrManualEntryOptionPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(id="com.flutterbee.tinyowl:id/message_text")
	WebElement retryText;
	
	@FindBy(id="com.flutterbee.tinyowl:id/tap_to_retry_location")
	WebElement retryLocation;
	
	@FindBy(id="com.flutterbee.tinyowl:id/text_enter_manually")
	WebElement enterManuallyLink;
	
	
	
	public boolean isRetryOrManualEntryOptionDisplayed()
	{
		if(isElementPresent(By.id("com.flutterbee.tinyowl:id/message_text")) && retryText.getText().equalsIgnoreCase("Unable To detect your location")){
    		return retryText.isDisplayed() && retryText.isEnabled();
    	}else{
    		return false;
    	}
	}
	
	public HomePage clickRetryLocationLink()
	{
		if(isRetryOrManualEntryOptionDisplayed())
		{
			retryLocation.click();
			return new HomePage(driver);
		}
		else
		{
			System.out.println("Tap here to retry link is not displayed");
			return null;
		}
	}
	
	public ManualLocationEntryPage clickEnterLocationManuallyLink()
	{
		if(isRetryOrManualEntryOptionDisplayed())
		{	enterManuallyLink.click();
			return new ManualLocationEntryPage(driver);
		}
		else
		{
			System.out.println("Enter Manually link is not displayed");
			return null;
		}
		
	}
}
