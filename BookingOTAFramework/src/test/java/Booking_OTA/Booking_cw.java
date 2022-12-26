package Booking_OTA;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import bookingFileOperations.FileFunctions;

public class Booking_cw

{
	public WebDriver driver;
	public List<String> Searchhotelnames=null;
	public List<String> Searchaddress=null;


	final static int cityname=0;
	final static int countryname=1;

	@Test
	public void BookingAutomation() throws Exception
	
	{

		
		System.setProperty("webdriver.chrome.silentOutput", "true");
	     System.setProperty("webdriver.chrome.driver","D:\\Drivers\\101\\chromedriver.exe");
	     
	        ChromeOptions options=new ChromeOptions();
			options.setExperimentalOption("useAutomationExtension", false);
			options.setExperimentalOption("excludeSwitches",Collections.singletonList("enable-automation"));
			Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("credentials_enable_service", false);
			prefs.put("profile.password_manager_enabled", false);
			options.setExperimentalOption("prefs", prefs);
			
	     
			driver=new ChromeDriver();
			driver.manage().window().maximize();
			//driver.get("https://www.booking.com/index.html?");
			//driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(6));
			
			Object[][] Bookingdata=FileFunctions.ReadExcelData("D:\\SelenenumTestData\\Automate_data\\MappingInputFile_Booking_citywise.xlsx","List");  
			List<WebElement> searchname=null;
			
			String hotelurl2="";
		    List<String> WebsiteData=null;
			String Sourcehotelname=null;
			
			for(int i=1;i<Bookingdata.length;i++)
			   {
				driver.get("https://www.booking.com/index.html?");
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(6));
				  	
				 Thread.sleep(6000);
				  try
				   {
				WebsiteData=new ArrayList<>(); 
				String city=Bookingdata[i][cityname].toString();
				String country=Bookingdata[i][countryname].toString();	
				Sourcehotelname=city+", "+country;
			
			driver.findElement(By.cssSelector("input#ss")).sendKeys(Sourcehotelname);
			Thread.sleep(8000);
			WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(20));
			
			List<WebElement> elements=driver.findElements(By.cssSelector("label.sb-destination-label-sr+ul>li"));
			WebElement element=elements.get(0);
			wait.until(ExpectedConditions.elementToBeClickable(element));
			element.click();
			driver.findElement(By.cssSelector("div.xp__dates-inner > div:nth-child(2) > div > div > div")).click();
			driver.findElement(By.cssSelector("span.js-sb-submit-text ")).click();
			
			    hotelurl2=driver.getCurrentUrl();
			    String hotelurl=hotelurl2+"Bookingcw";
			    hotelurl.replace("&", "subhash");
			    //System.out.println(hotelurl);
			    
			    String cityname_inputBooking="";
			    String country_booking="";
			    String hotelscount="";
			    try
			    {
			    cityname_inputBooking=driver.findElement(By.name("ss")).getAttribute("value");
			    country_booking=driver.findElement(By.cssSelector("ol > li:nth-child(2) > span > a > span")).getText();
			    hotelscount=driver.findElement(By.cssSelector("div.efdb2b543b")).getText();

			    }
			    catch(Exception e) {};
			    
			    Thread.sleep(4000);
			    String p1="";
			    String p2="";
			    String p3="";
			    String p4="";
			    String p5="";
			    try
			    {
			 		    
			    String p11=hotelurl.substring(hotelurl.indexOf("ss="),hotelurl.length());
				p1=p11.substring(p11.indexOf("ss="), p11.indexOf("&")).replace("ss=","");
			    
			    }catch(Exception e) {};
			    
			    try
			    {
				
				String p22=hotelurl.substring(hotelurl.indexOf("ss_raw"),hotelurl.length());
				p2=p22.substring(p22.indexOf("ss_raw"), p22.indexOf("&")).replace("ss_raw=","");
					
			    }catch(Exception e) {};
			    
			    try
			    {
				 
				String p33=hotelurl.substring(hotelurl.indexOf("dest_id"),hotelurl.length());
				p3=p33.substring(p33.indexOf("dest_id"), p33.indexOf("&")).replace("dest_id=","");
					
					
			    }catch(Exception e) {};
			    try
			    {
			
				String p44=hotelurl.substring(hotelurl.indexOf("dest_type"),hotelurl.length());
				p4= p44.substring( p44.indexOf("dest_type"),  p44.indexOf("&")).replace("dest_type=","");
						
					
			    }catch(Exception e) {};
			    
			    try
			    {
				
				 String p55=hotelurl.substring(hotelurl.indexOf("iata"),hotelurl.length());
				 p5=p55.substring( p55.indexOf("iata"),  p55.indexOf("&")).replace("iata=","");
				 
				 
			    }catch(Exception e) {};
			    
			    
			    
			    
                String Propertychaincode=p1+"#"+p5+"#"+p4;
                String CityName_booking=p2+"#"+p3;
			    
                
                
              
		 	    WebsiteData.add(hotelurl2);
		 	    WebsiteData.add(Propertychaincode);
		 	    WebsiteData.add(CityName_booking);
		 	    WebsiteData.add(cityname_inputBooking);
		 	    WebsiteData.add(country_booking);
		 	    WebsiteData.add(p1);
		 	    WebsiteData.add(p2);
		 	    WebsiteData.add(p3);
		 	    WebsiteData.add(p4);
		 	    WebsiteData.add(p5);
		 	    WebsiteData.add(hotelscount);

		 	    ExcelRead_Write.writeExcel("D:\\SelenenumTestData\\Automate_data\\MappingInputFile_Booking_citywise.xlsx","List",i,WebsiteData);
		 		searchname.clear();
		 		WebsiteData.clear();
		 		
			
				   }
				  
				  catch(Exception e)
				  {
					  
				  }
				  
				
	  }
	}

}
