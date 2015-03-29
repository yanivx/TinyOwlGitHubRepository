package com.flutterbee.tinyowl;

import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.LinkedHashSet;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class DefaultLocationGPSEnabledTest extends BaseTest {
	
	@BeforeClass
	public void checkGPS() throws MalformedURLException
	{
		LocationSettingsPage settings=new LocationSettingsPage(getDriverForGPSSettings());
		settings.goToLocation();
		settings.onSwitch();
		settings.quitLocationDriver();
	}	
	
	@Test
	public void DefaultLocationGPSEnabledTestCase() throws MalformedURLException{
		setClassName(DefaultLocationGPSEnabledTest.class.getSimpleName());
		
		//System.out.println("\nExecuting - " +getClassName());
		Reporter.log("\nExecuting - " +getClassName(),true);
		
		HomePage hp=new HomePage(getDriverForApp());
		hp.waitForPageToLoad(5);
		
		if(hp.iscancelAndSettingsLabelDisplayed())
			Assert.fail("Kindly check the GPS settings");
		
		if(hp.isPaytmUpdateNotNowButtonDisplayed())
			hp.clickPaytmUpdateNotNowButton();
		
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
