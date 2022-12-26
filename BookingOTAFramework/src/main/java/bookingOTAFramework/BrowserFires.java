package bookingOTAFramework;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import bookingFileOperations.FileFunctions;

public class BrowserFires
{
public static WebDriver driver;

	@BeforeTest
	@Parameters("browser")
	public void setDriver(String browser) throws Exception
	{
			//String browser =FileFunctions.getDataFromConfigFile("browserName");
			if (browser.equalsIgnoreCase("firefox"))
			{
			System.setProperty("webdriver.gecko.driver","SeleniumAutomation\\Softwares\\geckodriver.exe");
			driver=new FirefoxDriver();
			}
			else if(browser.equalsIgnoreCase("chrome"))
			{
				System.out.println("You are in Chrome browser");
				//System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
				System.setProperty("webdriver.chrome.silentOutput", "true");
                System.setProperty("webdriver.chrome.driver","D:\\Drivers\\chromedriver.exe");
				ChromeOptions options=new ChromeOptions();
				options.setExperimentalOption("useAutomationExtension", false);
				options.setExperimentalOption("excludeSwitches",Collections.singletonList("enable-automation"));
				Map<String, Object> prefs = new HashMap<String, Object>();
				prefs.put("credentials_enable_service", false);
				prefs.put("profile.password_manager_enabled", false);
				options.setExperimentalOption("prefs", prefs);
				driver=new ChromeDriver(options);
				}
			else if(browser.equalsIgnoreCase("IE"))
			{
				System.out.println("You are in IE browser");
				System.setProperty("webdriver.chrome.driver","D:\\Subhash-PGOTA-Drive(T)\\Private_Data\\Browser Driver\\chromedriver.exe");
				driver = new InternetExplorerDriver();
				}
			 else {
					throw new Exception("invalid browser name");
				}
			/*driver.get(url);
			driver.manage().window().maximize();
			//driver.manage().deleteAllCookies();
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(20,TimeUnit.SECONDS);*/
		
	}
	public WebDriver getDriver()
	{
		return driver;
	}
	
	public void websiteUrl(WebDriver driver,String url) throws IOException
	{
		if(url.contains("."))
	{
	 //String WebsiteUrl=FileFunctions.getDataFromConfigFile(url);
	 driver.get(url);
	 driver.manage().window().maximize();
	 //driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	 //driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
	}
	 else
	 {
         String WebsiteUrl=FileFunctions.getDataFromConfigFile(url);
		 driver.get(WebsiteUrl);
		 driver.manage().window().maximize();
		 driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
		 driver.manage().timeouts().pageLoadTimeout(80, TimeUnit.SECONDS);
	 }
	 }
    @AfterTest
	public void closeBrowser()
	{
		getDriver().close();
	}
	}
	

