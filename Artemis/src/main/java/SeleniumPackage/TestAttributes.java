package SeleniumPackage;

import java.io.File;
import java.sql.Connection;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import utilities.EnvironmentSetup;

public class TestAttributes {
	
	private static ExtentTest test;
	private static ExtentReports extent;
	static String ProjectLocation1;
	static String userpath=ProjectLocation1;
	static String a= new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(Calendar.getInstance().getTime());
	
	
	public static int Run_ID = 0;	
	public static String Run_Name = "";	
	public static int Instance_ID = 0;	
	public static String Global_DataBase = "";
	public static String AutomationTool = "";
	public static boolean TestInstanceControl = true;
	public static String Hostname = "Local";
	public static int exitCode = 0;
	public static String Browser = "";
	public static String AutomationApp = "";
	public static String InitialSetUpErrorMessage = "";	
	public static String ProjectLocation = "";
	public static String LogLocation= "";	
	public static String TestLog = "";
	public static String ExecutionLog = "";
	public static String Global_CDT = "";
	public static String FinalStatus= "Not Completed";
	public static String Global_Test_Id = "";
	public static String Global_Workflow_Name = "";
	public static String Global_SharedDataSet = "";
	public static String Global_SharedDataSetList;
	public static String Global_TestDataSet = "";
	public static String Global_TestDataSetList;
	public static String Test_Id = "";
	public static String Workflow_Name = "";
	public static String SharedDataSet = ""; 
	public static String TestDataSet = "";
	public static String Global_Test_ShortName = "";
	public static String Status= "";
	public static String ActualResult= "";
	public static String ScreenShotsPaths = "";
	public static String Step_Description = "";
	public static String Expected_Result = "";
	public static String Run_Type = "";
	public static String Screen_Name = "";
	public static String Field_Name = "";
	public static String Keyword = "";	
	public static String Data = "";
	public static String Locator = "";
	public static String LocatorValue = "";
	public static String ElementType= "";
	public static String ObjectType= "";
	public static String MobileLocator= "";
	public static int iflevel = 0;
	public static int iflevelinaction = 0;
	public static int calltestcounter = 0;	
	public static int looplevel = 0;
	public static int Step_Id = 0;
	public static int Step_Id_Results = 0;	
	public static int StartStepId = 0;	
	public static boolean TakeScreenShotFlag = false;
	public static boolean ExitLoop = false;
	public static boolean WriteResults = false;
	public static boolean breakflag = false;
	public static boolean executiontriggered  = false;
	public static WebElement element = null;
	public static WebElement childelement = null;
	public static WebDriver driver = null;
	public static WebDriverWait webdriverwait = null;
	public static Connection conn = null;
	public static Statement stmtTests = null;	
	public static Statement stmtSteps = null;	
	public static Statement stmt = null;
	public static Statement stmtsid = null;
	public static Statement stmtSDS = null;
	public static Statement stmtTDS = null;
	public static String[] calltesttestid = new String[5];
	public static String[] calltestworkflowname = new String[5];
	public static String[] calltestshareddataset = new String[5];
	public static String[] calltesttestdataset = new String[5];
	public static String[] ELSE = new String[5];
	public static String[] InputParameters = new String[5];
	public static String[] OutputParameters = new String[5];
	public static int[] LoopCounters = new int[5];
	public static int[] StepsIds = new int[5];
	public static boolean[] IfEvaluation = new boolean[5];
	
	public static void Initialization() {
		
		for(int i = 0; i < 5; i++) {
			TestAttributes.calltesttestid[i] = "";
			TestAttributes.calltestworkflowname[i] = "";
			TestAttributes.calltestshareddataset[i] = "";
			TestAttributes.calltesttestdataset[i] = "";			
			TestAttributes.ELSE[i] = "";
			TestAttributes.LoopCounters[i] = 0;
			TestAttributes.StepsIds[i] = 0;
			TestAttributes.IfEvaluation[i] = false;
			setInOutParameters();
		}
	}
	
	public static void setInOutParameters() {
		
		TestAttributes.InputParameters = new String[5];
		TestAttributes.OutputParameters = new String[5];
		
		for (int i = 0; i < 5; i++){
			TestAttributes.InputParameters[i] = "";
			TestAttributes.OutputParameters[i] = "";
		}
	}
	
public static void startResult(String browsername){
		
		ProjectLocation1=EnvironmentSetup.getProjectLocation();

		extent = new ExtentReports(ProjectLocation1+"Extentreporting\\"+a+"_"+browsername+".html", false);

		if(browsername.toLowerCase().trim().contentEquals("chrome"))
		{
		extent.loadConfig(new File(ProjectLocation1+"\\Artemis\\src\\main\\java\\Trash\\Chrome-extent-config.xml"));
		}
		else if(browsername.toLowerCase().trim().contentEquals("firefox"))
		{
			extent.loadConfig(new File(ProjectLocation1+"\\Artemis\\src\\main\\java\\Trash\\Firefox-extent-config.xml"));
		}
		else
		{
			extent.loadConfig(new File(ProjectLocation1+"\\Artemis\\src\\main\\java\\Trash\\Safari-extent-config.xml"));
		}
		


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

	public static void report(String desc,String status, String methoddesc) throws Throwable
	{
		try {
			new File(userpath+"\\Extentreporting\\").mkdir();

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
}