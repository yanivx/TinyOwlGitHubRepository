package com.flutterbee.tinyowl;

import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.LinkedHashSet;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CurrentLocationGPSDisabledTest extends BaseTest {
	
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
	public void currentLocationGPSDisabledTestCase() throws MalformedURLException{
		
		setClassName(CurrentLocationGPSDisabledTest.class.getSimpleName());
		
		//System.out.println("\nExecuting - " +getClassName());
		Reporter.log("\nExecuting - " +getClassName(),true);
		
		HomePage hp=new HomePage(getDriverForApp());
		hp.waitForPageToLoad(5);
		
		// if a cached version of the app showing restaurants in the last searched location is displayed
		if(hp.isEditLocationButtonDisplayed())
		{
			ManualLocationEntryPage manualPage = hp.clickEditLocation();
			if(manualPage.isManualLocationEntryPageDisplayed())
			{
				hp=manualPage.clickUseMyLocationLink();		// select My Location link
			}
			else
			{
				Assert.fail("Page for searching locations manually is not displayed");
			}
		}
		
		// if 'Please turn on location settings' screen is displayed
		else if(hp.iscancelAndSettingsLabelDisplayed())
		{
			LocationSettingsPage locSettings=hp.clickSettingsLink();
			if(locSettings.isLocationHeadingDisplayed())
			{
				// Enable GPS (Location) settings
				locSettings.onSwitch();
				if(locSettings.isImproveLocationAccuracyTitleDisplayed())
					locSettings.clickAgreeButton();
				// Navigate back to home page
				hp=locSettings.back();
			}
			else
			{
				Assert.fail("Location Settings page is not displayed");
			}
			
			// if 'Please turn on location settings' screen is displayed again even after enabling the GPS
			if(hp.iscancelAndSettingsLabelDisplayed())
			{
				Assert.fail("Restaurants list is not displayed even after enabling the GPS");
			}
		}
		else
		{
			Assert.fail("Neither 'Please turn on location settings' screen nor a 'cached version' of last searched location is displayed");
		}
		
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
