package Trash;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import SeleniumPackage.FunctionLibrary;
import SeleniumPackage.TestAttributes;

public class Testfffff {
	
public static void main(String[] args) throws InterruptedException {
	
	System.setProperty("webdriver.chrome.driver", "X:\\Gopi\\Gerty\\Web Drivers\\chromedriver.exe");
	WebDriver driver=new ChromeDriver();
	driver.get("https://qz1lx46m.ngrok.io/login");
	driver.findElement(By.id("email")).sendKeys("john@does.com");
	driver.findElement(By.xpath("(//div[@class='buttons']/button)[1]")).click();
	
	Thread.sleep(2000);
	
	driver.findElement(By.id("password")).sendKeys("hello1234*");
	Thread.sleep(2000);
	driver.findElement(By.xpath("(//div[@class='buttons']/button)[2]")).click();
	
	Thread.sleep(3000);
	
	driver.findElement(By.xpath("//div[@class='ant-layout-sider-children']/ul/li[3]")).click();
	
	Thread.sleep(6000);
	
	driver.findElement(By.xpath("(//span[@class='ant-input-group-addon']/div/div)[1]")).click();
	driver.findElement(By.xpath("(//span[@class='ant-input-group-addon']/div/div)[1]")).click();
	driver.findElement(By.xpath("(//span[@class='ant-input-group-addon']/div/div)[1]")).click();
	
	Thread.sleep(2000);
	
	String cv=driver.findElement(By.xpath("(//li[@unselectable='on'])[3]")).getText().trim();
	
	System.out.println(cv);
	Thread.sleep(2000);
	
	
	
	//String idata="//li[@unselectable='on']";
	
	//System.out.println(selectdata);	
	//Thread.sleep(5000);
	//List<WebElement> ele=driver.findElements(By.xpath(idata));
	//List<WebElement> ele=TestAttributes.driver.findElements(By.xpath("//li[@unselectable='unselectable']"));
	//Thread.sleep(1000);
				
	//String selectdata="GBP";
	//driver.findElement(By.xpath("//span[contains(text(),'"+selectdata+"')]")).click();
	
	
	
}

}
