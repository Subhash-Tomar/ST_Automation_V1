

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
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import bookingFileOperations.FileFunctions;
import net.ricecode.similarity.JaroWinklerStrategy;
import net.ricecode.similarity.SimilarityStrategy;
import net.ricecode.similarity.StringSimilarityService;
import net.ricecode.similarity.StringSimilarityServiceImpl;

public class TicketAutomatedSearch 
{

public WebDriver driver;
public List<String> Searchhotelnames=null;
public List<String> Searchaddress=null;

final static int hotelname=0;
final static int address=1;
final static int cityname=2;
final static int countryname=3;
	
	@Test
	public void BookingAutomation() throws Exception
	{
	 System.setProperty("webdriver.chrome.silentOutput", "true");
     System.setProperty("webdriver.chrome.driver","D:\\Drivers\\112\\chromedriver.exe");
     
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
		
		Object[][] Bookingdata=FileFunctions.ReadExcelData("D:\\SelenenumTestData\\MappingInputFile_ticket.xlsx","List");  
		List<WebElement> searchname=null;
		List<Double> High_similarityList=new ArrayList<>();
		//List<Double> High_similarityLocation=new ArrayList<>();
		
		SimilarityStrategy strategy = new JaroWinklerStrategy();
		StringSimilarityService service = new StringSimilarityServiceImpl(strategy);

		String hotelurl="";
	    List<String> WebsiteData=null;
		String Sourcehotelname=null;
		
		int counter=0;
		
		   for(int i=1;i<Bookingdata.length;i++)
		   {
				
				driver.get("https://en.tiket.com/hotel");
				driver.findElement(By.cssSelector("span[data-testid=\"destination-input-text\"]")).click();

			  	   
			   Thread.sleep(2000);
			   try
			   {
		WebsiteData=new ArrayList<>(); 
		String Sourcehotelname_main="";
		String Sourcehotelname_main2=Bookingdata[i][hotelname].toString();
	   Sourcehotelname_main=Sourcehotelname_main2;
			
		
		String city=Bookingdata[i][cityname].toString();
		String country=Bookingdata[i][countryname].toString();	
		
		

		Sourcehotelname=Sourcehotelname_main+", "+city;
		String Sourcehotelname2=Sourcehotelname+" , "+country;
		
		driver.findElement(By.cssSelector("input[placeholder^=\"Search by accommodation name\"]")).sendKeys(Sourcehotelname);

		
		Thread.sleep(2000);
		
		try
		{
		List<WebElement> hotelicon=driver.findElements(By.cssSelector("div[data-testid=\"destination-row\"]"));
        
		if(hotelicon.size()>0)
		{
		

			hotelicon.get(0).click();
	driver.findElement(By.cssSelector("button[aria-label=\"search\"]")).click();
	Thread.sleep(2000);
		 
		 	    hotelurl=driver.getCurrentUrl();
		 	  
		 	    try
		 	    {
		 	    

		 	   String locations=driver.findElement(By.cssSelector("script#__NEXT_DATA__")).getAttribute("outerHTML").toString();
		 	   String Location_all=locations.substring(locations.indexOf("\"name\":\""),locations.indexOf("\"area\":"));
		 	  String booking_hotelname=  Location_all.substring(Location_all.indexOf(",\"name\":\""),Location_all.indexOf(",\"address\""));
		 	   WebsiteData.add(booking_hotelname);
		 	   
			 String Location_address=  Location_all.substring(Location_all.indexOf("\"address\":\""),Location_all.indexOf("\"postalCode\":"));

		 	//System.out.println(Location_address);
		 	WebsiteData.add(Location_address);
		}catch(Exception e) {};
		
		 	   WebsiteData.add("");
		 	   WebsiteData.add(hotelurl);
		 	   WebsiteData.add("");
		 	   WebsiteData.add("");
		 	   WebsiteData.add("");
		 	   WebsiteData.add("");
		 	   WebsiteData.add("");
		       WebsiteData.add("");
		 	   WebsiteData.add("");

		 		ExcelRead_Write.writeExcel("D:\\SelenenumTestData\\MappingInputFile_ticket.xlsx","List",i,WebsiteData);

	    }
		}catch(Exception e) {};
	       
	
	

		   }
			   
	   catch(Exception e)
	    {
	    e.printStackTrace();
	    }
			   
			   finally
			   {
				   WebsiteData.clear();
			   }
			   
			   counter=counter+1;
			   System.out.println("row number:"+counter);
	}
	   
			   
}
		   
	}


