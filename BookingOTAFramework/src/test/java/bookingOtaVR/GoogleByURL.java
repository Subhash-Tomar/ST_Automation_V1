

package bookingOtaVR;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.Normalizer;
import java.time.Duration;
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
import net.ricecode.similarity.JaroWinklerStrategy;
import net.ricecode.similarity.SimilarityStrategy;
import net.ricecode.similarity.StringSimilarityService;
import net.ricecode.similarity.StringSimilarityServiceImpl;
import stringSimilarityScore.StringSimilarityMatchingScore_P;

public class GoogleByURL
{
	
	Object[][] ExcelData;
	HashMap<Integer,String> GoogleMap;
	final static int hotelurl=0;
	final static int addressExcel=1;
	final static int cityExcel=2;
	final static int CountryExcel=3;
	final static int GoogleExcelhotelName=4;
	final static int GoogleExcelhotelAddress=5;
	final static int GoogleHotelid=6;
	final static int similarity_hotelname=7;
	final static int similarity_address=8;

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



		
	    ExcelData=FileFunctions.ReadExcelData("D:\\SelenenumTestData\\MappingInputFile_GoogleURL.xlsx","List");  
	   for(int i=1;i<ExcelData.length;i++)
	   {
		   GoogleMap=new HashMap<Integer,String>();
		   String url=ExcelData[i][hotelurl].toString();
		   driver.get(url);
		   String currenturl=driver.getCurrentUrl();
		   GoogleMap.put(GoogleHotelid, currenturl);
			 
		   driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));   
	
		
		    System.out.println("Row number...."+i);
            writeExcel("D:\\SelenenumTestData\\MappingInputFile_GoogleURL.xlsx","List",i);
            
			
	   }
	   
	   
	   
   }
   
   
   
	public void writeExcel(String path,String sheet,int excelrownumber) throws Exception	
	{
	
		try
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
		
		   rows.getCell(GoogleHotelid).setCellValue(GoogleMap.get(GoogleHotelid));
	

		   fis.close();
		   FileOutputStream fos=new FileOutputStream(file);
		   Excelworkbook.write(fos);
		   fos.close();
		   GoogleMap.clear();
		}
		catch(Exception e)
		{
	           System.out.println(e.getMessage());

		}
		   
		   
	}
	
	

	
	}

	
	
