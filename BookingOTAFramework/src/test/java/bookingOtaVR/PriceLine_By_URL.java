
package bookingOtaVR;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.Duration;
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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import bookingFileOperations.FileFunctions;

public class PriceLine_By_URL


{
	HashMap<Integer,String> BookingMap;
	
	final static int Hotelurl=0;
	final static int ws_hotelname=1;
	final static int ws_address=2;
	final static int ws_hotelid=3;
	final static int ws_airportcode=4;
    final static int ws_city=5;
	final static int ws_country=6;
	final static int hotelurl=7;
	
	
	
	
	public static WebDriver driver;
	
   @Test
   public void TestData() throws Exception
   {
	 //System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
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
	
	Object[][] Bookingdata=FileFunctions.ReadExcelData("D:\\SelenenumTestData\\MappingInputFile_priceline.xlsx","List");  
	   for(int i=1;i<Bookingdata.length;i++)
	   {
		   Thread.sleep(40000);
		   try
		   {
		   BookingMap=new HashMap<Integer,String>();
		   String url=Bookingdata[i][Hotelurl].toString();	
		   driver.get(url);
		  
		   WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10));
	       wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[data-testid=\"Name_Area\"]>div>div+div>h1")));
	       
	       String AgodaHotelname=driver.findElement(By.cssSelector("div[data-testid=\"Name_Area\"]>div>div+div>h1")).getText();
	       BookingMap.put(ws_hotelname, AgodaHotelname);
	       String AgodaHotelAddress=driver.findElement(By.cssSelector("div[data-testid=\"Address\"]>div>div>div>div")).getText();
	      BookingMap.put(ws_address, AgodaHotelAddress);
		   //String AgodaHotelId=driver.findElement(By.cssSelector("div.MapCompact")).getAttribute("data-provider-id");
		   //BookingMap.put(ws_hotelid, AgodaHotelId);
	       //String AgodaHotelcity=driver.findElement(By.cssSelector("meta[property=\"og:country-name\"]")).getAttribute("content");
	       //BookingMap.put(ws_city, AgodaHotelcity);
	       //String AgodaHotelcountry=driver.findElement(By.cssSelector("meta[property=\"og:locality\"]")).getAttribute("content");
	       BookingMap.put(hotelurl, driver.getCurrentUrl());
		   
		   
		   
		   }
		   
		   catch(Exception e)
		   {
			   System.out.println(e.getMessage());
		   }
		   
		   writeExcel("D:\\SelenenumTestData\\MappingInputFile_priceline.xlsx","List",i);
		   System.out.println("rows number:"+i);

		   
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
		   //rows.getCell(ws_hotelid).setCellValue(BookingMap.get(ws_hotelid));
		   rows.getCell(hotelurl).setCellValue(BookingMap.get(hotelurl));
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

	
	


