
package Booking_OTA;

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

public class MMT_AutomatedSearch 
{

public WebDriver driver;
//public List<String> Searchhotelnames=null;
//public List<String> Searchaddress=null;

final static int hotelname=0;
final static int address=1;
final static int cityname=2;
final static int countryname=3;
	
	@Test
	public void BookingAutomation() throws Exception
	{
	 System.setProperty("webdriver.chrome.silentOutput", "true");
     System.setProperty("webdriver.chrome.driver","D:\\Drivers\\202\\chromedriver.exe");
     
        ChromeOptions options=new ChromeOptions();
		options.setExperimentalOption("useAutomationExtension", false);
		options.setExperimentalOption("excludeSwitches",Collections.singletonList("enable-automation"));
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);
		options.setExperimentalOption("prefs", prefs);
		
		
     
		driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.makemytrip.com/hotels/");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.cssSelector("bu tton#hsw_search_button")).click();
		
		Object[][] Bookingdata=FileFunctions.ReadExcelData("D:\\SelenenumTestData\\MappingInputFile_Booking_Manual.xlsx","List");  
		List<Double> High_similarityList=new ArrayList<>();
		//List<Double> High_similarityLocation=new ArrayList<>();

		
		String Sourcehotelname=null;
		List<String> keyset=new ArrayList<>();keyset.add("Religious Place");keyset.add("Area");keyset.add("Place");keyset.add("City");
		 keyset.add("Landmark");
		 keyset.add("Region");
		 keyset.add("Region");
		
		   for(int i=1;i<Bookingdata.length;i++)
		   {
			   String hotelurl="";
			   String MMT_hotelname="";
			   String MMT_Address="";
			   List<String> WebsiteData=new ArrayList<>();
			   		   
			   try
			   {
		 String Sourcehotelname_main=Bookingdata[i][hotelname].toString();
		 String city=Bookingdata[i][cityname].toString();
		 String country=Bookingdata[i][countryname].toString();
			
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	    driver.findElement(By.cssSelector("input#city")).sendKeys(Keys.ENTER);
		Thread.sleep(1000);

		Sourcehotelname=Sourcehotelname_main+", "+city+", "+country;
		driver.findElement(By.cssSelector("div.hsw_autocomplePopup>form>div>input")).sendKeys(Keys.CONTROL + "a");
		driver.findElement(By.cssSelector("div.hsw_autocomplePopup>form>div>input")).sendKeys(Keys.DELETE);
		driver.findElement(By.cssSelector("div.hsw_autocomplePopup>form>div>input")).sendKeys(Sourcehotelname_main);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		List<WebElement> searchname=driver.findElements(By.cssSelector("li[id^='react-autowhatever-1-section-0-item']"));
		Iterator<WebElement> searchname_add=searchname.iterator();
		
		if(searchname.size()>=1)
		{
	    while(searchname_add.hasNext())
	    {
		   try
		   {
	    	Thread.sleep(6000);
		    String hotelname_MMT="";
		    try
		    {
		    hotelname_MMT=searchname_add.next().findElement(By.cssSelector("li[id^='react-autowhatever-1-section-0-item']>div>div>div>p:nth-child(1)")).getText();
		    }catch(Exception e) {};
		    
	        High_similarityList.add(String_Similarity_Score.String_Matcher_Similarity(Sourcehotelname, hotelname_MMT));
	    	System.out.println(Sourcehotelname+"  "+hotelname_MMT);
		   }
		   
		   catch(Exception e)
		   {
			   
		   }
	    	
	    	 }
		}
		
		else
		{
			break;
		}
	    
	    Double score=Collections.max(High_similarityList);
	    System.out.println(score);

	    if(score>.45)
	    {
	    	int hotelindex=High_similarityList.indexOf(Collections.max(High_similarityList));
	    	try
	    	{
	    	searchname.get(hotelindex).click();
	    	}
	    	catch(StaleElementReferenceException e)
	    	{
		    searchname.get(hotelindex).click();

	    	}
	    	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	 	    driver.findElement(By.cssSelector("button#hsw_search_button")).click();	
	 	    driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	 	    hotelurl= driver.getCurrentUrl();
	 	    try{
	 	    MMT_hotelname=driver.findElement(By.cssSelector("div.prmProperty>h1")).getText();
	 	    }catch(Exception e) {};
		    String htmlsource=driver.findElement(By.xpath("//div[@id=\"pageLoader\"]//following-sibling::script[1]")).getAttribute("innerHTML");
            MMT_Address=htmlsource.substring(htmlsource.indexOf("address"), htmlsource.indexOf("checkinTime"));
   		    String newsource = Pattern.compile("address\":{\"line1\":\"", Pattern.LITERAL | Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE).matcher(MMT_Address).replaceAll(Matcher.quoteReplacement(""));
   		    String newsource1 = Pattern.compile("\",\"line2\":\"", Pattern.LITERAL | Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE).matcher(newsource).replaceAll(Matcher.quoteReplacement(","));
   		    String newsource2 = Pattern.compile("\"},\"", Pattern.LITERAL | Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE).matcher(newsource1).replaceAll(Matcher.quoteReplacement(","));

            if(hotelurl.contains("makemytrip"))
            {
            WebsiteData.add(MMT_hotelname);
            WebsiteData.add(newsource2);
            WebsiteData.add(hotelurl);
            }
            
            else
            {
            	WebsiteData.add(" ");
                WebsiteData.add(" ");
                WebsiteData.add(" ");
            }

	    }
   	        
	    //Thread.sleep(8000);
	    
	
	    }
			   catch(Exception e)
			    {
			    	e.printStackTrace();
			    }

	ExcelRead_Write.writeExcel("D:\\SelenenumTestData\\MappingInputFile_Booking_Manual.xlsx","List",i,WebsiteData);
	//searchname.clear();
	High_similarityList.clear();
	WebsiteData.clear();
			   
}
		   
	}

}
