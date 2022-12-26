
package bookingOtaVR;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.Normalizer;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import bookingFileOperations.FileFunctions;

public class GoogleExpediaMapping
{
	HashMap<Integer,String> GoogleMap;
	Object[][] Expediadata;
	String BookingHotelname;
	final static int ExpediaID=0;
	final static int Rg_hotelid=1;
	final static int InputhotelnameExcel=3;
	final static int GoogleExcelhotelName=5;
	final static int GoogleExcelhotelAddress=6;
	final static int ExpediaURL1=7;
	final static int ExpediaHotelName1=8;
    final static int ExpediaAddress1=9;
    final static int ExpediaCity1=10;
    final static int ExpediaCountry1=11;

	public static WebDriver driver;
	
   @Test
   public void TestData() throws Exception
   {
	 //System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
     System.setProperty("webdriver.chrome.silentOutput", "true");
     System.setProperty("webdriver.chrome.driver","D:\\Drivers\\chromedriver.exe");
     ChromeOptions options=new ChromeOptions();
		options.setExperimentalOption("useAutomationExtension", false);
		options.setExperimentalOption("excludeSwitches",Collections.singletonList("enable-automation"));
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);
		options.setExperimentalOption("prefs", prefs);
		driver=new ChromeDriver(options);
		driver.manage().window().maximize();
	    Expediadata=FileFunctions.ReadExcelData("D:\\SelenenumTestData\\Automate_data\\MappingInputFile_19July_amarjeet.xlsx","List");  
	   for(int i=1;i<Expediadata.length;i++)
	   {
		   driver.get("https://www.google.com/"); 
		   GoogleMap=new HashMap<Integer,String>();
			
		       try
			   {
		       
		       BookingHotelname =Expediadata[i][InputhotelnameExcel].toString();
		       String UTF_HotelName = new String(BookingHotelname.getBytes("ISO-8859-15"), "UTF-8");
	           Normalizer.normalize(UTF_HotelName, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");  
	           driver.findElement(By.cssSelector("input[title=\"Search\"]")).clear();
			   driver.findElement(By.cssSelector("input[title]")).sendKeys(UTF_HotelName+" ;expedia.com");
			   driver.findElement(By.cssSelector("input[title]")).sendKeys(Keys.ENTER);
			   List<WebElement> AllExpediaLinks_method1=driver.findElements(By.cssSelector("a[href^=\"https://www.expedia\"]"));
			   String ExpediaLink1=AllExpediaLinks_method1.get(0).getAttribute("href");
			   
			   
			   if(ExpediaLink1.contains("Hotel-Information"))
			   {
			   AllExpediaLinks_method1.get(0).click();
			   GoogleMap.put(ExpediaURL1, driver.getCurrentUrl());
		       String ExpediaHotelname=driver.findElement(By.cssSelector("h1[data-selenium=\"hotel-header-name\"]")).getText();
			   GoogleMap.put(ExpediaHotelName1, ExpediaHotelname);
		       String ExpediaHotelAddress=driver.findElement(By.cssSelector("div.HeaderCerebrum__Location>span[data-selenium=\"hotel-address-map\"]")).getText();
			   GoogleMap.put(ExpediaAddress1, ExpediaHotelAddress);
		       String ExpediaHotelcity=driver.findElement(By.cssSelector("div[itemprop=\"address\"]>meta[itemprop=\"name\"]")).getAttribute("content");
		       GoogleMap.put(ExpediaCity1, ExpediaHotelcity);
		       String ExpediaHotelcountry=driver.findElement(By.cssSelector("div[itemprop=\"address\"]>meta[itemprop=\"addressCountry\"]")).getAttribute("content");
		       GoogleMap.put(ExpediaCountry1, ExpediaHotelcountry);
			   }
		
			
			   }
			
			   catch(Exception e)
			   {
				   
			   }
	   
		   
           writeExcel("D:\\SelenenumTestData\\Automate_data\\MappingInputFile_19July_amarjeet.xlsx","List",i);
		
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
		   rows.getCell(ExpediaURL1).setCellValue(GoogleMap.get(ExpediaURL1));
		   rows.getCell(ExpediaHotelName1).setCellValue(GoogleMap.get(ExpediaHotelName1));
		   rows.getCell(ExpediaAddress1).setCellValue(GoogleMap.get(ExpediaAddress1));
		   rows.getCell(ExpediaCity1).setCellValue(GoogleMap.get(ExpediaCity1));
		   rows.getCell(ExpediaCountry1).setCellValue(GoogleMap.get(ExpediaCountry1));

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

	
	


