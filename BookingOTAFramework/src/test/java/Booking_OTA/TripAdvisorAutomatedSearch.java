
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

public class TripAdvisorAutomatedSearch 
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
		
		Object[][] Bookingdata=FileFunctions.ReadExcelData("D:\\SelenenumTestData\\MappingInputFile_tripadvisor_Manual.xlsx","List");  
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
				
				driver.get("https://www.tripadvisor.in/Hotel_Review-g303506-d305503-Reviews-Windsor_Guanabara_Hotel-Rio_de_Janeiro_State_of_Rio_de_Janeiro.html");

			  	   
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
		
		new WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.elementToBeClickable(By.name("q"))).
		sendKeys(Keys.CONTROL + "a");
		driver.findElement(By.name("q")).sendKeys(Keys.DELETE);

		driver.findElement(By.name("q")).sendKeys(Sourcehotelname);

		
		//driver.findElement(By.name("q")).sendKeys(Keys.CONTROL + "a");
		//driver.findElement(By.name("q")).sendKeys(Keys.DELETE);
		//driver.findElement(By.name("q")).sendKeys(Sourcehotelname);
		Thread.sleep(2000);
		
		try
		{
		List<WebElement> hotelicon=driver.findElements(By.cssSelector("div#typeahead_results>a"));
        
		if(hotelicon.size()>0)
		{
		

			hotelicon.get(0).click();
	
		 
		 	    hotelurl=driver.getCurrentUrl();
		 	    try
		 	    {
		 	    String booking_hotelname=driver.findElement(By.cssSelector("h1#HEADING")).getText();
		 	   WebsiteData.add(booking_hotelname);
		 	    }catch(Exception e) {};
			 	
		 	    try
		 	    {
		 	    

		 	   String Location_address=driver.findElement(By.xpath("//*[@id=\"component_3\"]/div/div/div[2]/div/div[2]/div/div/div/span[2]/span")).getText();
		 	
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

		 		ExcelRead_Write.writeExcel("D:\\SelenenumTestData\\MappingInputFile_tripadvisor_Manual.xlsx","List",i,WebsiteData);

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


