package utilities;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import SeleniumPackage.FunctionLibrary;
import SeleniumPackage.TestAttributes;
//import io.appium.java_client.android.AndroidDriver;

public class EnvironmentSetup {

	private static WebDriver webDriver;
	DesiredCapabilities cap = new DesiredCapabilities();
	String osType = System.getProperty("os.name");

	public WebDriver getWebDriver(String browserName) throws MalformedURLException {

		
		String webDriversLocation = TestAttributes.ProjectLocation  + "Web Drivers/";

		switch(browserName.trim().toLowerCase()) {

		case "firefox":		
			if (osType.trim().toLowerCase().contains("windows")) {
				System.setProperty("webdriver.gecko.driver", webDriversLocation  + "geckodriver.exe");
				webDriver = new FirefoxDriver();

			} else { 
				webDriver = new FirefoxDriver();
			}
			break;
		case "chrome":

			if (osType.trim().toLowerCase().contains("windows")) {

				// Driver initialization code for firefox Windows *************
				System.setProperty("webdriver.chrome.driver", webDriversLocation  + "chromedriver-win.exe");

			} else { 

				// Driver initialization code for firefox Mac or Some other OS *************
				System.setProperty("webdriver.chrome.driver", webDriversLocation  + "chromedriver-mac");
			}

			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-extensions");
			webDriver = new ChromeDriver(options);
			break;	

		case "ie":
			if (osType.trim().toLowerCase().contains("windows"))
			{
				//System.setProperty("java.net.preferIPv4Stack", "true");
				System.setProperty("webdriver.ie.driver", webDriversLocation + "IEDriverServer.exe");
				//System.setProperty("webdriver.ie.driver","X:\\Automation\\Framework\\Gerty\\Web Drivers\\IEDriverServer.exe");
				//DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();  
				//ieCapabilities.setCapability("ensureCleanSession", true);
				//ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				//webDriver = new InternetExplorerDriver();
				InternetExplorerOptions ieOptions = new InternetExplorerOptions();
				ieOptions.introduceFlakinessByIgnoringSecurityDomains();
				webDriver = new InternetExplorerDriver(ieOptions);
			} 
			else {

			}
			break;

		case "safari":
			webDriver = new SafariDriver();
			break;

		case "edge":
			System.setProperty("webdriver.edge.driver", webDriversLocation + "MicrosoftWebDriver.exe");
			//DesiredCapabilities capabilities = DesiredCapabilities.edge();
			EdgeOptions option=new EdgeOptions();
			option.getBrowserName();
			webDriver = new EdgeDriver(option);
			break;

		case "chromemobile":			
			try {
				cap.setCapability("udid", FunctionLibrary.getConfigValue("Deviceid"));
				cap.setCapability("deviceName", FunctionLibrary.getConfigValue("deviceName"));
				cap.setCapability("platformName", FunctionLibrary.getConfigValue("platformName"));
				cap.setCapability("platformVersion",FunctionLibrary.getConfigValue("platformVersion"));
				//cap.setCapability("appPackage","com.unionbankph.getgopay.qat");
				//cap.setCapability("appActivity","com.unionbankph.getgopay.splash.SplashActivity");
				cap.setCapability("browserName", FunctionLibrary.getConfigValue("browserName"));

				//webDriver = new AndroidDriver<>(new URL(FunctionLibrary.getConfigValue("Appiumurl")), cap);
				break;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}


			/*case "firefoxmobile":

			cap.setCapability("udid", "5200ac74fea19479");
			cap.setCapability("deviceName", "SamsungDuos");
			cap.setCapability("platformName", "Android");
			cap.setCapability("platformVersion", "7.0");
			//cap.setCapability("appPackage","com.unionbankph.getgopay.qat");
			//cap.setCapability("appActivity","com.unionbankph.getgopay.splash.SplashActivity");
			cap.setCapability("browserName", "Firefox");

			webDriver = new AndroidDriver<>(new URL("http://0.0.0.0:4723/wd/hub"), cap);
			break;*/
		}

		return webDriver;

	}

	public String getAutomationApplication(String toolName) {
		String aut;
		if (toolName.trim().equalsIgnoreCase("selenium")) {
			aut = "Web";
		}else if (toolName.trim().equalsIgnoreCase("seetest")) {
			aut = "Mobile";
		}else {
			aut = null;
		}
		return aut;
	}

	public static String getProjectLocation() {
		try {			
			String PackageLocation = EnvironmentSetup.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();

			String[] PackageLocationSplit = PackageLocation.split("/");

			String CodePath = "";

			if (!PackageLocation.toLowerCase().contains(".jar"))
				for (int i = 1;i<PackageLocationSplit.length-3;i++)
					CodePath = CodePath + PackageLocationSplit[i] + "/";
			else				
				for (int i = 1;i<PackageLocationSplit.length-1;i++)
					CodePath = CodePath + PackageLocationSplit[i] + "/";
			return CodePath.trim();		
		} catch (Exception e) {
			TestAttributes.InitialSetUpErrorMessage = TestAttributes.InitialSetUpErrorMessage + "Error while retrieving the project location. ";
			return null;
		}
	}

	public void createDatabaseConnection(String database) {
        try {
            switch(database.trim().toLowerCase()) {
            case "access":

                if (osType.trim().toLowerCase().contains("windows"))
                {
                    /*
                     * After Java 1.8 there is no in built support from JAVA to connect Microsoft Access
                     * Hence testers are requested to use some other open source toold to connect MS Access
                     * Here we are gonna use "UCanAccess" library to connect Access
                     */

                    Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
                    String ConnectionString = "jdbc:ucanaccess://"  + TestAttributes.ProjectLocation + "BackEnd.accdb";
                    TestAttributes.conn = DriverManager.getConnection(ConnectionString);
                    /*
                     * This is the old code , can be used for JAVA version less than 1.8
                     *TestAttributes.conn = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ=" + TestAttributes.ProjectLocation + "BackEnd.accdb");
                     */

                    break;
                }
                else {

                    /*
                     * After Java 1.8 there is no in built support from JAVA to connect Microsoft Access
                     * Hence testers are requested to use some other open source toold to connect MS Access
                     * Here we are gonna use "UCanAccess" library to connect Access
                     */

                    Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
                    String ConnectionString = "jdbc:ucanaccess:////"  + TestAttributes.ProjectLocation + "BackEnd.accdb";
                    TestAttributes.conn = DriverManager.getConnection(ConnectionString);
                    /*
                     * This is the old code , can be used for JAVA version less than 1.8
                     *TestAttributes.conn = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ=" + TestAttributes.ProjectLocation + "BackEnd.accdb");
                     */

                    break;

                }

            }    

            TestAttributes.stmtTests = TestAttributes.conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            TestAttributes.stmtSteps = TestAttributes.conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            TestAttributes.stmtsid = TestAttributes.conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            TestAttributes.stmt = TestAttributes.conn.createStatement();            
            TestAttributes.stmtSDS = TestAttributes.conn.createStatement();
            TestAttributes.stmtTDS = TestAttributes.conn.createStatement();

        } catch(Exception e) {
            TestAttributes.InitialSetUpErrorMessage = TestAttributes.InitialSetUpErrorMessage + "Error while making the database connection. ";
        }
    }
}
