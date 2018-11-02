package Trash;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import SeleniumPackage.TestAttributes;

public class Testrew {

	
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
		
		driver.findElement(By.xpath("//div[@class='ant-layout-sider-children']/ul/li[4]")).click();
		
		Thread.sleep(6000);
		
		String value=driver.findElement(By.xpath("//div[@class='ant-spin-container']/div/div/div/table/tbody/tr/td")).getText();
		
				System.out.println(value);
				
				
				
	boolean a=driver.findElement(By.xpath("(//div[@class='ant-spin-container']/div/div/div/table/tbody/tr/td[3]/div/h2/ul)[1]")).isSelected();
	boolean b=driver.findElement(By.xpath("(//div[@class='ant-spin-container']/div/div/div/table/tbody/tr/td[3]/div/h2/ul)[8]")).isSelected();
		
	
	boolean c=driver.findElement(By.xpath("(//div[@class='ant-spin-container']/div/div/div/table/tbody/tr/td[3]/div/h2/ul)[1]")).isEnabled();
	boolean d=driver.findElement(By.xpath("(//div[@class='ant-spin-container']/div/div/div/table/tbody/tr/td[3]/div/h2/ul)[8]")).isEnabled();
	
	boolean e=driver.findElement(By.xpath("(//div[@class='ant-spin-container']/div/div/div/table/tbody/tr/td[3]/div/h2/ul)[1]")).isDisplayed();
	boolean f=driver.findElement(By.xpath("(//div[@class='ant-spin-container']/div/div/div/table/tbody/tr/td[3]/div/h2/ul)[8]")).isDisplayed();
	
	String col1=driver.findElement(By.xpath("(//div[@class='ant-spin-container']/div/div/div/table/tbody/tr/td[3]/div/h2/ul)[1]")).getCssValue("color");
	String col2=driver.findElement(By.xpath("(//div[@class='ant-spin-container']/div/div/div/table/tbody/tr/td[3]/div/h2/ul)[8]")).getCssValue("color");
	
	
	String col3=driver.findElement(By.xpath("(//div[@class='ant-spin-container']/div/div/div/table/tbody/tr/td[3]/div/h2/ul/li)[1]")).getAttribute("class");
	String col4=driver.findElement(By.xpath("(//div[@class='ant-spin-container']/div/div/div/table/tbody/tr/td[3]/div/h2/ul/li)[8]")).getAttribute("class");
	
	if(col3.endsWith("full"))
	{
			System.out.println("STAR IS SELECTED");
	}
	else
	{
		System.out.println("STAR IS NOT SELECTED");
	}

	
	
	System.out.println(a);
	System.out.println(b);
	System.out.println(c);
	System.out.println(d);
	System.out.println(e);
	System.out.println(f);
	System.out.println(col1);
	System.out.println(col2);
	System.out.println(col3);
	System.out.println(col4);
	}
}
