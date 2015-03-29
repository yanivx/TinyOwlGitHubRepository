package com.flutterbee.tinyowl;

import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.LinkedHashSet;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class SearchLocationManuallyGPSDisabledTest extends BaseTest {
	
	// Disable GPS (Location) settings
	@BeforeClass
	public void checkGPS() throws MalformedURLException
	{
		LocationSettingsPage settings=new LocationSettingsPage(getDriverForGPSSettings());
		try
		{
			settings.goToLocation();
			settings.offSwitch();
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
	@Parameters({ "location" }) 	// input location
	public void searchLocationManuallyGPSDisabledTestCase(String location) throws MalformedURLException{
				
		setClassName(SearchLocationManuallyGPSDisabledTest.class.getSimpleName());	
		
		//System.out.println("\nExecuting - " +getClassName());
		Reporter.log("\nExecuting - " +getClassName(),true);
		
		HomePage hp=new HomePage(getDriverForApp());
		hp.waitForPageToLoad(5);
		
		ManualLocationEntryPage manualPage = null;
		
		// if 'Please turn on location settings' screen is displayed
		if(hp.iscancelAndSettingsLabelDisplayed())
		{
			RetryOrManualEntryOptionPage retryOption = hp.clickCancelLink();
			if(retryOption.isRetryOrManualEntryOptionDisplayed())
			{
				manualPage=retryOption.clickEnterLocationManuallyLink();
			}
			else
			{
				Assert.fail("Retry or Manual Entry Option is not displayed");
			}
		}
		// if a cached version of the app showing restaurants in the last searched location is displayed
		else if(hp.isEditLocationButtonDisplayed())
		{
			manualPage = hp.clickEditLocation();
		}	
		else
		{
			Assert.fail("Neither 'Please turn on location settings' screen nor a 'cached version' of last searched location is displayed");
		}
		
		// Enter a location Manually
		if(manualPage.isManualLocationEntryPageDisplayed())
		{
			manualPage.enterLocationManually(location);
			hp=manualPage.selectLocationManually(location); 	//	check whether the service is currently active or not in the searched location
			if(hp==null)
				Assert.fail("Location not found");
			
			// to click 'Not Now' when asked for update
			if(hp.isPaytmUpdateNotNowButtonDisplayed())
				hp.clickPaytmUpdateNotNowButton();
			
			// scroll and display all the Restaurants in the searched location
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
		else
		{
			Assert.fail("Page for searching locations manually is not displayed");
		}
	
	}
}

