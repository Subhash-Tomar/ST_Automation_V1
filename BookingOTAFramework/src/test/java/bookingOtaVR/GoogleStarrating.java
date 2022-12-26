


package bookingOtaVR;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.Normalizer;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import bookingFileOperations.FileFunctions;

public class GoogleStarrating
{
	
	Object[][] Bookingdata;
	HashMap<Integer,String> GoogleMap;
	final static int ExpediaID=0;
	final static int Rg_hotelid=1;
	final static int hotelnameExcel=3;
	final static int GoogleExcelhotelName=5;
	final static int GoogleExcelhotelAddress=6;
	final static int BookingGoogleUrl1=7;
	final static int GoogleStarrating=8;

   WebDriver driver;
	
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
	    Bookingdata=FileFunctions.ReadExcelData("D:\\Selenium_OTAData\\MappingInputFile.xlsx","List");  
	   for(int i=1;i<Bookingdata.length;i++)
	   {
			GoogleMap=new HashMap<Integer,String>();
			driver.get("https://www.google.com/");
	   
		   try
		   {

		   String BookingHotelname =Bookingdata[i][hotelnameExcel].toString();
           String UTF_HotelName = new String(BookingHotelname.getBytes("ISO-8859-15"), "UTF-8");
           Normalizer.normalize(UTF_HotelName, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
           driver.findElement(By.cssSelector("input[title]")).sendKeys(UTF_HotelName);
           driver.findElement(By.cssSelector("input[title]")).sendKeys(Keys.ENTER);
		   String GoogleHotelname=driver.findElement(By.cssSelector("h2[data-attrid=\"title\"]>span")).getText();
		   GoogleMap.put(GoogleExcelhotelName, GoogleHotelname);
		   String GooglehotelAddress=driver.findElement(By.cssSelector("div[data-md=\"1002\"]>div>div>span:nth-child(2)")).getText();
		   GoogleMap.put(GoogleExcelhotelAddress, GooglehotelAddress);
		   
		   String GoogleStarra=driver.findElement(By.cssSelector("span.YhemCb>span")).getText();
		   GoogleMap.put(GoogleStarrating, GoogleStarra);
		   
		   
		   driver.findElement(By.cssSelector("span.x2KSic")).click();
		   

		   }
		   
		   catch (Exception e)
		   {
			   System.out.println(e.getMessage());
		   }
		   
           writeExcel("D:\\Selenium_OTAData\\MappingInputFile.xlsx","List",i);
		
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
		   rows.getCell(GoogleExcelhotelName).setCellValue(GoogleMap.get(GoogleExcelhotelName));
		   rows.getCell(GoogleExcelhotelAddress).setCellValue(GoogleMap.get(GoogleExcelhotelAddress));
		   rows.getCell(GoogleStarrating).setCellValue(GoogleMap.get(GoogleStarrating));
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

	
	


