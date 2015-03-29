package com.flutterbee.tinyowl;

import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.LinkedHashSet;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CurrentLocationGPSEnabledTest extends BaseTest {
	
	// Enable GPS (Location) settings
	@BeforeClass
	public void checkGPS() throws MalformedURLException
	{
		LocationSettingsPage settings=new LocationSettingsPage(getDriverForGPSSettings());
		try
		{
			settings.goToLocation();
			settings.onSwitch();
		}
		catch(Exception ex)
		{
			Assert.fail("Unable to set GPS (Location) settings");
		}
		finally{
			settings.quitLocationDriver();
		}
	}	
	
	@Test
	public void currentLocationGPSEnabledTestCase() throws MalformedURLException{
		setClassName(CurrentLocationGPSEnabledTest.class.getSimpleName());
		
		//System.out.println("\nExecuting - " +getClassName());
		Reporter.log("\nExecuting - " +getClassName(),true);
		
		HomePage hp=new HomePage(getDriverForApp());
		hp.waitForPageToLoad(5);
		
		if(hp.iscancelAndSettingsLabelDisplayed())
			Assert.fail("Kindly check the GPS settings");
		
		// to click 'Not Now' when asked for update
		if(hp.isPaytmUpdateNotNowButtonDisplayed())
			hp.clickPaytmUpdateNotNowButton();
		
		// scroll and display all the Restaurants in current location
		if(hp.isRestaurantListViewDisplayed())
		{
			LinkedHashSet<String> restaurants = hp.getListOfRestaurants();
			//System.out.println("Following are the Restaurants in "+ hp.getCurrentLocationName() + " - ");
			Reporter.log("Following are the Restaurants in "+ hp.getCurrentLocationName() + " - ",true);
			Iterator<String> itr = restaurants.iterator();
	        while(itr.hasNext()){
	            //System.out.println(itr.next());
	        	Reporter.log(itr.next(),true);
	        }
        }
		else
		{
			Assert.fail("Restaurants list is not displayed");
		}
	}		
}
