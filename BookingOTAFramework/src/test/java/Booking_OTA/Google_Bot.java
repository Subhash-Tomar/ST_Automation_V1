package Booking_OTA;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.Normalizer;
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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import bookingFileOperations.FileFunctions;

public class Google_Bot
{
	
	Object[][] ExcelData;
	HashMap<Integer,String> GoogleMap;
	final static int hotelnameExcel=0;
	final static int addressExcel=1;
	final static int cityExcel=2;
	final static int CountryExcel=3;
	final static int GoogleExcelhotelName=4;
	final static int GoogleExcelhotelAddress=5;
	final static int GoogleHotelid=6;

   WebDriver driver;
	
   @Test
   public void TestData() throws Exception
   {
	 //System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
     System.setProperty("webdriver.chrome.silentOutput", "true");
     System.setProperty("webdriver.chrome.driver","D:\\Drivers\\100\\chromedriver.exe");
     ChromeOptions options=new ChromeOptions();
		options.setExperimentalOption("useAutomationExtension", false);
		options.setExperimentalOption("excludeSwitches",Collections.singletonList("enable-automation"));
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);
		options.setExperimentalOption("prefs", prefs);
		driver=new ChromeDriver(options);
		driver.manage().window().maximize();
	    ExcelData=FileFunctions.ReadExcelData("D:\\SelenenumTestData\\Subhash_automation_file\\Mapping_19_aug1.xlsx","List");  
	    int j=0;
	   String UTF_HotelName;
	   for(int i=1;i<ExcelData.length;i++)
	   {
		   UTF_HotelName="";
			GoogleMap=new HashMap<Integer,String>();
			driver.get("https://www.google.com/");
			
			if(j==6)
			{
				driver.get("https://www.youtube.com/");
				Thread.sleep(4000);
				j=0;


			}
			
			else {
	   
		   try
		   {

			Thread.sleep(4000);

		   String Hotelname =ExcelData[i][hotelnameExcel].toString();
		   String Address =ExcelData[i][addressExcel].toString();
		   String city =ExcelData[i][cityExcel].toString();
		   String country =ExcelData[i][CountryExcel].toString();

		   String InputParameter1=Hotelname+","+Address+","+city+","+country;
		   String InputParameter2=Hotelname+","+city+","+country;
		   String InputParameter4=Hotelname;
		   String GoogleHotelname="";
		   String GooglehotelAddress="";
		   String webGoogleHotelid="";
           try
           {
        	   
           UTF_HotelName = new String(InputParameter1.getBytes("ISO-8859-15"), "UTF-8");
           Normalizer.normalize(UTF_HotelName, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
           driver.findElement(By.cssSelector("input[title]")).sendKeys(UTF_HotelName);
           driver.findElement(By.cssSelector("input[title]")).sendKeys(Keys.ENTER);
		   GoogleHotelname=driver.findElement(By.cssSelector("h2[data-attrid=\"title\"]>span")).getText();
		   GoogleMap.put(GoogleExcelhotelName, GoogleHotelname);
           }catch(Exception e) {};
           
          /* try
           {
     		 if(GoogleMap.get(GoogleExcelhotelName)==""||GoogleMap.get(GoogleExcelhotelName).contains("See results"))

		   {
     			driver.get("https://www.google.com/");
			   UTF_HotelName = new String(InputParameter1.getBytes("ISO-8859-15"), "UTF-8");
	           Normalizer.normalize(UTF_HotelName, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	           driver.findElement(By.cssSelector("input[title]")).sendKeys(UTF_HotelName);
	           driver.findElement(By.cssSelector("input[title]")).sendKeys(Keys.ENTER);
			   GoogleHotelname=driver.findElement(By.cssSelector("h2[data-attrid=\"title\"]>span")).getText();
			   GoogleMap.put(GoogleExcelhotelName, GoogleHotelname);
		   }
		   
           }catch(Exception e) {};*/
           
           
		   try
		   {
			 
		   driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);   
		   GooglehotelAddress=driver.findElement(By.xpath("//a[text()='Address']//parent::span//following-sibling::span")).getText();
		   GoogleMap.put(GoogleExcelhotelAddress, GooglehotelAddress);
		   }catch(Exception e) {};
		   try
		   {
			 
			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		   
			   
			 webGoogleHotelid =driver.findElement(By.xpath("//a[text()='Check availability']")).getAttribute("href");
		   GoogleMap.put(GoogleHotelid, webGoogleHotelid);
		   } catch(Exception e) {};
		   
		  j++;
		   } 
		   
		   catch (Exception e)
		   {
			   System.out.println(e.getMessage());
		   }
		   
           writeExcel("D:\\SelenenumTestData\\Subhash_automation_file\\Mapping_19_aug1.xlsx","List",i);
		
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
		   rows.getCell(GoogleExcelhotelName).setCellValue(GoogleMap.get(GoogleExcelhotelName));
		   rows.getCell(GoogleExcelhotelAddress).setCellValue(GoogleMap.get(GoogleExcelhotelAddress));
		   rows.getCell(GoogleHotelid).setCellValue(GoogleMap.get(GoogleHotelid));
		   //rows.getCell(ExpediaGoogleUrl1).setCellValue(GoogleMap.get(ExpediaGoogleUrl1));
		   fis.close();
		   FileOutputStream fos=new FileOutputStream(file);
		   Excelworkbook.write(fos);
		   fos.close();
		   GoogleMap.clear();
		   
		   
	}
	
	

	
	}

	
	


