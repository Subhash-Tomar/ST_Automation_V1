
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

public class MMT_AutomatedSearch 
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
		driver.get("https://www.makemytrip.com/hotels/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(6));
		driver.findElement(By.cssSelector("button#hsw_search_button")).click();
		
		Object[][] Bookingdata=FileFunctions.ReadExcelData("D:\\SelenenumTestData\\MappingInputFile_Makemytrip.xlsx","List");  
		List<Double> High_similarityList=new ArrayList<>();
		//List<Double> High_similarityLocation=new ArrayList<>();

		
		String Sourcehotelname=null;
		
		 
		 List<String> WebsiteData=new ArrayList<>();
			List<String> searchname_names=new ArrayList<>();
		 String hotelurl="";
		 String MMT_hotelname="";
		 String MMT_Address="";
		
	 for(int i=1;i<Bookingdata.length;i++)
		   {
		 
			   Thread.sleep(4000);
			  	   		   
			   try
			   {
		 String Sourcehotelname_main=Bookingdata[i][hotelname].toString();
		 String city=Bookingdata[i][cityname].toString();
		 String country=Bookingdata[i][countryname].toString();
			
	    driver.findElement(By.cssSelector("input#city")).sendKeys(Keys.ENTER);
		Thread.sleep(2000);

		Sourcehotelname=Sourcehotelname_main+", "+city;
		driver.findElement(By.cssSelector("div.hsw_autocomplePopup>form>div>input")).sendKeys(Keys.CONTROL + "a");
		driver.findElement(By.cssSelector("div.hsw_autocomplePopup>form>div>input")).sendKeys(Keys.DELETE);
		Thread.sleep(1000);
		driver.findElement(By.cssSelector("div.hsw_autocomplePopup>form>div>input")).sendKeys(Sourcehotelname);
		List<WebElement> searchname=null;
		List<WebElement> searchname_city=null;
	

    	Thread.sleep(4000);

		try
		{
	    searchname=driver.findElements(By.cssSelector("li[id^='react-autowhatever-1-section-0-item']"));
		}catch(Exception e) {System.out.println(e.getMessage());}
		
		try
		{
		searchname_city=driver.findElements(By.cssSelector("li[id^='react-autowhatever-1-section-0-item']>div>div>div+span"));
		searchname_city.forEach(element->searchname_names.add(element.getText()));
			
		
	}catch(Exception e) {System.out.println(e.getMessage());}
		
		
		

		if(searchname.size()>=1 && (searchname_names.contains("Hotel")||searchname_names.contains("Resort")||searchname_names.contains("Hostel")||searchname_names.contains("Resort")||
				searchname_names.contains("Homestay")||searchname_names.contains("Apartment")||
				searchname_names.contains("Guest House")||searchname_names.contains("Villa")))
		{
			
	    Iterator<WebElement> searchname_add=searchname.iterator();
	    while(searchname_add.hasNext())
	    {
		 
	    	Thread.sleep(2000);
		    String hotelname_MMT="";
		    WebElement element=searchname_add.next();
		    try
		    {
		    hotelname_MMT=element.findElement(By.cssSelector("li[id^='react-autowhatever-1-section-0-item']>div>div>div>p:nth-child(1)")).getText();		
		    
	        High_similarityList.add(String_Similarity_Score.String_Matcher_Similarity(Sourcehotelname, hotelname_MMT));
	    	System.out.println(Sourcehotelname+"  "+hotelname_MMT);
		    }catch(Exception e) {}
	    	
	    	 }
	    
	    
	    
	    Double score=Collections.max(High_similarityList);
	    System.out.println(score);

	    	int hotelindex=High_similarityList.indexOf(Collections.max(High_similarityList));
	    	try
	    	{
	    	searchname.get(hotelindex).click();
	    	}
	    	catch(StaleElementReferenceException e)
	    	{
		    searchname.get(hotelindex).click();

	    	}
	    	
	    	
	    	 driver.findElement(By.cssSelector("button#hsw_search_button")).click();	
		 	    hotelurl= driver.getCurrentUrl();
		 	    try{
		 	    MMT_hotelname=driver.findElement(By.cssSelector("div.prmProperty>h1")).getText();
		 	    }catch(Exception e) {};
		 	    
		 	   String newsource2=null;
		 	    try
		 	    {
			    String htmlsource=driver.findElement(By.xpath("//div[@id=\"pageLoader\"]//following-sibling::script[1]")).getAttribute("innerHTML");
	            MMT_Address=htmlsource.substring(htmlsource.indexOf("address"), htmlsource.indexOf("checkinTime"));
	   		    String newsource = Pattern.compile("address\":{\"line1\":\"", Pattern.LITERAL | Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE).matcher(MMT_Address).replaceAll(Matcher.quoteReplacement(""));
	   		    String newsource1 = Pattern.compile("\",\"line2\":\"", Pattern.LITERAL | Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE).matcher(newsource).replaceAll(Matcher.quoteReplacement(","));
	   		    newsource2 = Pattern.compile("\"},\"", Pattern.LITERAL | Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE).matcher(newsource1).replaceAll(Matcher.quoteReplacement(","));
		 	    }catch(Exception e) {}
	          
	            WebsiteData.add(MMT_hotelname);
	            WebsiteData.add(newsource2);
	            WebsiteData.add(hotelurl);
	            	WebsiteData.add(" ");
	                WebsiteData.add(" ");
	                WebsiteData.add(" ");
	                WebsiteData.add(" ");
	                WebsiteData.add(" ");
	                WebsiteData.add(" ");
	                WebsiteData.add(" ");
	                WebsiteData.add(" ");
	            
	            
	  ExcelRead_Write.writeExcel("D:\\SelenenumTestData\\MappingInputFile_Makemytrip.xlsx","List",i,WebsiteData);
	  System.out.println("row number"+i);
      
      WebsiteData.remove(MMT_hotelname);
      WebsiteData.remove(newsource2);
      WebsiteData.remove(hotelurl);


	  
		}
		
		else
		{
			High_similarityList.clear();
			WebsiteData.clear();
			searchname_names.clear();
		}
	
	    }
			  
	catch(Exception e)
			    {
			    	e.printStackTrace();
			    }

	 finally
				{
					High_similarityList.clear();
					WebsiteData.clear();
					searchname_names.clear();
				}
	
	
			   
}
		   
	}

}
