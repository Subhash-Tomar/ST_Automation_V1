
package bookingOtaVR;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import bookingFileOperations.FileFunctions;

public class Convert_XML_TO_Excel
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
		driver.manage().window().maximize();
		
		String filePath = "D:\\Booking_Inventory\\Done\\hotel.xml";
		File file = new File(filePath);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbf.newDocumentBuilder();
		Document doc = dBuilder.parse(file);
		doc.getDocumentElement().normalize();
		//System.out.println(doc.getDocumentElement().getNodeName());
		NodeList nodeList = doc.getElementsByTagName("url");
		int tLength = nodeList.getLength();

		
	  int j=0;
	  Object[][] Bookingdata=FileFunctions.ReadExcelData("D:\\Booking_Inventory\\Done\\Booking_Hotelurl.xlsx","List");  
	   for(int i=1;i<=tLength;i++)
	   {
		   Node node = nodeList.item(j);
		   String citypage="";
		   if(node.getNodeType()==Node.ELEMENT_NODE)
		   {
				Element element = (Element)node;
			citypage=element.getElementsByTagName("loc").item(0).getTextContent();

			 //System.out.println(citypage);
		   }

		   BookingMap=new HashMap<Integer,String>();
		   
		   
		   
		   BookingMap.put(ws_hotelname, citypage);
	 
		   writeExcel("D:\\Booking_Inventory\\\\Done\\Booking_Hotelurl.xlsx","List",i);
		   System.out.println("rows number----------"+i);
		   j++;
		   
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
		   //rows.getCell(ws_address).setCellValue(BookingMap.get(ws_address));
		   //rows.getCell(ws_hotelid).setCellValue(BookingMap.get(ws_hotelid));
		   //rows.getCell(ws_airportcode).setCellValue(BookingMap.get(ws_airportcode));
		   //rows.getCell(newurl).setCellValue(BookingMap.get(newurl));
		  // rows.getCell(Combinedcode).setCellValue(BookingMap.get(Combinedcode));
		   //rows.getCell(Remark).setCellValue(BookingMap.get(Remark));

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

	