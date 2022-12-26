

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
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import bookingFileOperations.FileFunctions;

public class Ctrip_Citywise
{
	
	Object[][] Bookingdata;
	HashMap<Integer,String> GoogleMap;
	final static int SubscriberHotelID=0;
	final static int Hotelurl=1;

   WebDriver driver;
	
   @Test
   public void TestData() throws Exception
   {
	
	  System.setProperty("webdriver.chrome.silentOutput", "true");
	  System.setProperty("webdriver.chrome.driver","D:\\Drivers\\chromedriver.exe");
	  //System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");  
        ChromeOptions options=new ChromeOptions();
		options.setExperimentalOption("useAutomationExtension", false);
		options.setExperimentalOption("excludeSwitches",Collections.singletonList("enable-automation"));
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);
		options.setExperimentalOption("prefs", prefs);
		driver=new ChromeDriver(options);
		
		driver.manage().window().maximize();
		driver.get("https://www.trip.com/hotels/list?city=7523");
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		int homeloop=0;
		while(homeloop<10)
		{
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        
		}

		GoogleMap=new HashMap<Integer,String>();
		int rownumbr=1;
	    Bookingdata=FileFunctions.ReadExcelData("D:\\SelenenumTestData\\MappingInputFilePegi.xlsx","List");
	  
		 
		   List<WebElement> AllPegiPegiLinks=driver.findElements(By.cssSelector("div.online-h5-hotel-card__title>a"));
		   int PegiPegiLink=AllPegiPegiLinks.size();
		   int pages=0;
	       while(pages<PegiPegiLink)
	       {
		
			 GoogleMap.put(Hotelurl, AllPegiPegiLinks.get(pages).getAttribute("href"));

	        writeExcel("D:\\\\SelenenumTestData\\\\MappingInputFilePegi.xlsx","List",rownumbr);
	        pages=pages+1;
	        rownumbr=rownumbr+1;

	    	   
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
		   rows.getCell(SubscriberHotelID).setCellValue(GoogleMap.get(SubscriberHotelID));
		   rows.getCell(Hotelurl).setCellValue(GoogleMap.get(Hotelurl));
		   fis.close();
		   FileOutputStream fos=new FileOutputStream(file);
		   Excelworkbook.write(fos);
		   fos.close();
		   
	}
	
	
	  //@AfterTest
		public void closeBrowser()
		{
			driver.close();
		}
	
	}

	
	


