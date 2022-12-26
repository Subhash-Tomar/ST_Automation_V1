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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import bookingFileOperations.FileFunctions;

public class BookingVR
{
	HashMap<Integer,String> BookingMap;
	final static int ExpediaID=0;
	final static int Rg_hotelid=1;
	final static int BookingID=7;
	final static int ws_hotelname=8;
	final static int ws_address=9;
	final static int ws_hotelid=10;
	final static int ws_city=11;
	final static int ws_country=12;
	final static int HotelType=13;
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
	
	Object[][] Bookingdata=FileFunctions.ReadExcelData("D:\\SelenenumTestData.xlsx","List");  
	   for(int i=1;i<Bookingdata.length;i++)
	   {
		   BookingMap=new HashMap<Integer,String>();
		   String url=Bookingdata[i][BookingID].toString();
		   String websiteUrl="https://www.booking.com/hotel"+url+".html?";
		   driver.get(websiteUrl);
		   driver.manage().window().maximize();
		   String currenturl=driver.getCurrentUrl();
		  if (currenturl.contains("www.booking.com/searchresults"))
				  {
				    BookingMap.put(ws_hotelid,"Hotel is De-listed(www.booking.com/searchresults)");
			       
				  }
		
		  else
		  {
		   FileFunctions.locatorpath="src//main//resources//BookingOta.properties";
		   
   try {
		   WebElement element = driver.findElement(By.xpath("//*[@id=\"b2hotelPage\"]/script[24]"));
	       String htmlsource=element.getAttribute("outerHTML").toString();
	       if(htmlsource.contains("isVacationRental: '1'"))
	       {
			BookingMap.put(HotelType,"Vacation Rental");
	       }
   else if(htmlsource.contains("isVacationRental: ''"))
	       {
	     BookingMap.put(HotelType,"Hotel");

	       }
	else
	       {
    BookingMap.put(HotelType,"Not or VR not Hotel");
           }
		   }
	catch(Exception e)
		   {
			BookingMap.put(HotelType,"Hotel is De-listed");

		   }
			   
		   try
		   {
		   BookingMap.put(ws_hotelid,driver.findElement(By.cssSelector(FileFunctions.getLocatorFromElementFile("hotelid"))).getAttribute("value"));
		   }
		   catch(Exception e)
		   {
			 BookingMap.put(ws_hotelid,"NULL");

		   }

		   writeExcel("D:\\SelenenumTestData.xlsx","List",i);
		   
		  }
	   
	   
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
		   rows.getCell(ws_hotelid).setCellValue(BookingMap.get(ws_hotelid));
		   rows.getCell(HotelType).setCellValue(BookingMap.get(HotelType));


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

	
	


