package com.flutterbee.tinyowl;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@SuppressWarnings("unused")
public class PageSupport {
	
	protected WebDriver driver;
	protected final int DEFAULT_WAIT = Integer.parseInt(readConfigValue("WaitTime", "default"));
	
	public PageSupport(WebDriver driver) {
		this.driver = driver;
	}
	
	protected void waitForPageToLoad(int waitTime){
		driver.manage().timeouts().implicitlyWait(waitTime, TimeUnit.SECONDS);
	}

	protected boolean isImageLoaded(WebElement img){
		return (Boolean) ((JavascriptExecutor) driver).executeScript(
				"return arguments[0].complete", img);
	}

	protected boolean isElementDisplayed(WebElement element) {
		return (element != null && element.isDisplayed());
	}

	protected boolean isElementEnabled(WebElement element) {
		if (isElementDisplayed(element))
			return element.isEnabled();
		return false;
	}
	
	/**
	 * Function to read XML files
	 */
	public static String readXMLFile(String fileNameWithPath,String nodeName, String nodeValue){
		String valueReturned=null;
		try{
			File xmlFile=new File(fileNameWithPath);
			DocumentBuilderFactory dbFactory=DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder=dbFactory.newDocumentBuilder();
			Document doc=dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			NodeList nList = doc.getElementsByTagName(nodeName);
			
			for(int j=0;j<nList.getLength();j++){
				Node nNode=nList.item(j);
				//System.out.println("Node Name:"+nNode.getNodeName());
					
				if(nNode.getNodeType() == Node.ELEMENT_NODE){
					Element eElement = (Element) nNode;
					valueReturned=eElement.getElementsByTagName(nodeValue).item(0).getTextContent();
					//System.out.println("Node Value : " +valueReturned );
				}
			}
			
		}//End of Try
		catch(Exception e){
			e.printStackTrace();
		}//End of Catch
		return valueReturned;
	}
	
	/**
	 * Read Config File Value
	 */
	public static String readConfigValue(String nodeName, String elementName){
		 final String dir = System.getProperty("user.dir");
		 String configFilePath=dir+"\\Config File\\config.xml";
		 return readXMLFile(configFilePath,nodeName,elementName);
	}
}
