package bookingOtaVR;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import bookingFileOperations.FileFunctions;

public class Convert_xmlgz 
{
	
	public static WebDriver driver;
	final static int Hotelurl=0;


	public static void main(String[] args) throws Exception 
	
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
			
			  Object[][] Bookingdata=FileFunctions.ReadExcelData("D:\\Booking_Inventory\\book1.xlsx","List");  
			   for(int i=1;i<=Bookingdata.length;i++)
			   {
				   
				   String filename=Bookingdata[i][0].toString();
				   try
				   {
					Path source = Paths.get("D:\\Booking_Inventory\\xml-D\\"+filename);
			        Path target = Paths.get("D:\\Booking_Inventory\\XML\\"+filename+".xml");

			        try {

			        	Convert_xmlgz.decompressGzip(source, target);

			        } catch (IOException e) {
			            e.printStackTrace();
			        }
			        
			        System.out.println("row:"+i);
			        
				   } catch(Exception e){};
				}
			   
			   
	}
			

	
	public static void decompressGzip(Path source, Path target) throws IOException {

        try (GZIPInputStream gis = new GZIPInputStream(
                                      new FileInputStream(source.toFile()));
             FileOutputStream fos = new FileOutputStream(target.toFile())) {

            // copy GZIPInputStream to FileOutputStream
            byte[] buffer = new byte[1024];
            int len;
            while ((len = gis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }

        }

    }


}
