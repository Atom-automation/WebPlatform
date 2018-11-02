package Trash;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoginnnCheck {
	
	public static void main(String[] args) throws InterruptedException {
		
		System.setProperty("webdriver.chrome.driver", "X:\\Gopi\\Gerty\\Web Drivers\\chromedriver.exe");
		WebDriver driver=new ChromeDriver();
		driver.get("https://qz1lx46m.ngrok.io/login");
		driver.findElement(By.xpath("//button[@class='ant-btn register-text nonauth-header-button ant-btn-primary ant-btn-background-ghost']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//a[contains(text(),'NO')]")).click();
		Thread.sleep(2000);
String b=driver.findElement(By.xpath("//div[@role='tabpanel']/div/form/div/div/div[6]/p")).getCssValue("color");
		
		System.out.println(b);
		driver.findElement(By.id("password")).sendKeys("test123@");
		driver.findElement(By.id("confirm_password")).sendKeys("test123@");
		Thread.sleep(2000);
		String a=driver.findElement(By.xpath("//div[@role='tabpanel']/div/form/div/div/div[6]/p")).getCssValue("color");
		
		System.out.println(a);
	}

}
