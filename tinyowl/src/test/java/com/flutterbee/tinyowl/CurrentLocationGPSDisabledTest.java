package com.flutterbee.tinyowl;

import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.LinkedHashSet;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CurrentLocationGPSDisabledTest extends BaseTest {
	
	@BeforeClass
	public void checkGPS() throws MalformedURLException
	{
		LocationSettingsPage settings=new LocationSettingsPage(getDriverForGPSSettings());
		settings.goToLocation();
		settings.offSwitch();
		settings.quitLocationDriver();
	}
	
	@Test
	public void CurrentLocationGPSDisabledTestCase() throws MalformedURLException{
		setClassName(CurrentLocationGPSDisabledTest.class.getSimpleName());
		//System.out.println("\nExecuting - " +getClassName());
		Reporter.log("\nExecuting - " +getClassName(),true);
		HomePage hp=new HomePage(getDriverForApp());
		hp.waitForPageToLoad(5);
		
		// if a cached version of the app is opened
		if(hp.isEditLocationButtonDisplayed())
		{
			ManualLocationEntryPage manualPage = hp.clickEditLocation();
			if(manualPage.isManualLocationEntryPageDisplayed())
			{
				hp=manualPage.clickUseMyLocationLink();
			}
			else
			{
				Assert.fail("Page for searching locations manually is not displayed");
			}
		}
		else if(hp.iscancelAndSettingsLabelDisplayed())
		{
			LocationSettingsPage locSettings=hp.clickSettingsLink();
			if(locSettings.isLocationHeadingDisplayed())
			{
				locSettings.onSwitch();
				if(locSettings.isimproveLocationAccuracyTitleDisplayed())
					locSettings.clickAgreeButton();
				hp=locSettings.back();
			}
			else
			{
				Assert.fail("Location Settings page is not displayed");
			}
			
			if(hp.iscancelAndSettingsLabelDisplayed())
			{
				Assert.fail("Restaurants list is not displayed even after enabling the GPS");
			}
		}
		else
		{
			Assert.fail("'Please turn on location settings' screen is not displayed");
		}
		
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
