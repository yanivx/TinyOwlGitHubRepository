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
	
	@BeforeClass
	public void checkGPS() throws MalformedURLException
	{
		LocationSettingsPage settings=new LocationSettingsPage(getDriverForGPSSettings());
		settings.goToLocation();
		settings.offSwitch();
		settings.quitLocationDriver();
	}	
	
	@Test
	@Parameters({ "location" }) //String location
	public void SearchLocationManuallyGPSDisabledTestCase(String location) throws MalformedURLException{
				
		setClassName(SearchLocationManuallyGPSDisabledTest.class.getSimpleName());	
		
		//System.out.println("\nExecuting - " +getClassName());
		Reporter.log("\nExecuting - " +getClassName(),true);
		
		HomePage hp=new HomePage(getDriverForApp());
		hp.waitForPageToLoad(5);
		
		ManualLocationEntryPage manualPage = null;
		
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
		}// if a cached version of the app is opened
		else if(hp.isEditLocationButtonDisplayed())
		{
			manualPage = hp.clickEditLocation();
		}	
		else
		{
			Assert.fail("'Please turn on location settings' screen is not displayed");
		}
		
		if(manualPage.isManualLocationEntryPageDisplayed())
		{
			manualPage.enterLocationManually(location);
			hp=manualPage.selectLocationManually(location);
			if(hp==null)
				Assert.fail("Location not found");
			
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
		else
		{
			Assert.fail("Page for searching locations manually is not displayed");
		}
	
	}
}

