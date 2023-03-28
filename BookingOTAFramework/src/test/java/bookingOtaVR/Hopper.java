package bookingOtaVR;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.Duration;
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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import bookingFileOperations.FileFunctions;

public class Hopper
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
	final static int Remark=9;
	final static int latlong=10;

	
	public static WebDriver driver;
	
   @Test
   public void TestData() throws Exception
   {
	 //System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
     System.setProperty("webdriver.chrome.silentOutput", "true");
     System.setProperty("webdriver.chrome.driver","D:\\Drivers\\109\\chromedriver.exe");
     ChromeOptions options=new ChromeOptions();
		options.setExperimentalOption("useAutomationExtension", false);
		//options.addArguments("--headless");
		//options.addArguments("incognito");
		options.setExperimentalOption("excludeSwitches",Collections.singletonList("enable-automation"));
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);
		options.setExperimentalOption("prefs", prefs);
		driver=new ChromeDriver(options);
		driver.manage().window().maximize();

		
	
	Object[][] Bookingdata=FileFunctions.ReadExcelData("D:\\SelenenumTestData\\MappingInputFile_Hopper.xlsx","List");  
	   for(int i=1;i<Bookingdata.length;i++)
	   {
		   String url=Bookingdata[i][Hotelurl].toString();
		   driver.get(url);
		   Thread.sleep(15000);
		   
		   BookingMap=new HashMap<Integer,String>();
		     String hotelName_=null;
			String Address_=null;
			String cityname_=null;
		   
         
		   
	

		   try
		   {
			
		   hotelName_=driver.findElement(By.cssSelector("div[data-cy=\"hotel-shop-name\"]")).getText();

		   Address_=driver.findElement(By.cssSelector("div.hotel-shop-info>div:nth-child(2)")).getText();
		   cityname_=driver.findElement(By.cssSelector("div.hotel-star-rating>span+span+span")).getText();
		   BookingMap.put(ws_hotelname, hotelName_);
		   BookingMap.put(ws_address, Address_);
		   BookingMap.put(ws_city, cityname_);
		   System.out.println(hotelName_+Address_+"city"+cityname_);
		   
		   
		   }
		  catch(Exception e) {};
		   
		   writeExcel("D:\\SelenenumTestData\\MappingInputFile_Hopper.xlsx","List",i);
		   System.out.println("rows number----------"+i);
		   BookingMap.clear();
		   
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
		  // rows.getCell(ws_hotelid).setCellValue(BookingMap.get(ws_hotelid));
		   //rows.getCell(ws_airportcode).setCellValue(BookingMap.get(ws_airportcode));
		   rows.getCell(ws_city).setCellValue(BookingMap.get(ws_city));
		   //rows.getCell(ws_country).setCellValue(BookingMap.get(ws_country));
		 //rows.getCell(newurl).setCellValue(BookingMap.get(newurl));
		   //rows.getCell(Combinedcode).setCellValue(BookingMap.get(Combinedcode));
		   //rows.getCell(Remark).setCellValue(BookingMap.get(Remark));
		  // rows.getCell(latlong).setCellValue(BookingMap.get(latlong));
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

	
	


