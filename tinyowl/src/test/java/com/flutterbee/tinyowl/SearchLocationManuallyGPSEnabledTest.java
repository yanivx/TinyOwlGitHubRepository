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

	@BeforeClass
	public void checkGPS() throws MalformedURLException
	{
		LocationSettingsPage settings=new LocationSettingsPage(getDriverForGPSSettings());
		settings.goToLocation();
		settings.onSwitch();
		settings.quitLocationDriver();
	}
	
	@Test
	@Parameters({ "location" }) 
	public void EnterLocationManuallyGPSEnabledTestCase(String location) throws MalformedURLException{
				
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
