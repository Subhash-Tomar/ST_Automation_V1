
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
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import bookingFileOperations.FileFunctions;

public class GoodChoice
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
	  System.setProperty("webdriver.chrome.driver","D:\\drivers\\202\\chromedriver.exe");
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
		//driver.get("https://www.goodchoice.kr/product/search/2/2003");
		
		GoogleMap=new HashMap<Integer,String>();
		int rownumbr=1;
	    Bookingdata=FileFunctions.ReadExcelData("D:\\SelenenumTestData\\MappingInputFilePegi.xlsx","List");
	    
        String[] Citilist = new String[]{
        		"2003",	"2004",	"2005",	"2006",	"2007",	"2008",	"2009",	"2010",	"2011",	"2012",	"2013",	"2014",	"2015",	"2016",	"2017",	"2018",	"2019",	"2020",	"2021",	"2022",	"2023",	"2024",	"2025",	"2026",	"2027",	"2028",	"2029",	"2030",	"2031",	"2032",	"2033",	"2034",	"2035",	"2036",	"2037",	"2038",	"2039",	"2040",	"2041",	"2042",	"2043",	"2044",	"2045",	"2046",	"2047",	"2048",	"2049",	"2050",	"2051",	"2052",	"2053",	"2054",	"2055",	"2056",	"2057",	"2058",	"2059",	"2060",	"2061",	"2062",	"2063",	"2064",	"2065",	"2066",	"2067",	"2068",	"2069",	"2070",	"2071",	"2072",	"2073",	"2074",	"2075",	"2076",	"2077",	"2078",	"2079",	"2080",	"2081",	"2082",	"2083",	"2084",	"2085",	"2086",	"2087",	"2088"
        		};

       for(int citynumber=0;citynumber<Citilist.length;citynumber++)
       {

   		try
   		{
	
			  String hotelurlpegi="https://www.goodchoice.kr/product/search/2/"+Citilist[citynumber];
			  driver.get(hotelurlpegi);
		 
		   List<WebElement> AllPegiPegiLinks=driver.findElements(By.cssSelector("div#poduct_list_area>li>a"));
		   int PegiPegiLink=AllPegiPegiLinks.size();
		   int pages=0;
	       while(pages<PegiPegiLink)
	       {
		
			 GoogleMap.put(Hotelurl, AllPegiPegiLinks.get(pages).getAttribute("href"));

	        writeExcel("D:\\SelenenumTestData\\MappingInputFilePegi.xlsx","List",rownumbr);
	        pages=pages+1;
	        rownumbr=rownumbr+1;

	    	   
	       }

	       
		   }
   		
		   
	       catch(NoSuchElementException e)
	       {
	    	   System.out.println(e.getMessage());
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
		   rows.getCell(SubscriberHotelID).setCellValue(GoogleMap.get(SubscriberHotelID));
		   rows.getCell(Hotelurl).setCellValue(GoogleMap.get(Hotelurl));
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

	
	


