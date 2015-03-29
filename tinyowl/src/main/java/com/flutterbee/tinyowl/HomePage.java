package com.flutterbee.tinyowl;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class HomePage extends Page {
	static WebDriverWait wb;
	
	public HomePage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	 /********************************************************************
     * 						Page Element Declaration
     ********************************************************************/	

	@FindBy(id="android:id/content")
	WebElement cancelAndSettingsFrame;
	
	@FindBy(id="com.flutterbee.tinyowl:id/question_title")
	WebElement cancelAndSettingsLable;
	
	@FindBy(id="com.flutterbee.tinyowl:id/right_text")
	WebElement settingsLink;

	@FindBy(id="com.flutterbee.tinyowl:id/left_text")
	WebElement cancelLink;
	
	@FindBy(id="com.flutterbee.tinyowl:id/action_bar_count_text")
	WebElement restaurantCount;
	
	@FindBy(id="com.flutterbee.tinyowl:id/restaurant_list_view")
	WebElement restaurantListView;
	
	@FindBy(id="com.flutterbee.tinyowl:id/restaurant_name")
	WebElement restaurantName;	
	
	@FindBy(id="com.flutterbee.tinyowl:id/actionview_edit_location")
	WebElement editLocationButton;
	
	@FindBy(id="com.flutterbee.tinyowl:id/actionview_location_name")
	WebElement currentLocationName;
	
	@FindBy(id="com.flutterbee.tinyowl:id/no_button")
	WebElement paytmUpdateNotNowButton;
	
	@FindBy(xpath="//android.widget.LinearLayout")
	List<WebElement> restaurantNamesList;	
	
	
	/********************************************************************
   	 * 						Page Element Verification
   	 ********************************************************************/
	
	public boolean isPaytmUpdateNotNowButtonDisplayed()
	{
		if(isElementPresent(By.id("com.flutterbee.tinyowl:id/no_button"))){
    		return paytmUpdateNotNowButton.isDisplayed() && paytmUpdateNotNowButton.isEnabled();
    	}else{
    		return false;
    	}
	}
	
	public boolean isRestaurantListViewDisplayed()
	{
		if(isElementPresent(By.id("com.flutterbee.tinyowl:id/restaurant_list_view"))){
    		return restaurantListView.isDisplayed() && restaurantListView.isEnabled();
    	}else{
    		return false;
    	}
	}
	
	/*
	public boolean isRestaurantNameDisplayed()
	{
		if(isElementPresent(By.id("com.flutterbee.tinyowl:id/restaurant_name"))){
    		return restaurantName.isDisplayed() && restaurantName.isEnabled();
    	}else{
    		return false;
    	}
	}
	*/
	
	public boolean isEditLocationButtonDisplayed()
	{
		if(isElementPresent(By.id("com.flutterbee.tinyowl:id/actionview_edit_location"))){
    		return editLocationButton.isDisplayed() && editLocationButton.isEnabled();
    	}else{
    		return false;
    	}
	}
		
	public boolean iscancelAndSettingsLabelDisplayed()
	{
		if(isElementPresent(By.id("com.flutterbee.tinyowl:id/question_title"))){
    		return cancelAndSettingsLable.isDisplayed() && cancelAndSettingsLable.isEnabled();
    	}else{
    		return false;
    	}
	}
	
	public boolean isRestaurantCountDisplayed()
	{
		if(isElementPresent(By.id("com.flutterbee.tinyowl:id/action_bar_count_text"))){
    		return restaurantCount.isDisplayed() && restaurantCount.isEnabled();
    	}else{
    		return false;
    	}
	}
	
	public void clickPaytmUpdateNotNowButton()
	{
		paytmUpdateNotNowButton.click();
		waitForPageToLoad(2);
	}
	
	public String getCurrentLocationName()
	{
		return currentLocationName.getText();
	}
	
	public ManualLocationEntryPage clickEditLocation()
	{
		editLocationButton.click();
		waitForPageToLoad(2);
		return new ManualLocationEntryPage(driver);
	}
	
	public LocationSettingsPage clickSettingsLink()
	{
		settingsLink.click();
		waitForPageToLoad(2);
		return new LocationSettingsPage(driver);
	}
	
	public RetryOrManualEntryOptionPage clickCancelLink()
	{
		cancelLink.click();
		return new RetryOrManualEntryOptionPage(driver);
	}
    	
	public LinkedHashSet<String> getListOfRestaurants()
	{
		return scroll(restaurantListView,15,true,getCountOfRestaurants());
	}
	
	@SuppressWarnings("serial")
    private LinkedHashSet<String> scroll(WebElement viewToScroll, int speed, boolean scrollDown, int totalNoOfRestaurants) throws org.openqa.selenium.NoSuchElementException {
		
		LinkedHashSet<String> names=new LinkedHashSet<String>();		
        RemoteWebDriver remoteWebDriver = (RemoteWebDriver)driver;

        int offSize = remoteWebDriver.manage().window().getSize().getHeight() / 3;
        int screenSize = remoteWebDriver.manage().window().getSize().getHeight();
        Double initialYPos = 0.0;
        Double finalYPos = 0.0;

        final Double scrollSpeed = 30.0 / speed;
        final Double xPosition = remoteWebDriver.manage().window().getSize().getWidth() - 15.0;
        
        if (scrollDown) {
            // Code to scroll down
            initialYPos = (double) screenSize - offSize;
            finalYPos = initialYPos - offSize;
        }
        else {
            // Code to scroll up
            finalYPos = (double) screenSize - offSize;
            initialYPos = finalYPos - offSize;

        }
        final Double initialY = initialYPos;
        final Double finalY = finalYPos-150;
        
        int oldCount=0;
        while(true){
        	oldCount=names.size();
        	//List<WebElement> restaurantNamesList = driver.findElements(By.xpath("//android.widget.LinearLayout"));
	       
			for(WebElement restaurantNameElement : restaurantNamesList)
			{
				try{
					if((restaurantNameElement.findElement(By.id("com.flutterbee.tinyowl:id/restaurant_name")).isDisplayed()))  
					{
						String nameOfRestaurant= restaurantNameElement.findElement(By.id("com.flutterbee.tinyowl:id/restaurant_name")).getText();
						names.add(nameOfRestaurant);
					}
				}
				catch(Exception ex)
				{
					//ex.printStackTrace();
				}		
			}

			if(oldCount==names.size())
			{
				 break;
			}
			else
			{	                
				remoteWebDriver.executeScript("mobile: swipe", new HashMap<String, Double>() {
	            {
	                put("touchCount", 1.0);
	                put("startX", xPosition);
	                put("startY", initialY);
	                put("endX", xPosition);
	                put("endY", finalY);
	                put("duration", scrollSpeed);
	            }
				});
			}
        }
        return names;
	}
	
	public int getCountOfRestaurants()
	{
		if(isRestaurantCountDisplayed())
		{	
			String noOfRestaurants=restaurantCount.getText();
			noOfRestaurants = noOfRestaurants.replaceAll("\\D+","");
			int totalNoOfRestaurants=Integer.parseInt(noOfRestaurants);
			return totalNoOfRestaurants;
		}
		else
		{
			Assert.fail("Restaurant Count is not displayed");
			return 0;
		}
	}
}
