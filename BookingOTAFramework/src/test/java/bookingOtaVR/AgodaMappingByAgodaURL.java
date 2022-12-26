
package bookingOtaVR;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.Normalizer;
import java.time.Duration;
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
import org.openqa.selenium.Keys;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import bookingFileOperations.FileFunctions;

public class AgodaMappingByAgodaURL
{
	HashMap<Integer,String> GoogleMap;
	Object[][] Agodadata;
	String BookingHotelname;
	final static int SubscriberHotelID=0;
	final static int Rg_hotelid=1;
	final static int InputhotelnameExcel=3;
	final static int GoogleAGodaURL1=5;
	final static int GoogleAgodaHotelName1=6;
	final static int GoogleAgodahotelAddress1=7;
	final static int GoogleAgodaHotelid1=8;
    final static int GoogleAgodaCityname1=9;
    final static int GoogleAgodaCountryname1=10;

	public static WebDriver driver;
	
   @Test
   public void TestData() throws Exception
   {
	 //System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
     System.setProperty("webdriver.chrome.silentOutput", "true");
     System.setProperty("webdriver.chrome.driver","D:\\Drivers\\chromedriver.exe");
     

	   driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
     
     /*ChromeOptions options=new ChromeOptions();
		options.setExperimentalOption("useAutomationExtension", false);
		options.setExperimentalOption("excludeSwitches",Collections.singletonList("enable-automation"));
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);
		options.setExperimentalOption("prefs", prefs);
		driver=new ChromeDriver(options);*/
		
		
		driver.manage().window().maximize();
	    Agodadata=FileFunctions.ReadExcelData("C:\\Users\\RG\\Desktop\\Selenium\\AgodaMappingInputFilelink.xlsx","List");  
	   for(int i=1;i<Agodadata.length;i++)
	   {

		   GoogleMap=new HashMap<Integer,String>();
		   driver.get("https://www.google.com/");

			   try
			   {
				   
			 String BookingHotelname =Agodadata[i][InputhotelnameExcel].toString();
		     String UTF_HotelName = new String(BookingHotelname.getBytes("ISO-8859-15"), "UTF-8");
		     Normalizer.normalize(UTF_HotelName, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
		      driver.findElement(By.cssSelector("input[title]")).sendKeys(UTF_HotelName+" ; agoda.com");
		      driver.findElement(By.cssSelector("input[title]")).sendKeys(Keys.ENTER);
		      List<WebElement> AllExpediaLinks_method1=driver.findElements(By.cssSelector("a[href^=\"https://www.agoda\"]"));
			  AllExpediaLinks_method1.get(AllExpediaLinks_method1.size()-1).click();
			  String AgodaonlineURL=driver.getCurrentUrl();
			   GoogleMap.put(GoogleAGodaURL1, AgodaonlineURL);
			   
			   WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10));
		       wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1[data-selenium=\"hotel-header-name\"]")));
		       
		       String AgodaHotelname=driver.findElement(By.cssSelector("h1[data-selenium=\"hotel-header-name\"]")).getText();
			   GoogleMap.put(GoogleAgodaHotelName1, AgodaHotelname);
		       String AgodaHotelAddress=driver.findElement(By.cssSelector("div.HeaderCerebrum__Location>span[data-selenium=\"hotel-address-map\"]")).getText();
			   GoogleMap.put(GoogleAgodahotelAddress1, AgodaHotelAddress);
			   String AgodaHotelId=driver.findElement(By.cssSelector("div.MapCompact")).getAttribute("data-provider-id");
			   GoogleMap.put(GoogleAgodaHotelid1, AgodaHotelId);
		       String AgodaHotelcity=driver.findElement(By.cssSelector("meta[property=\"og:country-name\"]")).getAttribute("content");
		       GoogleMap.put(GoogleAgodaCityname1, AgodaHotelcity);
		       String AgodaHotelcountry=driver.findElement(By.cssSelector("meta[property=\"og:locality\"]")).getAttribute("content");
		       GoogleMap.put(GoogleAgodaCountryname1, AgodaHotelcountry);
			   }
			   catch(Exception e)
			   {
				   
			   }
			 
		   
           writeExcel("C:\\Users\\RG\\Desktop\\Selenium\\AgodaMappingInputFilelink.xlsx","List",i);
		
	   }
   }
   
	
	public void writeExcel(String path,String sheet,int excelrownumber) throws Exception	
	{
	
		 File file=new File(path);
		 FileInputStream fis=new FileInputStream(file);
		 String fileType=file.getName().substring(file.getName().indexOf(".")+1);
		 Workbook Excelworkbook=null;

		   if (fileType.equalsIgnoreCase("xlsx"))
		   {
			   Excelworkbook=new XSSFWorkbook(fis);
			   
		   }
			
		   else if(fileType.equalsIgnoreCase("xls"))
		   {
			   Excelworkbook=new HSSFWorkbook(fis);
		   }
		   else
		   {
			   System.out.println("File has different file format");
		   }
		   
		   Sheet BoongSheet=Excelworkbook.getSheet(sheet);
		   Row rows=BoongSheet.getRow(excelrownumber);
		   rows.getCell(GoogleAGodaURL1).setCellValue(GoogleMap.get(GoogleAGodaURL1));
		   rows.getCell(GoogleAgodaHotelName1).setCellValue(GoogleMap.get(GoogleAgodaHotelName1));
		   rows.getCell(GoogleAgodahotelAddress1).setCellValue(GoogleMap.get(GoogleAgodahotelAddress1));
		   rows.getCell(GoogleAgodaHotelid1).setCellValue(GoogleMap.get(GoogleAgodaHotelid1));
		   rows.getCell(GoogleAgodaCityname1).setCellValue(GoogleMap.get(GoogleAgodaCityname1));
		   rows.getCell(GoogleAgodaCountryname1).setCellValue(GoogleMap.get(GoogleAgodaCountryname1));


     	   fis.close();
		   FileOutputStream fos=new FileOutputStream(file);
		   Excelworkbook.write(fos);
		   fos.close();
		   
	}
	
	  @AfterTest
		public void closeBrowser()
		{
			driver.close();
		}
	
	}

	
	


