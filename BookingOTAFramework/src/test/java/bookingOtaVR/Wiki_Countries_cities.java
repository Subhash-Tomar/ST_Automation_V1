package bookingOtaVR;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.Normalizer;
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
import net.ricecode.similarity.JaroWinklerStrategy;
import net.ricecode.similarity.SimilarityStrategy;
import net.ricecode.similarity.StringSimilarityService;
import net.ricecode.similarity.StringSimilarityServiceImpl;

public class Wiki_Countries_cities
{
	
	Object[][] ExcelData;
	HashMap<Integer,String> GoogleMap;
	final static int Number=0;
	final static int wiki_URL=1;
	final static int cityExcel=2;
	final static int CountryExcel=3;
	final static int CountryExcel2=4;
	//final static int GoogleExcelhotelName=4;
	//final static int GoogleExcelhotelAddress=5;
	//final static int GoogleHotelid=6;

   WebDriver driver;
	
   @Test
   public void TestData() throws Exception
   {
	 //System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
     System.setProperty("webdriver.chrome.silentOutput", "true");
     System.setProperty("webdriver.chrome.driver","D:\\Drivers\\202\\chromedriver.exe");
     ChromeOptions options=new ChromeOptions();
		options.setExperimentalOption("useAutomationExtension", false);
		options.setExperimentalOption("excludeSwitches",Collections.singletonList("enable-automation"));
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);
		options.setExperimentalOption("prefs", prefs);
		driver=new ChromeDriver(options);
		driver.manage().window().maximize();

	    ExcelData=FileFunctions.ReadExcelData("D:\\SelenenumTestData\\MappingInputFile_wiki.xlsx","List1");  
	    int j=0;
	    int rownumber=1;
	    String wiki_countryname="";
	   for(int i=1;i<ExcelData.length;i++)
	   {
		
		   try
		   {
		   
			   
		   String url=ExcelData[i][0].toString();
		   wiki_countryname=ExcelData[i][1].toString();
		   driver.get(url);
		   Thread.sleep(4000);
		   String country= driver.findElement(By.cssSelector("h1#firstHeading")).getText();
		   
		       
		   List<WebElement> allrows=driver.findElements(By.cssSelector("table.wikitable.sortable.jquery-tablesorter > tbody>tr"));
		   Iterator<WebElement> allrowdata=allrows.iterator();
		
		   while(allrowdata.hasNext())
		   {
			 
			   String cityname="";
			
			  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			    cityname=allrowdata.next().findElement(By.cssSelector("td:nth-child(1)")).getText();
			    System.out.println(cityname);
			    try
			    {
			    writeExcel("D:\\SelenenumTestData\\MappingInputFile_wiki.xlsx","List",rownumber,url,cityname,country,wiki_countryname);
			    }
			    catch(Exception e)
			    {
			    	System.out.println(e.getMessage());
			    };
			    
			   rownumber=rownumber+1;
			  
		   }
		   }
		   catch(Exception e)
		   {
			   
		   }
		 
		
		   
		   
		 //writeExcel("D:\\SelenenumTestData\\MappingInputFile.xlsx","List",i);
            
			
		
	   }
	   
		   
	  
   }
   
   
   
	public void writeExcel(String path,String sheet,int excelrownumber,String url_sheet,String city,String country_wiki,String country_ex) throws Exception	
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
		   rows.getCell(wiki_URL).setCellValue(url_sheet);
		   rows.getCell(cityExcel).setCellValue(city);
		   rows.getCell(CountryExcel).setCellValue(country_wiki);
		   rows.getCell(CountryExcel2).setCellValue(country_ex);
		   //rows.getCell(GoogleHotelid).setCellValue(GoogleMap.get(GoogleHotelid));
		   //rows.getCell(ExpediaGoogleUrl1).setCellValue(GoogleMap.get(ExpediaGoogleUrl1));
		   fis.close();
		   FileOutputStream fos=new FileOutputStream(file);
		   Excelworkbook.write(fos);
		   fos.close();
		  // GoogleMap.clear();
		   
		   
	}
	
	

	
	}

	
	


