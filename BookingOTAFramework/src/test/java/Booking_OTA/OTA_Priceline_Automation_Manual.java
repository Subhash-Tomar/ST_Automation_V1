


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

public class OTA_Priceline_Automation_Manual 
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
     System.setProperty("webdriver.chrome.driver","D:\\Drivers\\113\\chromedriver.exe");
     
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
		
		Object[][] Bookingdata=FileFunctions.ReadExcelData("D:\\SelenenumTestData\\MappingInputFile_agodamanual.xlsx","List");  
		List<WebElement> searchname=null;
		List<Double> High_similarityList=new ArrayList<>();
		//List<Double> High_similarityLocation=new ArrayList<>();
		
		SimilarityStrategy strategy = new JaroWinklerStrategy();
		StringSimilarityService service = new StringSimilarityServiceImpl(strategy);

		String hotelurl="";
	    List<String> WebsiteData=null;
		String Sourcehotelname=null;
		
		   for(int i=1;i<Bookingdata.length;i++)
		   {
			  	   
			driver.get("https://www.priceline.com/?");

			   try
			   {
		WebsiteData=new ArrayList<>(); 
		String Sourcehotelname_main="";
		String Sourcehotelname_main2=Bookingdata[i][hotelname].toString();
		
		String city=Bookingdata[i][cityname].toString();
		String country=Bookingdata[i][countryname].toString();	
		String Sourcehotelname_main_C=Sourcehotelname_main2+","+city+","+country;
		
		driver.findElement(By.cssSelector("input#endLocation-typeahead-downshift-container-input")).sendKeys(Keys.CONTROL+"a");
		driver.findElement(By.cssSelector("input#endLocation-typeahead-downshift-container-input")).sendKeys(Keys.DELETE);
		
		driver.findElement(By.cssSelector("input#endLocation-typeahead-downshift-container-input")).sendKeys("Sallisaw Inn");
		driver.findElement(By.cssSelector("input#endLocation-typeahead-downshift-container-input")).sendKeys(Keys.BACK_SPACE);

		Thread.sleep(4000);
		List<WebElement> hotelicon=driver.findElements(By.cssSelector("ul.AutocompleteList>li"));
        
		if(hotelicon.size()>0)
		{
		 Wait<WebDriver> wait_searchname = new FluentWait<WebDriver>(driver)
		.withTimeout(Duration.ofSeconds(8))
		.pollingEvery(Duration.ofSeconds(2))
		.ignoring(Exception.class);
		 
		wait_searchname.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("ul.AutocompleteList>li")));

		searchname=driver.findElements(By.cssSelector("ul.AutocompleteList>li"));
		Iterator<WebElement> searchname_add =searchname.iterator();
		

    	String location="";
	    String hotelname="";
	    
	    if(searchname.size()<1)
	    {
		   
	    }
	    
	    
	    else if(searchname.size()>0)
	    {
	    int getdata=0;

	    while(searchname_add.hasNext())
	    {
	    
	    
	    
	    	try
	    	{
		    //Thread.sleep(6000);

	    	searchname_add.next();
	    	String hotelname_Complete=searchname.get(getdata).getAttribute("aria-label");
	    	
	    	High_similarityList.add(service.score(Sourcehotelname_main_C, hotelname_Complete));
	    	getdata=getdata+1;
	    	
	    	System.out.println(Sourcehotelname_main_C+"  "+hotelname_Complete);	
	        System.out.println(service.score(Sourcehotelname_main_C, hotelname_Complete));
	    	
	    	}catch(Exception e) {}
	    }
	    
	        
	        if(Collections.max(High_similarityList)>.20)
		    {
		    	int hotelindex=High_similarityList.indexOf(Collections.max(High_similarityList));
		    	try
		    	{
		    		
		    		try
		    		{
		    			driver.findElement(By.cssSelector("button.ab-close-button")).click();
		    		}catch(Exception e) {}
		    		
		    		
		    	
		    	searchname.get(hotelindex).click();
		    	driver.findElement(By.cssSelector("span[data-selenium-date=\"2023-06-14\"]")).click();
		    	driver.findElement(By.cssSelector("span[data-selenium-date=\"2023-06-15\"]")).click();
		    	driver.findElement(By.cssSelector("div#occupancy-box")).click();
		        }
		    	catch(Exception e)
		    	{

		    	}
		    	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		 	    driver.findElement(By.xpath("//span[text()='SEARCH']")).click();	
		 	   
		 	   Thread.sleep(2000);
		 	   driver.findElement(By.cssSelector("span.Searchbox__searchButton__text")).click();	
		    	hotelurl=driver.findElements(By.cssSelector("ol.hotel-list-container>li>div+div>a")).get(0).getAttribute("href");
		    	
			 	WebsiteData.add("");
		 	   WebsiteData.add("");
		 	   WebsiteData.add("");
		 	   WebsiteData.add(hotelurl);
		 	   WebsiteData.add("");
		 	   WebsiteData.add("");
		 	  WebsiteData.add("");
		 	 WebsiteData.add("");
		 	WebsiteData.add("");
		 	WebsiteData.add("");
		 	WebsiteData.add("");
		 	
			ExcelRead_Write.writeExcel("D:\\SelenenumTestData\\MappingInputFile_agodamanual.xlsx","List",i,WebsiteData);
			

	    	}
	    }
		}
			   }
			   catch(Exception e) {}
			   
			   finally
			   {
				   searchname.clear();
				High_similarityList.clear();
				WebsiteData.clear();
				
				Set<String> windows=driver.getWindowHandles();
				List<String> windows_=new ArrayList<>(windows);
				if(windows.size()>1)
				{
					driver.switchTo().window(windows_.get(1)).close();
					driver.switchTo().window(windows_.get(0));
				}
				   
			   }
			   
		        System.out.println("row number:"+i);

		   }
	}
}
	
			   
	    	
	    

