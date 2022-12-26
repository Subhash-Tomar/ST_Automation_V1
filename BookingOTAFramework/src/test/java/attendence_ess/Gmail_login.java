package attendence_ess;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class Gmail_login 

{
public WebDriver driver;
	
	@Test
	public void attendence()
	{
	 System.setProperty("webdriver.chrome.silentOutput", "true");
     System.setProperty("webdriver.chrome.driver","D:\\Drivers\\chromedriver.exe");
     
        ChromeOptions options=new ChromeOptions();
		options.setExperimentalOption("useAutomationExtension", false);
		options.addArguments("--lang=en-us");
		options.addArguments("user-data-dir=C:\\Users\\subhash.tomar\\AppData\\Local\\Google\\Chrome\\User Data\\Default\\Default");
		options.setExperimentalOption("excludeSwitches",Collections.singletonList("enable-automation"));
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);
		options.setExperimentalOption("prefs", prefs);
     
		driver=new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.get("https://www.google.com/");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.cssSelector("div>a[href^=\"https://mail\"]")).click();
		driver.findElement(By.xpath("//a[text()='Sign in']")).click();
		//Set<String> windows=driver.getWindowHandles();
		//Object child=windows.stream().collect(Collectors.toList()).get(0);
		//Object parent=windows.stream().collect(Collectors.toList()).get(1);
		//driver.switchTo().window(parent.toString());
		//String newurl=driver.getCurrentUrl();
		//driver.close();
		//driver.switchTo().window(child.toString());
		//driver.get(newurl);
		//driver.findElement(By.cssSelector("input[type=\"email\"]")).sendKeys("subhashmca07@gmail.com");
		//driver.findElement(By.xpath("//span[text()='Next']")).click();
		
		
		
}
	
}



