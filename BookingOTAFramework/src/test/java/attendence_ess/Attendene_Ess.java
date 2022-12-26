package attendence_ess;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
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

public class Attendene_Ess 

{

	public WebDriver driver;
	
	@Test
	public void attendence()
	{
	 System.setProperty("webdriver.chrome.silentOutput", "true");
     System.setProperty("webdriver.chrome.driver","D:\\Drivers\\chromedriver.exe");
     
    /* ChromeOptions options=new ChromeOptions();
		options.setExperimentalOption("useAutomationExtension", false);
		options.setExperimentalOption("excludeSwitches",Collections.singletonList("enable-automation"));
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);
		options.setExperimentalOption("prefs", prefs);*/
     
		driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.ess.adp.in/ESS4/");
		driver.findElement(By.cssSelector("adp-textbox[name=\"ClientID\"]>div>input")).sendKeys("RateGainTravel");
		driver.findElement(By.cssSelector("adp-textbox[name=\"EmployeeID\"]>div>input")).sendKeys("2435");
		driver.findElement(By.cssSelector("adp-textbox[name=\"PasswordControl\"]>div>input")).sendKeys("jeevikaT0m@r567");
		driver.findElement(By.cssSelector("div.vdl-form-group.vdl-text-center > button")).click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.findElement(By.xpath("//span[text()='Time & Attendance']")).click();
		String window1=driver.getWindowHandle();
		System.out.println("window first"+window1);
        Set<String> windows=driver.getWindowHandles();
        List<Object> windowfinls=windows.stream().collect(Collectors.toList());
        driver.switchTo().window(windowfinls.get(1).toString());
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//button[text()='Punch In ']")))).click();
        driver.findElement(By.xpath("//adp-textbox[@name='ClientID']")).sendKeys("");
        
        
}
	
}
