
package bookingOtaVR;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import bookingFileOperations.FileFunctions;

public class ExpediaOTA_PGH


{
	HashMap<Integer,String> BookingMap;
	final static int ExpediaID=0;
	final static int ws_hotelname=1;
	final static int ws_address=2;
	final static int ws_hotelid=3;
	final static int ws_airportcode=4;
	final static int ws_city=5;
	final static int ws_country=6;
	
    
	
	
	public static WebDriver driver;
	
   @Test
   public void TestData() throws Exception
   {
	 //System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
     System.setProperty("webdriver.chrome.silentOutput", "true");
     System.setProperty("webdriver.chrome.driver","D:\\Drivers\\109\\chromedriver.exe");
     ChromeOptions options=new ChromeOptions();
		options.setExperimentalOption("useAutomationExtension", false);
		options.setExperimentalOption("excludeSwitches",Collections.singletonList("enable-automation"));
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);
		options.setExperimentalOption("prefs", prefs);
		driver=new ChromeDriver(options);
		 int j=0;
	Object[][] Bookingdata=FileFunctions.ReadExcelData("D:\\SelenenumTestData\\MappingInputFile_GoogleAll.xlsx","List");  
	   for(int i=1;i<Bookingdata.length;i++)
	   {
			Thread.sleep(30000);

		   try
		   {
			   
			   
			   if(j==6)
				{
					driver.get("https://www.youtube.com/");
					Thread.sleep(4000);
					j=0;


				}
	   
		   BookingMap=new HashMap<Integer,String>();
		   String url=Bookingdata[i][ExpediaID].toString();
		   //String websiteUrl="https://www.expedia.com/.h"+url+".Hotel-Information?";
		   String websiteUrl=url;
		   driver.get(websiteUrl);
		   driver.manage().window().maximize();	
		   String ExpediaHotelname=driver.findElement(By.cssSelector("div[data-stid=\"content-hotel-title\"]>h1")).getText();
		   BookingMap.put(ws_hotelname, ExpediaHotelname);
	       String ExpediaHotelAddress=driver.findElement(By.cssSelector("div[data-stid=\"content-hotel-address\"]")).getText();
		   BookingMap.put(ws_address, ExpediaHotelAddress);
           String currenturl=driver.getCurrentUrl();
		   BookingMap.put(ws_hotelid, currenturl);
           //String ExpediaHotelRegionID=driver.findElement(By.cssSelector("a[id=\"his__back-button\"]")).getAttribute("href");
		   //BookingMap.put(ws_airportcode, ExpediaHotelRegionID);
		   
		   //String ExpediaHotelcity=driver.findElement(By.cssSelector("div[itemprop=\"address\"]>meta[itemprop=\"name\"]")).getAttribute("content");
		   //BookingMap.put(ws_city, ExpediaHotelcity);
		   
		  // String ExpediaHotelcountry=driver.findElement(By.cssSelector("div[itemprop=\"address\"]>meta[itemprop=\"addressCountry\"]")).getAttribute("content");
		   //BookingMap.put(ws_country, ExpediaHotelcountry);
		   j++;
		   }
		  
		   catch(Exception e)
		   {
			   
		   }
		   writeExcel("D:\\SelenenumTestData\\MappingInputFile_GoogleAll.xlsx","List",i);
		   
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
		   rows.getCell(ws_hotelname).setCellValue(BookingMap.get(ws_hotelname));
		   rows.getCell(ws_address).setCellValue(BookingMap.get(ws_address));
		   rows.getCell(ws_hotelid).setCellValue(BookingMap.get(ws_hotelid));
		   //.getCell(ws_airportcode).setCellValue(BookingMap.get(ws_airportcode));
		   //rows.getCell(ws_city).setCellValue(BookingMap.get(ws_city));
		   //rows.getCell(ws_country).setCellValue(BookingMap.get(ws_country));
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

	
	


