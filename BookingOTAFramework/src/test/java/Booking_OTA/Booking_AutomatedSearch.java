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

public class Booking_AutomatedSearch 
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
     System.setProperty("webdriver.chrome.driver","D:\\Drivers\\102\\chromedriver.exe");
     
        ChromeOptions options=new ChromeOptions();
		options.setExperimentalOption("useAutomationExtension", false);
		options.setExperimentalOption("excludeSwitches",Collections.singletonList("enable-automation"));
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);
		options.setExperimentalOption("prefs", prefs);
		
     
		driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.booking.com/searchresults.html?");
		
		Object[][] Bookingdata=FileFunctions.ReadExcelData("D:\\SelenenumTestData\\Automate_data\\MappingInputFile_Amarjeet2_28Jul_22.xlsx","List");  
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
			  	   
			   try
			   {
		WebsiteData=new ArrayList<>(); 
		String Sourcehotelname_main="";
		String Sourcehotelname_main2=Bookingdata[i][hotelname].toString();
		if(Sourcehotelname_main.length()>20)
		{
		 Sourcehotelname_main=Sourcehotelname_main2.substring(0, 19);
		}
		else
		{
			Sourcehotelname_main=Sourcehotelname_main2;
			
		}
		String city=Bookingdata[i][cityname].toString();
		String country=Bookingdata[i][countryname].toString();	
		
		
	    driver.findElement(By.xpath("//input[@name='ss']")).sendKeys(Keys.CONTROL + "a");
	    driver.findElement(By.xpath("//input[@name='ss']")).sendKeys(Keys.DELETE);

		Sourcehotelname=Sourcehotelname_main+", "+city;
		String Sourcehotelname2=Sourcehotelname+" , "+country;
		driver.findElement(By.name("ss")).sendKeys(Sourcehotelname);
		driver.findElement(By.name("ss")).sendKeys(Keys.BACK_SPACE);
		Thread.sleep(2000);
		List<WebElement> hotelicon=driver.findElements(By.cssSelector("span[data-testid=\"autocomplete-icon-hotel\"]"));
        
		if(hotelicon.size()>0)
		{
		 Wait<WebDriver> wait_searchname = new FluentWait<WebDriver>(driver)
		.withTimeout(Duration.ofSeconds(8))
		.pollingEvery(Duration.ofSeconds(2))
		.ignoring(Exception.class);
		 
		wait_searchname.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.f9a8ccdcc6")));

		searchname=driver.findElements(By.cssSelector("div.f9a8ccdcc6"));
		Iterator<WebElement> searchname_add =searchname.iterator();
		

    	String location="";
	    String hotelname="";
	    
	    if(searchname.size()<=1)
	    {
		    Thread.sleep(6000);
	    	searchname_add.next();
	    	String hotelname_Complete=searchname.get(0).findElement(By.cssSelector("div.a40619bfbe")).getText();
	    	location=searchname.get(0).findElement(By.cssSelector("div.a493a926bc")).getText();
	    	hotelname=hotelname_Complete+" "+location;
	    	
	    	High_similarityList.add(service.score(Sourcehotelname2, hotelname));
	        System.out.println(Sourcehotelname2+"  "+hotelname);
	        System.out.println(service.score(Sourcehotelname2, hotelname));

	        if(Collections.max(High_similarityList)>.55)
		    {
	        	Wait<WebDriver> wait_searchnamef = new FluentWait<WebDriver>(driver)
	        			.withTimeout(Duration.ofSeconds(10))
	        			.pollingEvery(Duration.ofSeconds(2))
	        			.ignoring(Exception.class);
	        			 
	        	wait_searchnamef.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.a493a926bc")));
	        	searchname.get(0).findElement(By.cssSelector("div.a493a926bc")).click();
		    	
		    	}
		    	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		 	    driver.findElement(By.xpath("//span[text()='Search']")).click();	
		 	    List<WebElement> hotels=driver.findElements(By.cssSelector("a[data-testid=\"title-link\"]"));
		 	    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		 
		 	    hotelurl=hotels.get(0).getAttribute("href");
		 	    String booking_hotelname=hotels.get(0).findElement(By.cssSelector("div:nth-child(1)")).getText();
			 	WebsiteData.add(booking_hotelname);

		 	    String booking_id=hotelurl.substring(hotelurl.indexOf("dest_id"), hotelurl.indexOf("dest_type"));
		 	    String final_booking_hotelid=booking_id.replace("dest_id=", "").replace("&","");
			 	//System.out.println(final_booking_hotelid);

             
		 	    String booking_para4details=hotelurl.substring(hotelurl.indexOf("www.booking.com/hotel"), hotelurl.indexOf(".html")).
		 	    replace("www.booking.com/hotel/", "")+".";
		 	    
		 	   String para4=booking_para4details.substring(booking_para4details.indexOf("/"),booking_para4details.indexOf(".")).replace("/", "");
		 	    
			 	//System.out.println(para4);
                String BasicPropertyData="BasicPropertyData:"+final_booking_hotelid;
		 	   
		 	   String Locationdetails= driver.findElement(By.cssSelector("script[data-capla-application-context=\"data-capla-application-context\"]+script+script")).getAttribute("innerHTML");

		 	   String pagename_="pageName\":\""+para4;
		 	   String Location_address=Locationdetails.substring(Locationdetails.indexOf(BasicPropertyData), Locationdetails.indexOf(pagename_)); 
		 	   System.out.println(Location_address);
		 	   //System.out.println(Location_address);
               String country_name= driver.findElement(By.cssSelector("div[data-testid=\"breadcrumbs\"]>nav>ol>li+li>span>a>span")).getText();
		 	   String loc_Booking=Location_address.substring(Location_address.indexOf("address"), Location_address.indexOf("countryCode")).replace("address\":\"", "").replace("\",\"city\":\"", ",").replace("\",\"", "");
		 	   System.out.println(loc_Booking);

			    String cityname_inputBooking=driver.findElement(By.name("ss")).getAttribute("value");

		 	   WebsiteData.add(loc_Booking);
		 	   WebsiteData.add(final_booking_hotelid);
		 	   WebsiteData.add(hotelurl);
		 	   WebsiteData.add(country_name);
		 	   WebsiteData.add(cityname_inputBooking);
		 	   WebsiteData.add("");
		 	   WebsiteData.add("");
		 	   WebsiteData.add("");
		       WebsiteData.add("");
		 	   WebsiteData.add("");


	    }
	    
	    
	    else if(searchname.size()>1)
	    {
	    int getdata=0;

	    while(searchname_add.hasNext())
	    {
	    
	    
	    
	    	try
	    	{
		    Thread.sleep(6000);

	    	searchname_add.next();
	    	String hotelname_Complete2=searchname.get(getdata).findElement(By.cssSelector("div.a40619bfbe")).getText();
	    	location=searchname.get(getdata).findElement(By.cssSelector("div.a493a926bc")).getText();
	    	hotelname=hotelname_Complete2+" "+location;
	    	
	    	High_similarityList.add(service.score(Sourcehotelname2, hotelname));
	    	getdata=getdata+1;
	    	
	    	System.out.println(Sourcehotelname2+"  "+hotelname);	
	        System.out.println(service.score(Sourcehotelname2, hotelname));
	        
	        if(Collections.max(High_similarityList)>.70)
		    {
		    	int hotelindex=High_similarityList.indexOf(Collections.max(High_similarityList));
		    	try
		    	{
		    	
		    	searchname.get(hotelindex).click();
		        }
		    	catch(Exception e)
		    	{
			    	searchname.get(hotelindex).click();

		    	}
		    	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		 	    driver.findElement(By.xpath("//span[text()='Search']")).click();	
		 	    List<WebElement> hotels=driver.findElements(By.cssSelector("a[data-testid=\"title-link\"]"));
		    	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		 	   hotelurl=hotels.get(0).getAttribute("href");
		 	    String booking_hotelname=hotels.get(0).findElement(By.cssSelector("div:nth-child(1)")).getText();
			 	WebsiteData.add(booking_hotelname);

		 	    String booking_id=hotelurl.substring(hotelurl.indexOf("dest_id"), hotelurl.indexOf("dest_type"));
		 	    String final_booking_hotelid=booking_id.replace("dest_id=", "").replace("&","");
			 	//System.out.println(final_booking_hotelid);

            
		 	    String booking_para4details=hotelurl.substring(hotelurl.indexOf("www.booking.com/hotel"), hotelurl.indexOf(".html")).
		 	    replace("www.booking.com/hotel/", "")+".";
		 	    
		 	   String para4=booking_para4details.substring(booking_para4details.indexOf("/"),booking_para4details.indexOf(".")).replace("/", "");
		 	    
			 	//System.out.println(para4);
               String BasicPropertyData="BasicPropertyData:"+final_booking_hotelid;
		 	   
		 	   String Locationdetails= driver.findElement(By.cssSelector("script[data-capla-application-context=\"data-capla-application-context\"]+script+script")).getAttribute("innerHTML");
               String country_name= driver.findElement(By.cssSelector("div[data-testid=\"breadcrumbs\"]>nav>ol>li+li>span>a>span")).getText();
		 	   String pagename_="pageName\":\""+para4;
		 	   String Location_address=Locationdetails.substring(Locationdetails.indexOf(BasicPropertyData), Locationdetails.indexOf(pagename_)); 
		 	   //System.out.println(Location_address);
		 	   String loc_Booking=Location_address.substring(Location_address.indexOf("address"), Location_address.indexOf("countryCode")).replace("address\":\"", "").replace("\",\"city\":\"", ",").replace("\",\"", "");
			    String cityname_inputBooking=driver.findElement(By.name("ss")).getAttribute("value");
		 	   WebsiteData.add(loc_Booking);
		 	   WebsiteData.add(final_booking_hotelid);
		 	   WebsiteData.add(hotelurl);
		 	   WebsiteData.add(country_name);
		 	   WebsiteData.add(cityname_inputBooking);
		 	  WebsiteData.add("");
		 	 WebsiteData.add("");
		 	WebsiteData.add("");
		 	WebsiteData.add("");
		 	WebsiteData.add("");

	    	}
	    	}
	    	catch(Exception e)
	    	{
	    		
	    	}
	    	
	    }
	    }
	
	        
	   
	ExcelRead_Write.writeExcel("D:\\SelenenumTestData\\Automate_data\\MappingInputFile_Amarjeet2_28Jul_2.xlsx","List",i,WebsiteData);
	searchname.clear();
	High_similarityList.clear();
	WebsiteData.clear();
	//High_similarityLocation.clear();

		   }
			   }
	   catch(Exception e)
	    {
	    e.printStackTrace();
	    }
	   
			   
}
		   
	}

}
