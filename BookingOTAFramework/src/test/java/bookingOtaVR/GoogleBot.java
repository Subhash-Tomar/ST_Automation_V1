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

public class GoogleBot
{
	
	Object[][] ExcelData;
	HashMap<Integer,String> GoogleMap;
	HashMap<Integer,Double> SimilarityMap;
	final static int hotelnameExcel=0;
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
     System.setProperty("webdriver.chrome.driver","D:\\Drivers\\111\\chromedriver.exe");
     ChromeOptions options=new ChromeOptions();
		options.setExperimentalOption("useAutomationExtension", false);
		options.setExperimentalOption("excludeSwitches",Collections.singletonList("enable-automation"));
		options.addArguments("--remote-allow-origins=*");
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);
		options.setExperimentalOption("prefs", prefs);
		driver=new ChromeDriver(options);
		driver.manage().window().maximize();

		driver.get("https://www.google.com/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
        driver.findElement(By.cssSelector("input[name=\"q\"]")).sendKeys("park plaza hotel");
        driver.findElement(By.cssSelector("input[name=\"q\"]")).sendKeys(Keys.ENTER);

		
	    ExcelData=FileFunctions.ReadExcelData("D:\\SelenenumTestData\\MappingInputFile.xlsx","List");  
	    int j=0;
	   String UTF_HotelName;
	   for(int i=1;i<ExcelData.length;i++)
	   {
		   double hotelname_score = 0;
		   double address_score=0;
		   
		   try
		   {
		
		   UTF_HotelName="";
			GoogleMap=new HashMap<Integer,String>();
			SimilarityMap=new HashMap<Integer,Double>();
			
			if(j==12)
			{
				driver.get("https://www.youtube.com/");
				Thread.sleep(4000);
				j=0;


				driver.get("https://www.google.com/");
		        driver.findElement(By.cssSelector("input[title]")).sendKeys("park plaza hotel");
		        driver.findElement(By.cssSelector("input[title]")).sendKeys(Keys.ENTER);
			}
			
			else {
	   
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
          
 
           UTF_HotelName = new String(InputParameter4.getBytes("ISO-8859-15"), "UTF-8");
           Normalizer.normalize(UTF_HotelName, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
           
           driver.findElement(By.cssSelector("div[jsname=\"gLFyf\"]>input")).sendKeys(Keys.CONTROL+"a");
           driver.findElement(By.cssSelector("div[jsname=\"gLFyf\"]>input")).sendKeys(Keys.DELETE);

           driver.findElement(By.cssSelector("div[jsname=\"gLFyf\"]>input")).sendKeys(UTF_HotelName);

           driver.findElement(By.cssSelector("button[jsname=\"Tg7LZd\"]>div>span>svg")).click();
           try
           {
		   GoogleHotelname=driver.findElement(By.cssSelector("h2[data-attrid=\"title\"]>span")).getText();
		   GoogleMap.put(GoogleExcelhotelName, GoogleHotelname);
           }catch(Exception e) {};
           
           
           try
           {
     		 if(GoogleHotelname==""||GoogleHotelname.contains("See results"))

		   {
     			String UTF_HotelName2 = new String(InputParameter2.getBytes("ISO-8859-15"), "UTF-8");
                Normalizer.normalize(UTF_HotelName2, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
                
     			driver.findElement(By.cssSelector("div[jsname=\"gLFyf\"]>input")).sendKeys(Keys.CONTROL+"a");
     	        driver.findElement(By.cssSelector("div[jsname=\"gLFyf\"]>input")).sendKeys(Keys.DELETE);
                driver.findElement(By.cssSelector("div[jsname=\"gLFyf\"]>input")).sendKeys(UTF_HotelName2);
                driver.findElement(By.cssSelector("button[jsname=\"Tg7LZd\"]>div>span>svg")).click();
                
			   GoogleHotelname=driver.findElement(By.cssSelector("h2[data-attrid=\"title\"]>span")).getText();
			   GoogleMap.put(GoogleExcelhotelName, GoogleHotelname);
		   }
		   
           }catch(Exception e) {};
           
           
		   driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));   
		   
		   try
		   {
			   List<WebElement> Addr=driver.findElements(By.xpath("//a[text()='Address']//parent::span//following-sibling::span"));
			   System.out.println(Addr.get(0).getText());
			   System.out.println(Addr.get(1).getText());
		     GooglehotelAddress=Addr.get(1).getText();

		 //driver.findElement(By.xpath("//a[text()='Address']//parent::span//following-sibling::span")).getText();
		   GoogleMap.put(GoogleExcelhotelAddress, GooglehotelAddress);
		   }catch(Exception e) {};
		  
			 
		   driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));   
		   
			   
		   webGoogleHotelid =driver.findElement(By.xpath("//a[text()='Check availability']")).getAttribute("href");
		   GoogleMap.put(GoogleHotelid, webGoogleHotelid);
		   
		   hotelname_score=StringSimilarityMatchingScore_P.String_Matcher_Similarity(Hotelname, GoogleHotelname);
		   address_score=StringSimilarityMatchingScore_P.String_Matcher_Similarity(Address, GooglehotelAddress);
		   SimilarityMap.put(similarity_hotelname, hotelname_score);
		   SimilarityMap.put(similarity_address, address_score);
		     	   
		  j++;
		   }
		   
			System.out.println(hotelname_score+"............"+address_score);
			
			if(hotelname_score>.10)
			{
		    System.out.println("Row number...."+i);
            writeExcel("D:\\SelenenumTestData\\MappingInputFile.xlsx","List",i);
            
			}
		
	   }
	   
		   catch(Exception e)
		   {
			   
		   }
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
		   rows.getCell(GoogleExcelhotelName).setCellValue(GoogleMap.get(GoogleExcelhotelName));
		   rows.getCell(GoogleExcelhotelAddress).setCellValue(GoogleMap.get(GoogleExcelhotelAddress));
		   rows.getCell(GoogleHotelid).setCellValue(GoogleMap.get(GoogleHotelid));
		   rows.getCell(similarity_hotelname).setCellValue(SimilarityMap.get(similarity_hotelname));
		   rows.getCell(similarity_address).setCellValue(SimilarityMap.get(similarity_address));

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

	
	
