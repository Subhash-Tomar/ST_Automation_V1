


package bookingOtaVR;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import bookingFileOperations.FileFunctions;

public class Agoda_cities_link
{
	HashMap<Integer,String> BookingMap;
	
	final static int Hotelurl=0;
	final static int ws_hotelname=1;
	final static int ws_address=2;
	final static int ws_hotelid=3;
	final static int ws_airportcode=4;
    final static int ws_city=5;
	final static int ws_country=6;
	final static int Combinedcode=7;
	final static int newurl=8;
	final static int starrating=9;

	
	public static WebDriver driver;
	
   @Test
   public void TestData() throws Exception
   {
	 //System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
     System.setProperty("webdriver.chrome.silentOutput", "true");
     System.setProperty("webdriver.chrome.driver","D:\\Drivers\\driver\\chromedriver.exe");
     ChromeOptions options=new ChromeOptions();
		options.setExperimentalOption("useAutomationExtension", false);
		options.setExperimentalOption("excludeSwitches",Collections.singletonList("enable-automation"));
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);
		options.setExperimentalOption("prefs", prefs);
		driver=new ChromeDriver(options);
		driver.manage().window().maximize();
		List<String> citieslinks=new ArrayList<String>();
		driver.get("https://sgp.agoda.com/en-in/region/queensland-state-au.html");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		List<WebElement> alllinks=driver.findElements(By.cssSelector("a[data-selenium=\"neighbor-link\"]"));
		for(int k=0;k<alllinks.size();k++)
		{
			String cities=alllinks.get(k).getAttribute("href");
			citieslinks.add(cities);
			
		}
		for(int i=0;i<citieslinks.size();i++)
		{
		
			try
			{
		driver.get(citieslinks.get(i));
			}catch(Exception e) {};
		//driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		try
		{
		List<WebElement> alllinks2=driver.findElements(By.cssSelector("a[data-selenium=\"neighbor-link\"]"));
		
		for(int j=0;j<alllinks2.size();j++)
		{
		System.out.println(alllinks2.get(j).getAttribute("href"));
		}
		}catch(Exception e) {};
		
		
		}
	
	
   }
}