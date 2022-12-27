package bookingOtaVR;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import bookingFileOperations.FileFunctions;

public class BookingVR2
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

	
	public static WebDriver driver;
	
   @Test
   public void TestData() throws Exception
   {
	 //System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
     System.setProperty("webdriver.chrome.silentOutput", "true");
     System.setProperty("webdriver.chrome.driver","D:\\Drivers\\108\\chromedriver.exe");
     ChromeOptions options=new ChromeOptions();
		options.setExperimentalOption("useAutomationExtension", false);
		options.setExperimentalOption("excludeSwitches",Collections.singletonList("enable-automation"));
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);
		options.setExperimentalOption("prefs", prefs);
		driver=new ChromeDriver(options);
	
	Object[][] Bookingdata=FileFunctions.ReadExcelData("D:\\SelenenumTestData\\MappingInputFile_Booking.xlsx","List");  
	   for(int i=1;i<Bookingdata.length;i++)
	   {
		   Thread.sleep(1000);
		   BookingMap=new HashMap<Integer,String>();
		   String url=Bookingdata[i][Hotelurl].toString();
		   driver.get(url);
		   driver.manage().window().maximize();
		   String currenturl=driver.getCurrentUrl();
		   
		
		   if (currenturl.contains("www.booking.com/searchresults"))
				  {
				    BookingMap.put(Remark,"Hotel is De-listed(www.booking.com/searchresults)");
			       
				  }
		   
		   else
			   
		   {

		       BookingMap.put(newurl, currenturl);
             try
             {
	   
		   FileFunctions.locatorpath="src//main//resources//BookingOta.properties";
		   String htmlsource="";
		   try
		   {
		   WebElement element = driver.findElement(By.cssSelector("#b2hotelPage > script:nth-child(2)"));
		   htmlsource=element.getAttribute("innerHTML");
		   String htmlCode=htmlsource.substring(600,1500);
		      String CombinedCode=htmlCode.substring(htmlCode.indexOf("ufi"), htmlCode.indexOf("hotel_id_list"));
		      BookingMap.put(Combinedcode, CombinedCode);
		   }catch(Exception e) {}
		   
		   try
		   {
		   WebElement element = driver.findElement(By.cssSelector("#b2hotelPage > script:nth-child(3)"));
		   htmlsource=element.getAttribute("innerHTML");
		   String htmlCode=htmlsource.substring(600,1500);
		   String CombinedCode=htmlCode.substring(htmlCode.indexOf("ufi"), htmlCode.indexOf("hotel_id_list"));
		   BookingMap.put(Combinedcode, CombinedCode);
		   
		   }catch(Exception e) {}
		   
		   String Bookinghotelname="";
		   try
	       {
	       Bookinghotelname=driver.findElement(By.cssSelector("div#hp_hotel_name>div>div>h2")).getText();
	       
	       }catch(Exception e) {};
	       
	       try
	       {
	       if (Bookinghotelname=="")
	       {
	    	   Bookinghotelname=driver.findElement(By.cssSelector("h2#hp_hotel_name")).getText();
	       }
	       }catch(Exception e) {};
	       
	       BookingMap.put(ws_hotelname, Bookinghotelname);
	       String BookingAddress=driver.findElement(By.cssSelector("#showMap2 > span.hp_address_subtitle.js-hp_address_subtitle.jq_tooltip")).getText();
	       BookingMap.put(ws_address, BookingAddress);
	       
	       System.out.println(Bookinghotelname+"........"+BookingAddress);
	
	       try
	       {
	       String BookingHotelid=driver.findElement(By.cssSelector("button#wl--wl_entrypoint_hp_head")).getAttribute("data-hotel-id");
	       BookingMap.put(ws_hotelid, BookingHotelid);
	       String BookingAirportcode1=driver.findElement(By.cssSelector("div#hp_availability_style_changes>form>div.hiddenInputSection>input[name=\"dest_id\"]")).getAttribute("value");
	       BookingMap.put(ws_airportcode, BookingAirportcode1);
	       
		

	       } catch(Exception e) {}
	      
			  
	       String Remark_="";
		   try
	       {
			   Remark_=driver.findElement(By.cssSelector("div#wrap-hotelpage-top>div>p")).getText();
		       BookingMap.put(Remark, Remark_);

	       }catch(Exception e) {};
	     
             }
             catch(Exception e) {}
		   }
		   
		   
		  
	 
		   writeExcel("D:\\SelenenumTestData\\MappingInputFile_Booking.xlsx","List",i);
		   System.out.println("rows number----------"+i);
		   
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
		   rows.getCell(ws_airportcode).setCellValue(BookingMap.get(ws_airportcode));
		   rows.getCell(newurl).setCellValue(BookingMap.get(newurl));
		   rows.getCell(Combinedcode).setCellValue(BookingMap.get(Combinedcode));
		   rows.getCell(Remark).setCellValue(BookingMap.get(Remark));

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

	
	


