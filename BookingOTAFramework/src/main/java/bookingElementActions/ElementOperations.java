package bookingElementActions;

import java.io.IOException;
import java.rmi.ServerException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import bookingFileOperations.FileFunctions;

public class ElementOperations
{
		public static void sendKeys(WebDriver driver, String locatorName, String data) throws IOException
		{
			
			try{
				String elementName = FileFunctions.getLocatorFromElementFile(locatorName);
				driver.findElement(By.cssSelector(elementName)).sendKeys(data);
			} 
			
			catch (ServerException e)
			{
				e.printStackTrace();
				//captureScreenshot();
			}
			
		}
		
		
		public static void click(WebDriver driver, String locatorName) throws IOException {
			
			try{
				String elementName = FileFunctions.getLocatorFromElementFile(locatorName);
				driver.findElement(By.cssSelector(elementName)).click();
			} 
			catch (ServerException e)
			{
				//e.printStackTrace();
				//captureScreenshot();
			}
			
		}
		
public static String getText(WebDriver driver, String locatorName) throws IOException
                 {
			
		         String elementName = FileFunctions.getLocatorFromElementFile(locatorName);
				return driver.findElement(By.cssSelector(elementName)).getText();
			} 
		
			
		
		
		//public static void captureScreenshot()
		////{
			
		}
	
