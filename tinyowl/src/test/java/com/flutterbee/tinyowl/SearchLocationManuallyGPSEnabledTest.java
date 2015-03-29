package com.flutterbee.tinyowl;

import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.LinkedHashSet;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class SearchLocationManuallyGPSEnabledTest extends BaseTest {

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
	@Parameters({ "location" }) 	// input location
	public void searchLocationManuallyGPSEnabledTestCase(String location) throws MalformedURLException{
				
		setClassName(SearchLocationManuallyGPSEnabledTest.class.getSimpleName());

		//System.out.println("\nExecuting - " +getClassName());
		Reporter.log("\nExecuting - " +getClassName(),true);
		
		HomePage hp=new HomePage(getDriverForApp());
		hp.waitForPageToLoad(5);
		
		if(hp.iscancelAndSettingsLabelDisplayed())
			Assert.fail("Kindly check the GPS settings");
		
		
		if(hp.isEditLocationButtonDisplayed())
		{
			ManualLocationEntryPage manualPage = hp.clickEditLocation();
			if(manualPage.isManualLocationEntryPageDisplayed())
			{
				manualPage.enterLocationManually(location);		//	Enter a location Manually
				hp=manualPage.selectLocationManually(location);	//	check whether the service is currently active or not in the searched location
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
					Assert.fail("Restaurant view list is not displayed");
				}
			}
			else
			{
				Assert.fail("Page for searching locations manually is not displayed");
			}
		}
		else
		{
			Assert.fail("Edit location button is not being displayed");
		}
	}	
}
