package Trash;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;


import org.openqa.selenium.WebDriverException;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;








public class Reporter   {
	
	public static ExtentTest test;
	public static ExtentReports extent=null;;
	  
	//static String a= new SimpleDateFormat("YYYY-MM-DD_HH:MM:SS").format(new Date());
	//static String a= new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(Calendar.getInstance().getTime());
	public static String a;
	//static int number=1;
	
	public static int i;
	
	public static String CodePath="X:\\Gopi\\Gerty";
	static Connection conn;

	public static Statement stmtresult = null;	
	public static Statement stmtbatch = null;
	
	private static ResultSet TestsResults;
	private static String TestName,teststatus,actualresult;
	//static String CodePath="X:\\Gopi\\Gerty\\REPORTTT\\";
	
	public static void createdb() throws ClassNotFoundException, SQLException
	{
		Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		String ConnectionString = "jdbc:ucanaccess://"  + CodePath + "BackEnd_OROLDBK.accdb";
		conn = DriverManager.getConnection(ConnectionString);	
		
		stmtbatch = conn.createStatement();			
		stmtresult = conn.createStatement();		
		
		
		String sql="SELECT * FROM SQS_TEST_BATCH where SQS_TB_Test_Batch_Name='GetGo_Run'";
		TestsResults = stmtbatch.executeQuery(sql);
		TestsResults.next();		
		TestName=TestsResults.getString("SQS_TB_Test_Id_Name").trim();
		TestsResults.close();		
		
		String sql1="SELECT * FROM SQS_TEST_RESULT where SQS_TR_Run_Id=1045";
		TestsResults = stmtbatch.executeQuery(sql1);
		TestsResults.next();		
		teststatus=TestsResults.getString("SQS_TR_Step_Status").trim();
		actualresult=TestsResults.getString("SQS_TR_Actual_Result").trim();
		TestsResults.close();
		
		
	}


	
	public static void main(String[] args) throws Throwable {
		//////
		
		
		Reporter.i=1045;
		Reporter.a= new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(Calendar.getInstance().getTime());
		
		Reporter.startResult();
		Reporter.startTestCase(TestName);
		
		
		////startResult();
		
		//startTestCase(String iData);
		//report(String desc,String status, String methoddesc);
		
		startTestCase("Testflowvalidating");
		report("loginflow","pass", "method1");
		
		
	endtest();
		endflush();
	}
	
	public static void report(String desc,String status, String methoddesc) throws Throwable
	{	
		//long number = (long) Math.floor(Math.random() * 900000000L) + 10000000L;
		try {
			new File(CodePath+"\\reports\\"+a+"_Run_"+i+"\\images").mkdir();
			//BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
			//ImageIO.write(image, "jpg", new File(CodePath+"\\reports\\"+a+"_Run_"+i+"\\images\\step "+number+"_"+methoddesc+".jpg"));
			//FileUtils.copyFile(((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE) , new File(CodePath+"\\reports\\"+a+"_Run_"+i+"\\images\\step "+number+"_"+methoddesc+".jpg"));
		} catch (WebDriverException e) {
			e.printStackTrace();
		}

		// Write if it is successful or failure or information
		if(status.toUpperCase().equals("PASSED")){
			//test.log(LogStatus.PASS, desc+test.addScreenCapture(CodePath+"\\reports\\"+a+"_Run_"+i+"\\images\\step "+number+"_"+methoddesc+".jpg"));
		}else if(status.toUpperCase().equals("FAILED")){
			//test.log(LogStatus.FAIL, desc+test.addScreenCapture(CodePath+"\\reports\\"+a+"_Run_"+i+"\\images\\step#"+number+"_"+methoddesc+".jpg"));
			//throw new RuntimeException("FAILED");
		}else if(status.toUpperCase().equals("INFO")){
			//test.log(LogStatus.INFO, desc);
		}
		//number++;
	}


	public static void startResult(){

		extent = new ExtentReports(CodePath+"\\reports\\"+a+"_Run_"+i+"\\"+a+"_cyclos_Run "+i+".html", false);

		//File filee=new File(userpath+"\\extent-config.xml");
		//extent.loadConfig(filee);

	}

	public static void startTestCase(String iData){
		test=extent.startTest(iData);
	}

	public static void endtest(){
		extent.endTest(test);
	}

	public static void endflush(){

		extent.flush();
	}
}
		
		

			
