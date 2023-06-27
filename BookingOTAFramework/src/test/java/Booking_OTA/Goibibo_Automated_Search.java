

package Booking_OTA;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import bookingFileOperations.FileFunctions;

public class Goibibo_Automated_Search 
{

public WebDriver driver;


final static int hotelname=0;
final static int address=1;
final static int cityname=2;
final static int countryname=3;
	
	@Test
	public void BookingAutomation() throws Exception
	{
	 System.setProperty("webdriver.chrome.silentOutput", "true");
     System.setProperty("webdriver.chrome.driver","D:\\Drivers\\114\\chromedriver.exe");
     
        ChromeOptions options=new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
		options.setExperimentalOption("useAutomationExtension", false);
		options.setExperimentalOption("excludeSwitches",Collections.singletonList("enable-automation"));
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);
		options.setExperimentalOption("prefs", prefs);
		
		
		driver=new ChromeDriver(options);
		driver.manage().window().maximize();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(6));
		
		Object[][] Bookingdata=FileFunctions.ReadExcelData("D:\\SelenenumTestData\\MappingInputFile_Goibibo.xlsx","List");  
		List<Double> High_similarityList=new ArrayList<>();
		//List<Double> High_similarityLocation=new ArrayList<>();

		
		String Sourcehotelname=null;
		List<String> WebsiteData=new ArrayList<>();
		  
	 for(int i=1;i<Bookingdata.length;i++)
		   {
		 
		 driver.get("https://www.goibibo.com/hotels/");
			   Thread.sleep(4000);
			   String hotelurl="";
			   String MMT_hotelname="";
			   String MMT_Address="";
			 
			   
			   		   
			   try
			   {
				   
		 String Sourcehotelname_main=Bookingdata[i][hotelname].toString();
		 String city=Bookingdata[i][cityname].toString();
		 String country=Bookingdata[i][countryname].toString();
			
		Thread.sleep(1000);

		Sourcehotelname=Sourcehotelname_main+", "+city;
		driver.findElement(By.cssSelector("input[id^='downshift-']")).sendKeys(Keys.CONTROL + "a");
		driver.findElement(By.cssSelector("input[id^='downshift-']")).sendKeys(Keys.DELETE);
		Thread.sleep(1000);
		driver.findElement(By.cssSelector("input[id^='downshift-']")).sendKeys(Sourcehotelname);
		
		List<WebElement> searchname=driver.findElements(By.cssSelector("ul[id^='downshift-']"));
		
		if(searchname.size()>=1)
		{
			Thread.sleep(3000);
			searchname.get(0).click();
			driver.findElement(By.xpath("//button[text()=\"Search\"]")).click();
			
		 	hotelurl= driver.getCurrentUrl();
		 	    try{
		 	    	Thread.sleep(2000);
		 	    MMT_hotelname=driver.findElement(By.cssSelector("h1[itemprop=\"name\"]")).getText();
		 	    }catch(Exception e) {};
		 	    
		 	    try
		 	    {
	            MMT_Address=driver.findElement(By.cssSelector("span[itemprop=\"streetAddress\"]")).getText();
		 	    }catch(Exception e) {}
	          
	            WebsiteData.add(MMT_hotelname);
	            WebsiteData.add(MMT_Address);
	            WebsiteData.add(hotelurl);
	            	WebsiteData.add(" ");
	                WebsiteData.add(" ");
	                WebsiteData.add(" ");
	                WebsiteData.add(" ");
	                WebsiteData.add(" ");
	                WebsiteData.add(" ");
	                WebsiteData.add(" ");
	                WebsiteData.add(" ");
	            
	            
	  ExcelRead_Write.writeExcel("D:\\SelenenumTestData\\MappingInputFile_Goibibo.xlsx","List",i,WebsiteData);
	  System.out.println("row number"+i);

		}
		
		else
		{
		
			
		}
	    
	  
	
	    }
			  
	catch(Exception e)
			    {
			    	e.printStackTrace();
			    }

	 finally
				{
					WebsiteData.clear();
				}
	
	
			   
}
		   
	}

}
