package bookingOtaVR;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.Normalizer;
import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
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

public class Google_Expedia_Booking_Makemytrip
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
	final static int Expediaurl=7;
	final static int BookingUrl=8;
	final static int Makemytripurl=9;

	

   WebDriver driver;
	
   @Test
   public void TestData() throws Exception
   {
	 //System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
     System.setProperty("webdriver.chrome.silentOutput", "true");
     System.setProperty("webdriver.chrome.driver","D:\\Drivers\\110\\chromedriver.exe");
     ChromeOptions options=new ChromeOptions();
		options.setExperimentalOption("useAutomationExtension", false);
		options.setExperimentalOption("excludeSwitches",Collections.singletonList("enable-automation"));
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);
		options.setExperimentalOption("prefs", prefs);
		driver=new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.get("https://www.google.com/");
        driver.findElement(By.cssSelector("input[title]")).sendKeys("park plaza hotel");
        driver.findElement(By.cssSelector("input[title]")).sendKeys(Keys.ENTER);

	    ExcelData=FileFunctions.ReadExcelData("D:\\SelenenumTestData\\MappingInputFile_GoogleAll.xlsx","List");  
	    int j=0;
	   String UTF_HotelName;
	   for(int i=1;i<ExcelData.length;i++)
	   {
		   UTF_HotelName="";
			GoogleMap=new HashMap<Integer,String>();
			
			if(j==21)
			{
				driver.get("https://www.youtube.com/");
				Thread.sleep(4000);
				j=0;


				driver.get("https://www.google.com/");
		        driver.findElement(By.cssSelector("input[title]")).sendKeys("park plaza hotel");
		        driver.findElement(By.cssSelector("input[title]")).sendKeys(Keys.ENTER);


			}
			
			else {
	   
		   try
		   {

			Thread.sleep(4000);

		   String Hotelname =ExcelData[i][hotelnameExcel].toString();
		   String Address =ExcelData[i][addressExcel].toString();
		   String city =ExcelData[i][cityExcel].toString();
		   String country =ExcelData[i][CountryExcel].toString();

		   String InputParameter1=Hotelname+","+Address.substring(0,6)+","+city+","+country;
		   String InputParameter2=Hotelname+","+city+","+country;
		   String GoogleHotelname="";   
           UTF_HotelName = new String(InputParameter1.getBytes("ISO-8859-15"), "UTF-8");
           Normalizer.normalize(UTF_HotelName, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
           
           driver.findElement(By.cssSelector("div[jsname=\"gLFyf\"]>input")).sendKeys(Keys.CONTROL+"a");
           driver.findElement(By.cssSelector("div[jsname=\"gLFyf\"]>input")).sendKeys(Keys.DELETE);

           driver.findElement(By.cssSelector("div[jsname=\"gLFyf\"]>input")).sendKeys(UTF_HotelName);
           driver.findElement(By.cssSelector("button[jsname=\"Tg7LZd\"]>div>span>svg")).click();
           
           
           try
           {
		   GoogleHotelname=driver.findElement(By.cssSelector("h2[data-attrid=\"title\"]>span")).getText();
		   GoogleMap.put(GoogleExcelhotelName, GoogleHotelname);
		  // System.out.println("......."+GoogleMap.get(GoogleExcelhotelName));
		   
		  /* if(GoogleMap.get(GoogleExcelhotelName)==""||GoogleMap.get(GoogleExcelhotelName).contains("See results"))
		   {
			   driver.get("https://www.google.com/");
			   UTF_HotelName = new String(InputParameter2.getBytes("ISO-8859-15"), "UTF-8");
	           Normalizer.normalize(UTF_HotelName, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	           driver.findElement(By.cssSelector("input[title]")).sendKeys(UTF_HotelName);
	           driver.findElement(By.cssSelector("input[title]")).sendKeys(Keys.ENTER);
			   GoogleHotelname=driver.findElement(By.cssSelector("h2[data-attrid=\"title\"]>span")).getText();
			   GoogleMap.put(GoogleExcelhotelName, GoogleHotelname);
		   }*/
		   
           
		   try
		   {
			   List<WebElement> Addr=driver.findElements(By.xpath("//a[text()='Address']//parent::span//following-sibling::span"));
			 
		     String GooglehotelAddress=Addr.get(1).getText();

		 //driver.findElement(By.xpath("//a[text()='Address']//parent::span//following-sibling::span")).getText();
		   GoogleMap.put(GoogleExcelhotelAddress, GooglehotelAddress);
		   }catch(Exception e) {};
		   
		   
		   
		   try
		   {
			 
			   driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));   
		   
			   
		  String webGoogleHotelid =driver.findElement(By.xpath("//a[text()='Check availability']")).getAttribute("href");
		   GoogleMap.put(GoogleHotelid, webGoogleHotelid);
		   } catch(Exception e) {};
		   
		   try
		   {
			   List<WebElement> webexpediaurls =driver.findElements(By.cssSelector("a[href^=\"https://www.booking\"]"));
			   String webexpediaurl=webexpediaurls.get(0).getAttribute("href");
			   GoogleMap.put(BookingUrl, webexpediaurl);
			   
		   }  catch (Exception e){ }
		   
		   try
		   {
			   List<WebElement> webexpediaurls =driver.findElements(By.cssSelector("a[href^=\"https://www.expedia\"]"));
			   String webexpediaurl=webexpediaurls.get(0).getAttribute("href");
			   GoogleMap.put(Expediaurl, webexpediaurl);
			   
		   }  catch (Exception e){ }
		   
		   try
		   {
			   String webexpediaurl="";
			   List<WebElement> webexpediaurls =driver.findElements(By.cssSelector("a[href^=\"https://www.makemytrip.com\"]"));
			   Iterator<WebElement> weburls=webexpediaurls.iterator();
			
			   webexpediaurl=weburls.next().getAttribute("href");
			  
			   
			   GoogleMap.put(Makemytripurl, webexpediaurl);
			   
		   }  catch (Exception e){ }
		   
		   
           }catch (Exception e) {}   
           
           
		  j++;
		   }
		   
		   catch (Exception e)
		   {
			   System.out.println(e.getMessage());
		   }
		   
           writeExcel("D:\\SelenenumTestData\\MappingInputFile_GoogleAll.xlsx","List",i);
           System.out.println("row number"+i);
		
	   }
	   }
   }
   
   
   
	public void writeExcel(String path,String sheet,int excelrownumber) throws Exception	
	{
		FileOutputStream fos=null;
		 FileInputStream fis=null;
	
		try
		{
		 File file=new File(path);
		 fis=new FileInputStream(file);
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
		   rows.getCell(BookingUrl).setCellValue(GoogleMap.get(BookingUrl));
		   rows.getCell(Expediaurl).setCellValue(GoogleMap.get(Expediaurl));
		   rows.getCell(Makemytripurl).setCellValue(GoogleMap.get(Makemytripurl));
		   
		   //rows.getCell(ExpediaGoogleUrl1).setCellValue(GoogleMap.get(ExpediaGoogleUrl1));
		   
		   fos=new FileOutputStream(file);
		   Excelworkbook.write(fos);
		}
		catch(Exception e)
		{}
		
		finally
		{
		   fis.close();
		   fos.close();
		}
		   
	}
	
	

	
	}
