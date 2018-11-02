package Trash;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.openqa.selenium.WebDriverException;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class DBconnectivity_Mac_Safdari  {

	static String ProjectLocation="X:\\Gopi\\MAC_Dafari\\";
	static String batchname="GetGo_Regression_Safari";
	//static String batchname="Revamp";

	static Connection conn = null;
	static Statement stmtTests,smtbatch,instanceruns = null;	
	static ResultSet TestsResults,batch,instruns;
	static String[] testlog = new String[1000];
	static String[] status = new String[1000];
	static String[] screenshot = new String[1000];
	static int[] instancesss = new int[1000];
	static String[] testname = new String[1000];
	static int[] rid = new int[1000];





	private static ExtentTest test;
	private static ExtentReports extent;

	//static String a= new SimpleDateFormat("YYYY-MM-DD_HH:MM:SS").format(new Date());
	static String a= new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(Calendar.getInstance().getTime());

	static SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh-mm-ss aa", Locale.ENGLISH);



	static String userpath=ProjectLocation;


	public static void main(String[] args) throws Throwable {

		startResult(batchname);

		// counter to get the records from DB
		int i=1;
		int g=1;
		int h=1;

		Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		String ConnectionString = "jdbc:ucanaccess://"  +ProjectLocation + "BackEnd.accdb";

		conn = DriverManager.getConnection(ConnectionString);

///////////////////////////////////////////////////////SQS_TB_Test_Batch_Name/////////////////////////////
		
		smtbatch = conn.createStatement();
		String sql1="SELECT * FROM SQS_TEST_BATCH where SQS_TB_Test_Batch_Name = '" + batchname+"'";
		String starttime="SELECT SQS_TB_Execution_DateTime FROM SQS_TEST_BATCH where SQS_TB_Test_Instance_Seq =1 AND SQS_TB_Test_Batch_Name = '" + batchname+"'";
		ResultSet batch1 =smtbatch.executeQuery(starttime);
		while(batch1.next())
		{
			try
			{
				System.out.println("starttime-->"+batch1.getString("SQS_TB_Execution_DateTime"));
				setStartTime(batch1.getString("SQS_TB_Execution_DateTime"));
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
				
			}
		}
		/*String endtime="SELECT SQS_TB_Execution_DateTime FROM SQS_TEST_BATCH where SQS_TB_Test_Instance_Seq =12 AND SQS_TB_Test_Batch_Name = '" + batchname+"'";
		ResultSet batch2 =smtbatch.executeQuery(endtime);
		while(batch2.next())
		{
			try
			{
				System.out.println("endtimmeee-->"+batch2.getString("SQS_TB_Execution_DateTime"));
				setEndTime(batch.getString("SQS_TB_Execution_DateTime"));
			}
			catch(Exception e)
			{
				
			}
		}*/
		
		batch =smtbatch.executeQuery(sql1);
			while(batch.next())
			{
				instancesss[g]=batch.getInt("SQS_TB_Test_Instance_Id");
				testname[g]=batch.getString("SQS_TB_Test_Id_Name").trim();	
				System.out.print(instancesss[g]+"\t");
				System.out.print(testname[g]+"\t");
				g++;
			}

		for (int x = 1; x < g; x++) 
		{
			startTestCase(testname[x]);

///////////////////////////////////////////////////////SQS_IR_Test_Instance_Id/////////////////////////////
			
			instanceruns = conn.createStatement();
			String sql2="SELECT * FROM SQS_INSTANCE_RUNS where SQS_IR_Test_Instance_Id = " + instancesss[x];
			instruns =instanceruns.executeQuery(sql2);	
				while(instruns.next())
				{
					rid[h]=instruns.getInt("SQS_IR_Run_Id");
					System.out.print(rid[h]+"\t");
					h++;
				}
				
///////////////////////////////////////////////////////SQS_TEST_RESULT////////////////////////////////////
				
			stmtTests = conn.createStatement();

			String sql3="SELECT * FROM SQS_TEST_RESULT where SQS_TR_Run_Id = " + (rid[h-1]);
			TestsResults =stmtTests.executeQuery(sql3);
		
				while(TestsResults.next())
				{
					String a=TestsResults.getString("SQS_TR_Actual_Result").trim();
					testlog[i] = a;
					System.out.println(testlog[i]+"\t");
					status[i] = TestsResults.getString("SQS_TR_Step_Status").trim();
					System.out.print(status[i]+"\t");
					screenshot[i] = TestsResults.getString("SQS_TR_ScreenShotPaths").trim();
					System.out.print(screenshot[i]+"\t");
					System.out.print("\n");
					report(testlog[i],status[i],screenshot[i]);
					setStartTime(TestsResults.getString("SQS_TR_Test_Time"));
					//setEndTime(TestsResults.getString("SQS_TR_Test_Time"));
					i++;
				
				}
				
				
			TestsResults.close();
			//System.out.println("vaeofi "+i);
			//	for (int j = 1; j <i; j++) 
			//	{
			//		report(testlog[j],status[j],screenshot[j]);
			//	}


		}

		endtest();
		endflush();
		conn.close();

	}





	public static void report(String desc,String status, String methoddesc) throws Throwable
	{
		try {
			new File(userpath+"\\reports\\").mkdir();

		} catch (WebDriverException e) {
			e.printStackTrace();
		}

		// Write if it is successful or failure or information
		if(status.toUpperCase().equals("PASSED")){
			try {
				test.log(LogStatus.PASS, desc+test.addScreenCapture(methoddesc));
			} catch (Exception e) {
				// TODO: handle exception
			}
			///test.log(LogStatus.PASS, desc+test.addScreenCapture(userpath+"\\reports\\"+a+"_Run_"+i+"\\images\\step "+number+"_"+methoddesc+".jpg"));
		}else if(status.toUpperCase().equals("FAILED")){

			try {
				test.log(LogStatus.FAIL, desc+test.addScreenCapture(methoddesc));

			} catch (Exception e) {
				// TODO: handle exception
			}		
		}else if(status.toUpperCase().equals("INFO")){
			test.log(LogStatus.INFO, desc);
		}	
		
		
	}
	
	public static void setStartTime(String starttime) throws ParseException
	{
		System.out.println(starttime);
		Date start = sdf.parse(starttime);
		test.getTest().setStartedTime(start);
		//test.setStartedTime(start);
		
		
	}
	public static void setEndTime(String endtime) throws ParseException
	{	Date end= sdf.parse(endtime);
	//test.getTest().setEndedTime(end);
	test.setEndedTime(end);
		
	}

	public static void startResult(String batch){

		extent = new ExtentReports(userpath+"\\reports\\"+a+"_GetGoPay_Batch_"+batch+".html", false);

		extent.loadConfig(new File("X:\\Gopi\\Gerty\\Artemis\\src\\main\\java\\Trash\\Firefox-extent-config.xml"));


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
