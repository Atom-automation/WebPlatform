package SeleniumPackage;

/*
import static java.nio.file.StandardCopyOption.*;
import java.nio.file.Path;
import java.nio.file.Files;
import com.experitest.selenium.MobileWebDriver;
import org.openqa.selenium.JavascriptExecutor;
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.xmlbeans.XmlException;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Trash.Reporter;
import utilities.*;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class FunctionLibrary extends TestAttributes{
	public static ResultSet TestStepsResults = null;
	private static GenerateData TestData = new GenerateData();
	private static TimeStamp Time = new TimeStamp();
	private static EnvironmentSetup Setup = new EnvironmentSetup();

	//========================================================================================================================================'
	public static void EstablishConnection() {
		try {
			switch(TestAttributes.Global_DataBase.toUpperCase().trim()) {
			case "ACCESS":

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
				 * TestAttributes.conn = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ=" + TestAttributes.ProjectLocation + "BackEnd.accdb");
				 */

				break;
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

	//========================================================================================================================================'
	public static String getData() throws ClassNotFoundException, SQLException, URISyntaxException {
		ResultSet TestDataResults = TestAttributes.stmt.executeQuery("Select * from SQS_TEST_WORKFLOW where SQS_TW_Test_Id = '" + TestAttributes.Test_Id + "' and SQS_TW_Step_Id = " + TestAttributes.Step_Id + " and SQS_TW_Workflow_Name = '" + TestAttributes.Workflow_Name + "'");
		TestDataResults.next();
		String testData = TestDataResults.getString("SQS_TW_Test_Data");
		TestDataResults.close();
		return getActualData(testData);
	}

	//========================================================================================================================================'
	public static void StoreValue(String Var, String Val) throws SQLException, InterruptedException {
		/*String DeleteQuery = "Delete * from SQS_STORED_VALUES where " + 
				"SQS_SV_Test_Id = '" + TestAttributes.Global_Test_Id + "' and " +
				"SQS_SV_Workflow = '" + TestAttributes.Global_Workflow_Name + "' and " +
				"SQS_SV_Shared_Data_Set = '" + TestAttributes.Global_SharedDataSet + "' and " + 
				"SQS_SV_Test_Data_Set = '" + TestAttributes.Global_TestDataSet + "' and " +
				"SQS_SV_Step_Id = '" + TestAttributes.Step_Id + "' and " +
				"SQS_SV_Sub_Test_Details = '" + getSubTestDetails() + "' and " +
				"SQS_SV_Value = '" + Val + "' and " +
				"SQS_SV_DateTime = '" + getCurrentTimeStamp() + "' and " +
				"SQS_SV_Variable = '" + Var + "'";	*/

		String DeleteQuery = "Delete * from SQS_STORED_VALUES where " + 
				"SQS_SV_Test_Id = '" + TestAttributes.Global_Test_Id + "'";


		//Insert into SQS_STORED_VALUES(SQS_SV_Test_Id,SQS_SV_Workflow,SQS_SV_Shared_Data_Set,SQS_SV_Test_Data_Set,SQS_SV_Step_Id,SQS_SV_Sub_Test_Details,SQS_SV_Variable,SQS_SV_Value,SQS_SV_DateTime)
		//Values
		//('GGP-5-TC-002','Default','GetgoURL','Getgo-virtualcard-testdata','7','Script GGP-5-TC-002 Runinng on browser (CHROME)','EmailAddress','mina.adiga@yahoo.co.in','06-08-2018 10-42-48 AM')


		String StoreQuery = "Insert into SQS_STORED_VALUES"+ 
				"(" + 
				"SQS_SV_Test_Id," +
				"SQS_SV_Workflow," +
				"SQS_SV_Shared_Data_Set," +
				"SQS_SV_Test_Data_Set," +
				"SQS_SV_Step_Id," +
				"SQS_SV_Sub_Test_Details," +
				"SQS_SV_Variable," +
				"SQS_SV_Value," +
				"SQS_SV_DateTime" +
				")" +
				"Values"+
				"('"+
				TestAttributes.Global_Test_Id + "','" + 
				TestAttributes.Global_Workflow_Name + "','" + 
				TestAttributes.Global_SharedDataSet + "','" +
				TestAttributes.Global_TestDataSet + "','" +
				TestAttributes.Step_Id + "','" +                      
				getSubTestDetails() + "','" +
				Var + "','" +
				Val + "','" +					   
				getCurrentTimeStamp() 
				+"')";



		/*String StoreQuery1 = "Insert into SQS_STORED_VALUES Values('" +
					   TestAttributes.Global_Test_Id + "','" + 
					   TestAttributes.Global_Workflow_Name + "','" + 
					   TestAttributes.Global_SharedDataSet + "','" +
					   TestAttributes.Global_TestDataSet + "','" +
					   TestAttributes.Step_Id + "','" +                      
					   getSubTestDetails() + "','" +
					   Var + "','" +
 					   Val + "','" +					   
					   getCurrentTimeStamp() +
					   "')";
		 */


		TestAttributes.stmt.executeUpdate(DeleteQuery);
		//Thread.sleep(2000);
		TestAttributes.stmt.executeUpdate(StoreQuery);

	}

	//========================================================================================================================================'
	public static String getActualData(String Data) throws ClassNotFoundException, SQLException, URISyntaxException {
		//LOOP UNTIL NO @@ 

		String ActualData = "";

		String Variable;
		String DSType;

		ResultSet DataSetResults;

		if(TestAttributes.ElementType.toUpperCase().trim().equalsIgnoreCase("TABLE")  || TestAttributes.Keyword.toUpperCase().trim().equalsIgnoreCase("STOREVALUE") || TestAttributes.Keyword.toUpperCase().trim().equalsIgnoreCase("VERIFYATTRIBUTE") || TestAttributes.Keyword.toUpperCase().trim().equalsIgnoreCase("CALLFUNCTION") || TestAttributes.ElementType.toUpperCase().trim().equalsIgnoreCase("PAGE"))
			ActualData = Data;
		else {
			String[] DataSplit = Data.split("@@",-1);

			if(DataSplit.length > 1) {
				Variable = DataSplit[0].trim();	
				DSType = DataSplit[DataSplit.length -1].trim().toUpperCase();

				switch(DSType) {
				case "CONFIGVALUE":	
					ActualData = getConfigValue(Variable).trim();					
					break;

				case "SHAREDDATASET":				
					DataSetResults = TestAttributes.stmt.executeQuery("Select * from SQS_TEST_DATASET where SQS_TD_DataSet_Type = 'SharedDataSet' and SQS_TD_Test_Id like '%" + TestAttributes.Test_Id + "%' and SQS_TD_Data_Set = '" + TestAttributes.SharedDataSet + "' and SQS_TD_Param_Name = '" + Variable + "'");
					DataSetResults.next();
					ActualData = DataSetResults.getString("SQS_TD_Param_Value");
					break;

				case "TESTDATASET":				
					DataSetResults = TestAttributes.stmt.executeQuery("Select * from SQS_TEST_DATASET where SQS_TD_DataSet_Type = 'TestDataSet' and SQS_TD_Test_Id = '" + TestAttributes.Test_Id + "' and SQS_TD_Data_Set = '" + TestAttributes.TestDataSet + "' and SQS_TD_Param_Name = '" + Variable + "'");
					DataSetResults.next();
					ActualData = DataSetResults.getString("SQS_TD_Param_Value");
					break;

				case "STOREDVALUE":
					String[] VaraibleSplit = Variable.split("::",-1);

					String TestIDToUse;
					String WFToUse;
					String SDSToUse;
					String TDSToUse;
					String VariableToUse;

					if(VaraibleSplit.length>1){
						TestIDToUse = VaraibleSplit[0].trim();
						WFToUse = VaraibleSplit[1].trim();
						SDSToUse = VaraibleSplit[2].trim();
						TDSToUse = VaraibleSplit[3].trim();
						VariableToUse = VaraibleSplit[4].trim();
					} else {
						TestIDToUse = TestAttributes.Global_Test_Id;
						WFToUse = TestAttributes.Global_Workflow_Name;
						SDSToUse = TestAttributes.Global_SharedDataSet;
						TDSToUse = TestAttributes.Global_TestDataSet;
						VariableToUse = Variable;
					}

					DataSetResults = TestAttributes.stmt.executeQuery("Select * from SQS_STORED_VALUES where SQS_SV_Test_Id = '" + TestIDToUse  + "' and SQS_SV_Workflow = '" + WFToUse  + "' and SQS_SV_Shared_Data_Set = '" + SDSToUse  + "' and SQS_SV_Test_Data_Set = '" + TDSToUse  + "' and SQS_SV_Variable = '" + VariableToUse  + "'");
					DataSetResults.next();
					ActualData = DataSetResults.getString("SQS_SV_Value");
					break;

				case "CALCULATEDVALUE":
					String CalculateInput = "";
					for(int i = 0;i<DataSplit.length-1;i++)
						CalculateInput = CalculateInput + DataSplit[i] + "@@";

					CalculateInput = CalculateInput.substring(0,CalculateInput.length()-2).trim();

					ActualData = "" + Calculate(CalculateInput) + "";
				}
			} else
				ActualData = Data;
		}
		return ActualData;
	}

	//========================================================================================================================================'
	public static WebElement getElement() {
		TestAttributes.element = null;
		try {
			if (!(TestAttributes.Screen_Name.trim().equalsIgnoreCase("") && TestAttributes.Field_Name.trim().equalsIgnoreCase(""))) {
				ResultSet ORResults = TestAttributes.stmt.executeQuery("Select * from SQS_OBJECT_REPOSITORY where SQS_OR_Screen = '" + TestAttributes.Screen_Name + "' and SQS_OR_Field = '" + TestAttributes.Field_Name + "' and SQS_OR_Tool = '" + TestAttributes.AutomationTool +"'");				
				ORResults.next();			

				if (TestAttributes.AutomationApp.equalsIgnoreCase("WEB")) {					

					TestAttributes.Locator = ORResults.getString("SQS_OR_Locator").trim();
					TestAttributes.LocatorValue = ORResults.getString("SQS_OR_Locator_Value").trim();

					String Class1 = ORResults.getString("SQS_OR_Class1").trim();
					String Class2 = ORResults.getString("SQS_OR_Class2").trim();
					String Class3 = ORResults.getString("SQS_OR_Class3").trim();

					if(Class3.equalsIgnoreCase(""))
						if(Class2.equalsIgnoreCase(""))													
							TestAttributes.ElementType = Class1;						
						else							
							TestAttributes.ElementType = Class2;						
					else
						TestAttributes.ElementType = Class3;					

					TestAttributes.LocatorValue = replaceXPathParameters(TestAttributes.LocatorValue);

					switch (TestAttributes.Locator.toUpperCase().trim()) {
					case "ID":
						//TestAttributes.webdriverwait.until(ExpectedConditions.visibilityOfElementLocated((By.id(TestAttributes.LocatorValue))));
						TestAttributes.element = TestAttributes.webdriverwait.until(ExpectedConditions.elementToBeClickable((By.id(TestAttributes.LocatorValue))));
						break;

					case "NAME":
						//TestAttributes.webdriverwait.until(ExpectedConditions.visibilityOfElementLocated((By.name(TestAttributes.LocatorValue))));
						TestAttributes.element = TestAttributes.webdriverwait.until(ExpectedConditions.elementToBeClickable((By.name(TestAttributes.LocatorValue))));
						break;

					case "TAG":
						//TestAttributes.webdriverwait.until(ExpectedConditions.visibilityOfElementLocated((By.tagName(TestAttributes.LocatorValue))));
						TestAttributes.element = TestAttributes.webdriverwait.until(ExpectedConditions.elementToBeClickable((By.tagName(TestAttributes.LocatorValue))));
						break;

					case "LINKTEXT":
						//TestAttributes.webdriverwait.until(ExpectedConditions.visibilityOfElementLocated((By.linkText(TestAttributes.LocatorValue))));
						TestAttributes.element = TestAttributes.webdriverwait.until(ExpectedConditions.elementToBeClickable((By.linkText(TestAttributes.LocatorValue))));
						break;

					case "XPATH":
						//TestAttributes.webdriverwait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath(TestAttributes.LocatorValue))));
						TestAttributes.element = TestAttributes.webdriverwait.until(ExpectedConditions.elementToBeClickable((By.xpath(TestAttributes.LocatorValue))));
						break;

					case "CLASS":
						//TestAttributes.webdriverwait.until(ExpectedConditions.visibilityOfElementLocated((By.className(TestAttributes.LocatorValue))));
						TestAttributes.element = TestAttributes.webdriverwait.until(ExpectedConditions.elementToBeClickable((By.className(TestAttributes.LocatorValue))));
						break;

					case "CSS":
						//TestAttributes.webdriverwait.until(ExpectedConditions.visibilityOfElementLocated((By.cssSelector(TestAttributes.LocatorValue))));
						TestAttributes.element = TestAttributes.webdriverwait.until(ExpectedConditions.elementToBeClickable((By.cssSelector(TestAttributes.LocatorValue))));
						break;
					}
				}

				if (TestAttributes.AutomationApp.equalsIgnoreCase("MOBILE")) {
					TestAttributes.Locator = ORResults.getString("SQS_OR_Locator").trim().toLowerCase();
					TestAttributes.LocatorValue = ORResults.getString("SQS_OR_Locator_Value").trim();
					TestAttributes.ElementType = ORResults.getString("SQS_OR_Class3").trim();

					TestAttributes.ObjectType = ORResults.getString("SQS_OR_Object_Type").trim();					
					TestAttributes.MobileLocator = TestAttributes.Locator + "=" + TestAttributes.LocatorValue;
				}
			} else {
				TestAttributes.element = null;				
				TestAttributes.Locator = "";
				TestAttributes.LocatorValue = "";
				TestAttributes.ElementType = "";
			}
		} catch(Exception e) {
			TestAttributes.element = null;			
			TestAttributes.Locator = "";
			TestAttributes.LocatorValue = "";
			TestAttributes.ElementType = "";			
		}

		return TestAttributes.element;
	}

	//========================================================================================================================================'
	public static void logSetup() {	
		try {

			System.setOut(new PrintStream(new FileOutputStream(TestAttributes.LogLocation + TestAttributes.TestLog, true)));
			System.setErr(new PrintStream(new FileOutputStream(TestAttributes.LogLocation + TestAttributes.TestLog, true)));
		} catch(Exception e) {
			TestAttributes.InitialSetUpErrorMessage = TestAttributes.InitialSetUpErrorMessage + "Error while creating test log file. ";
		}
	}

	//========================================================================================================================================'
	public static void CaptureScreenShot() throws IOException, InvalidFormatException, XmlException, InterruptedException {

		if (Settings.Generate_Execution_Log.equalsIgnoreCase("ON")) {
			if (TestAttributes.TakeScreenShotFlag==true) {

				//Thread.sleep(2000);
				String StepScreenShotPath = TestAttributes.LogLocation	+ TestAttributes.Test_Id + "_" + TestAttributes.Step_Id + "_" + getCurrentTimeStamp() + ".png";

				if(TestAttributes.AutomationApp.equalsIgnoreCase("WEB")) {
					File src=((TakesScreenshot)TestAttributes.driver).getScreenshotAs(OutputType.FILE);		    
					FileUtils.copyFile(src, new File(StepScreenShotPath));
				}

				if (TestAttributes.AutomationApp.equalsIgnoreCase("MOBILE")) {				    
					/*
			         	String str0 = TestAttributes.mobiledriver.client.capture("Capture");
			        	Path psource =new File(str0).toPath();
			        	Path pdest =new File(StepScreenShotPath).toPath();
			        	Files.copy(psource,pdest,REPLACE_EXISTING);
					 */

				}

				if (TestAttributes.ScreenShotsPaths.trim().equalsIgnoreCase(""))
					TestAttributes.ScreenShotsPaths = StepScreenShotPath;
				else
					TestAttributes.ScreenShotsPaths = TestAttributes.ScreenShotsPaths + ";" + StepScreenShotPath;
			}
		}

	}

	//========================================================================================================================================'
	public static boolean isNextScreenDifferent(ResultSet TempTestStepsResults) throws SQLException {
		boolean returnflag = false;	

		String Current_Screen = TestAttributes.Screen_Name;
		String Next_Screen;

		if (TempTestStepsResults.isLast()==true)
			Next_Screen = "Next_Screen";
		else {
			TempTestStepsResults.next();	
			Next_Screen = TempTestStepsResults.getString("SQS_TS_Screen_Name");
			TempTestStepsResults.previous();
		}

		if (!TestAttributes.Keyword.trim().equalsIgnoreCase("LAUNCHAPP")) {
			if (!Current_Screen.trim().equalsIgnoreCase(Next_Screen.trim()))
				returnflag = true;
			else
				returnflag = false;
		}

		return returnflag;		
	}

	//========================================================================================================================================'
	public static void WriteResults() throws Throwable {

		try {

			String StatusToUpload = "";

			if (TestAttributes.Status.equalsIgnoreCase("Error"))
				StatusToUpload = "Failed";
			else
				StatusToUpload = TestAttributes.Status;

			TestAttributes.Step_Id_Results = TestAttributes.Step_Id_Results + 1;
			TestAttributes.Step_Description = TestAttributes.Step_Description.replace("'", "''"); 
			TestAttributes.Expected_Result = TestAttributes.Expected_Result.replace("'", "''");
			TestAttributes.ActualResult = TestAttributes.ActualResult.replace("'", "''");	
			String ResultsQuery = "Insert INTO SQS_TEST_RESULT " + 
					"(" + 
					"SQS_TR_Run_Id," +
					"SQS_TR_Step_Id," +
					"SQS_TR_Sub_Test_Details," +
					"SQS_TR_Step_Description," +
					"SQS_TR_Expected_Result," +
					"SQS_TR_Actual_Result," +
					"SQS_TR_Step_Status," +
					"SQS_TR_User_Name," +
					"SQS_TR_Test_Time," +
					"SQS_TR_Run_Number," +
					"SQS_TR_ScreenShotPaths" +
					")" +
					" VALUES" +
					"(" +
					TestAttributes.Run_ID + "," +
					TestAttributes.Step_Id_Results + ",'" +
					getSubTestDetails() + "','" +
					TestAttributes.Step_Description + "','" +
					TestAttributes.Expected_Result + "','" +
					TestAttributes.ActualResult + "','" +
					StatusToUpload + "','" +
					System.getProperty("user.name") + "','" +
					getCurrentTimeStamp() + "'," + 
					TestAttributes.Run_ID + ",'" +
					TestAttributes.ScreenShotsPaths  + 
					"')";	
			;
			report(TestAttributes.ActualResult,StatusToUpload,TestAttributes.ScreenShotsPaths);
			TestAttributes.stmt.executeUpdate(ResultsQuery);
			TestAttributes.WriteResults = true;

		}catch(Exception e) {

			System.out.println("Failed to write results BIGBEE ..... Reason: "  + e.getMessage());

		}





	}

	//========================================================================================================================================'
	public static String getConfigValue(String ConfigName) throws SQLException, ClassNotFoundException, URISyntaxException {
		ResultSet ConfigResults = TestAttributes.stmt.executeQuery("Select * from SQS_CONFIG_DATA where SQS_CD_Config_Name = '" + ConfigName + "'");
		ConfigResults.next();
		String configResult = ConfigResults.getString("SQS_CD_Config_Value");
		ConfigResults.close();
		return configResult;
	}

	//========================================================================================================================================'
	public static void DriverSetup() {		
		try {

			if (TestAttributes.AutomationApp.trim().equalsIgnoreCase("WEB")) {
				
				//TestIE ie=new TestIE();
				//TestAttributes.driver = ie.returniedriver();
				TestAttributes.driver = Setup.getWebDriver(Settings.Browser);
			
				//TestAttributes.driver.manage().window().maximize();
				if(!(TestAttributes.Browser.contains("mobile"))) {
					TestAttributes.driver.manage().window().maximize();
				}
				TestAttributes.driver.manage().timeouts().implicitlyWait(Settings.implicitlyWait, TimeUnit.SECONDS);
				TestAttributes.driver.manage().timeouts().pageLoadTimeout(Settings.pageLoadTimeout,TimeUnit.SECONDS);
				TestAttributes.driver.manage().timeouts().setScriptTimeout(Settings.setScriptTimeout,TimeUnit.SECONDS);
				TestAttributes.webdriverwait = new WebDriverWait(TestAttributes.driver, Settings.webdriverwaittime);
			}		
			if (TestAttributes.AutomationApp.trim().equalsIgnoreCase("MOBILE")) {				


				/*
				TestAttributes.mobiledriver =  new MobileWebDriver(Settings.Host, Settings.Port, TestAttributes.ProjectLocation + "SeeTestWorkspace/", "xml", "reports", "Untitled");			  	
			  	TestAttributes.mobiledriver.client.waitForDevice("@os='android' AND @remote='false'", 30000);
			  	TestAttributes.mobiledriver.setDevice(Settings.Device);
			  	TestAttributes.mobiledriver.client.openDevice();
				 */
			}
		} catch (Exception e) {
			TestAttributes.InitialSetUpErrorMessage = TestAttributes.InitialSetUpErrorMessage + "Error in Driver setup. ";
			System.out.println("Artemis:ERROR ("+Time.getCurrentTimeStamp()+") " + "System encountered an error while launching the " + Settings.Browser + " browser, Error: " + e.getMessage());
		}
	}

	//========================================================================================================================================'
	public static boolean getKeywordInvokeFlag() {
		boolean returnvalue = true;
		if (!TestAttributes.Data.equalsIgnoreCase("NOT APPLICABLE")) {
			if (TestAttributes.iflevelinaction==0)
				returnvalue = true; 
			else {
				if (TestAttributes.IfEvaluation[TestAttributes.iflevelinaction]==true) {			
					if (TestAttributes.ELSE[TestAttributes.iflevelinaction].equalsIgnoreCase("True"))				
						returnvalue = false;
					else				
						returnvalue = true;
				}

				if (TestAttributes.IfEvaluation[TestAttributes.iflevelinaction]==false) {			
					if(TestAttributes.ELSE[TestAttributes.iflevelinaction].equalsIgnoreCase("True"))				
						returnvalue = true;
					else				
						returnvalue = false;
				}
			}

			if (TestAttributes.Keyword.equalsIgnoreCase("ELSE"))
				if(TestAttributes.iflevel == TestAttributes.iflevelinaction)
					returnvalue = true;

			if (TestAttributes.Keyword.equalsIgnoreCase("ENDIF"))
				if(TestAttributes.iflevel == TestAttributes.iflevelinaction)
					returnvalue = true;

			if (TestAttributes.Keyword.equalsIgnoreCase("IF"))
				TestAttributes.iflevel = TestAttributes.iflevel + 1;

			if (TestAttributes.Keyword.equalsIgnoreCase("ENDIF"))					
				TestAttributes.iflevel = TestAttributes.iflevel - 1;

			if (TestAttributes.ExitLoop)
				if(!TestAttributes.Keyword.equalsIgnoreCase("ENDLOOP") && !TestAttributes.Keyword.equalsIgnoreCase("IF") && !TestAttributes.Keyword.equalsIgnoreCase("ELSE") && !TestAttributes.Keyword.equalsIgnoreCase("ENDIF"))
					returnvalue = false;

		} else
			returnvalue = false;

		return returnvalue;
	}

	//========================================================================================================================================'
	public static void getStatus() {
		try {
			ResultSet StatusResults = TestAttributes.stmt.executeQuery("Select * from SQS_TEST_RESULT where SQS_TR_Run_Id = " + TestAttributes.Run_ID + " and SQS_TR_Step_Status = 'Failed'");
			StatusResults.next();

			if(StatusResults.getRow()!=0)
				TestAttributes.FinalStatus = "Failed";
			else        		
				if(TestAttributes.WriteResults==true && TestAttributes.breakflag==false)
					TestAttributes.FinalStatus = "Passed";
		} catch (Exception e) {
			System.out.println("Error while retrieving the final status.");
		}
	}

	//========================================================================================================================================'
	public static void ConsolidateSS() {
		if (Settings.Generate_Execution_Log.equalsIgnoreCase("ON"))
			if (SSInWord.CreateSSDoc() == false)
				System.out.println("Error while creating screenshots document.");
	}

	//========================================================================================================================================'
	public static void getProjectLocation() {
		try {			
			String PackageLocation = Driver.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();

			String[] PackageLocationSplit = PackageLocation.split("/");

			String CodePath = "";

			if (!PackageLocation.toLowerCase().contains(".jar"))
				for (int i = 1;i<PackageLocationSplit.length-3;i++)
					CodePath = CodePath + PackageLocationSplit[i] + "/";
			else				
				for (int i = 1;i<PackageLocationSplit.length-1;i++)
					CodePath = CodePath + PackageLocationSplit[i] + "/";

			TestAttributes.ProjectLocation = CodePath.trim();
			TestAttributes.LogLocation = TestAttributes.ProjectLocation + "Execution Logs/";		
		} catch (Exception e) {
			TestAttributes.InitialSetUpErrorMessage = TestAttributes.InitialSetUpErrorMessage + "Error while retrieving the project location. ";
		}
	}

	//========================================================================================================================================'
	public static int Calculate(String CalculateInput) throws NumberFormatException, ClassNotFoundException, SQLException, URISyntaxException{
		CalculateInput = CalculateInput + "+";
		int CalculatedValue = 0;

		String CurrentInput = "";

		String previous = "+";

		for (int i = 0; i < CalculateInput.length() ; i++) {
			String CurrentString = CalculateInput.substring(i,i+1);

			if (!CurrentString.equalsIgnoreCase("+") && !CurrentString.equalsIgnoreCase("-") && !CurrentString.equalsIgnoreCase("*") && !CurrentString.equalsIgnoreCase("/"))
				CurrentInput = CurrentInput + CurrentString;
			else {
				switch (previous) {
				case "+":
					CalculatedValue = CalculatedValue + Integer.parseInt(getActualData(CurrentInput.trim()).trim());
					break;

				case "-":
					CalculatedValue = CalculatedValue - Integer.parseInt(getActualData(CurrentInput.trim()).trim());
					break;

				case "*":
					CalculatedValue = CalculatedValue * Integer.parseInt(getActualData(CurrentInput.trim()).trim());
					break;

				case "/":
					CalculatedValue = CalculatedValue / Integer.parseInt(getActualData(CurrentInput.trim()).trim());
					break;				
				}

				switch (CurrentString) {
				case "+":
					previous = "+";
					break;

				case "-":
					previous = "-";
					break;

				case "*":
					previous = "*";
					break;

				case "/":
					previous = "/";
					break;				
				}
				CurrentInput = "";
			}
		}

		return CalculatedValue;
	}

	//========================================================================================================================================'
	public static boolean isNumeric(String string) {
		try {
			Long.parseLong(string);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	//========================================================================================================================================'
	public static String getSubTestDetails() {
		String subtestdetails;

		/*
		  if(TestAttributes.TestInstanceControl)
			  subtestdetails =  TestAttributes.Browser + "::" +
			  			TestAttributes.Test_Id + "::" +
		 				TestAttributes.Workflow_Name + "::" +
		 				TestAttributes.SharedDataSet + "::" +
		 				TestAttributes.TestDataSet + "-" +
		 				TestAttributes.Step_Id;
		  else
			  subtestdetails =  TestAttributes.Browser + "::" +
			  		TestAttributes.Test_Id + "::" +
	 				TestAttributes.Workflow_Name + "::" +
	 				TestAttributes.SharedDataSet + "::" +
	 				TestAttributes.TestDataSet + "-" +
	 				TestAttributes.Step_Id + "-" +
	 				TestAttributes.AutomationTool;
		 */

		subtestdetails = "Script " + TestAttributes.Test_Id + " Runinng on browser (" + TestAttributes.Browser.toUpperCase() + ")";

		return subtestdetails;
	}

	//========================================================================================================================================'
	public static boolean getBreakFlag() {
		TestAttributes.breakflag = false;

		if (!(TestAttributes.Keyword.equalsIgnoreCase("VerifyValue") || TestAttributes.Keyword.equalsIgnoreCase("IsDataExist") || 
				TestAttributes.Keyword.equalsIgnoreCase("IsEnable") || TestAttributes.Keyword.equalsIgnoreCase("IsExist"))) {
			if (TestAttributes.Status.equals("Error")) {
				String[] Run_Type_Split = TestAttributes.Run_Type.split("\\|\\|", -1);

				if (Run_Type_Split.length == 2) {
					if (!Run_Type_Split[1].trim().equalsIgnoreCase("Optional"))
						TestAttributes.breakflag = true;
				} else
					TestAttributes.breakflag = true;
			}

			if (TestAttributes.Status.equals("Failed")) {
				String[] Run_Type_Split = TestAttributes.Run_Type.split("\\|\\|", -1);

				if (Run_Type_Split.length == 2) {
					if (Run_Type_Split[1].trim().equalsIgnoreCase("Critical"))
						TestAttributes.breakflag=true;
				}
			}
		}

		return TestAttributes.breakflag;
	}

	//========================================================================================================================================'
	public static int getRunID() {
		try {

			ResultSet RunID = TestAttributes.stmt.executeQuery("SELECT * FROM SQS_KEY_VALUE where SQS_KV_KEY = 'LastTestRunId'");
			RunID.next();
			TestAttributes.Run_ID = RunID.getInt("SQS_KV_VALUE") + 1;

			TestAttributes.stmt.executeUpdate("Update SQS_KEY_VALUE Set SQS_KV_Value = " + TestAttributes.Run_ID + " where SQS_KV_Key = 'LastTestRunId'");

		} catch (Exception e) {
			TestAttributes.InitialSetUpErrorMessage = TestAttributes.InitialSetUpErrorMessage + "Error while retrieving Run ID. ";
		}

		return TestAttributes.Run_ID;
	}

	/*
	 	public static int getRunID() {
		try {
			ResultSet RunID;

			if (TestAttributes.StartStepId <= 2) {
				RunID = TestAttributes.stmt.executeQuery("SELECT * FROM SQS_KEY_VALUE where SQS_KV_KEY = 'LastTestRunId'");
				RunID.next();
				TestAttributes.Run_ID = RunID.getInt("SQS_KV_VALUE") + 1;

				TestAttributes.stmt.executeUpdate("Update SQS_KEY_VALUE Set SQS_KV_Value = " + TestAttributes.Run_ID + " where SQS_KV_Key = 'LastTestRunId'");

			} else {
				RunID = TestAttributes.stmt.executeQuery("SELECT * FROM SQS_INSTANCE_RUNS where SQS_IR_Test_Instance_Id = " + TestAttributes.Instance_ID + " order by SQS_IR_Run_Id desc");
				RunID.next();
				TestAttributes.Run_ID = RunID.getInt("SQS_IR_Run_Id");
			}

		} catch (Exception e) {
			TestAttributes.InitialSetUpErrorMessage = TestAttributes.InitialSetUpErrorMessage + "Error while retrieving Run ID. ";
		}

		return TestAttributes.Run_ID;
	}
	 */


	//========================================================================================================================================'
	public static void UpdateInstanceRuns(String when) {
		try {
			String UpdateQuery = "";

			if (when.equalsIgnoreCase("INITIAL"))				
				UpdateQuery = "Insert into SQS_INSTANCE_RUNS Values(" + TestAttributes.Instance_ID  + "," +
						TestAttributes.Run_ID + ",'" +
						TestAttributes.Run_Name + "','" + 
						TestAttributes.FinalStatus + "','" +
						getCurrentTimeStamp() + "','" +
						TestAttributes.TestLog + "','" +
						TestAttributes.ExecutionLog +  "')";

			else
				UpdateQuery = "Update SQS_INSTANCE_RUNS set SQS_IR_Run_Status = '" + TestAttributes.FinalStatus + "' " + 
						"where SQS_IR_Run_Id = " + TestAttributes.Run_ID;

			TestAttributes.stmt.executeUpdate(UpdateQuery);

			//Thread.sleep(5000);

		} catch (Exception e) {
			if (when.equalsIgnoreCase("INITIAL"))
				TestAttributes.InitialSetUpErrorMessage = TestAttributes.InitialSetUpErrorMessage + "Error while updating Instance Runs Table. ";
			if (when.equalsIgnoreCase("FINAL"))
				System.out.println("Error while updating Instance Runs Table.");
		}
	}	

	//========================================================================================================================================'
	public static String getCurrentTimeStamp() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(Settings.DateFormat);
		return sdf.format(date);
	}

	//========================================================================================================================================'
	public static void setParameters() throws ClassNotFoundException, SQLException, URISyntaxException {	
		String[] DataSplit = TestAttributes.Data.split("\\|\\|");

		if (DataSplit.length == 2) {
			getInputValues(DataSplit[1].trim());			
		}

		if (DataSplit.length == 3) {
			getInputValues(DataSplit[1].trim());
			getOutputValues(DataSplit[2].trim());
		}
	}

	public static void getInputValues(String Input) throws ClassNotFoundException, SQLException, URISyntaxException {
		String[] InputSplit = Input.split(";;");

		for (int i = 0; i < InputSplit.length; i++) {
			TestAttributes.InputParameters = Arrays.copyOf(TestAttributes.InputParameters, TestAttributes.InputParameters.length + 1);

			InputSplit[i] = InputSplit[i].trim();

			if (InputSplit[i].contains("\""))			
				TestAttributes.InputParameters[i] = InputSplit[i].substring(1,InputSplit[i].length()-1); 
			else{
				TestAttributes.Keyword = "TEMP";
				TestAttributes.InputParameters[i] = getActualData(InputSplit[i].trim());
				TestAttributes.Keyword = "CALLFUNCTION";
			}
		}
	}

	public static void getOutputValues(String Output) throws ClassNotFoundException, SQLException, URISyntaxException {
		String[] OutputSplit = Output.split(";;");

		for(int i=0; i < OutputSplit.length; i++) {
			TestAttributes.OutputParameters = Arrays.copyOf(TestAttributes.OutputParameters, TestAttributes.OutputParameters.length + 1);

			OutputSplit[i] = OutputSplit[i].trim();

			if (OutputSplit[i].contains("\""))
				TestAttributes.OutputParameters[i] = OutputSplit[i].substring(1,OutputSplit[i].length()-1);
			else{
				TestAttributes.Keyword = "TEMP";
				TestAttributes.OutputParameters[i] = getActualData(OutputSplit[i].trim());
				TestAttributes.Keyword = "CALLFUNCTION";
			}
		}
	}

	//========================================================================================================================================'
	public static String getDataSet (String DataSetType,String DataSetName) throws SQLException{
		String DataSetNameToReturn;

		if (DataSetName.equalsIgnoreCase("")) {
			ResultSet DSResults = TestAttributes.stmt.executeQuery("SELECT * FROM SQS_TEST_DATASET where UCASE(SQS_TD_DataSet_Type) = '" + DataSetType.toUpperCase() + "'" +
					" and SQS_TD_Test_Id like '%" + TestAttributes.Test_Id + "%' order by SQS_TD_DataSet_Id");

			if (DSResults.getRow() != 0) {        		
				DSResults.next();
				DataSetNameToReturn = DSResults.getString("SQS_TD_Data_Set");
			} else
				DataSetNameToReturn = DataSetName;
		} else
			DataSetNameToReturn = DataSetName;

		return DataSetNameToReturn;
	}

	//========================================================================================================================================'

	public static void getAutomationApp(){

		if(TestAttributes.AutomationTool.equalsIgnoreCase("SELENIUM"))
			TestAttributes.AutomationApp = "WEB";

		if(TestAttributes.AutomationTool.equalsIgnoreCase("SEETEST"))
			TestAttributes.AutomationApp = "MOBILE";
	}

	//========================================================================================================================================'

	public static void getStepIdResults() throws SQLException{

		ResultSet SIDResults = TestAttributes.stmtsid.executeQuery("SELECT * FROM SQS_TEST_RESULT where SQS_TR_Run_Id = " + TestAttributes.Run_ID);

		SIDResults.next();
		if (SIDResults.getRow() != 0) { 
			SIDResults.last();
			TestAttributes.Step_Id_Results = SIDResults.getInt("SQS_TR_Step_Id");
		}
		else
			TestAttributes.Step_Id_Results = 0;

	}

	//========================================================================================================================================'

	public static void CloseDriver(){

		try{
			if(TestAttributes.AutomationApp.equalsIgnoreCase("WEB"))
				TestAttributes.driver.quit();
			if(TestAttributes.AutomationApp.equalsIgnoreCase("MOBILE"))
				System.out.println("Mobile keywords are not configured ......");
			//TestAttributes.mobiledriver.quit();				
		}
		catch(Exception e){

		}

	}


	//========================================================================================================================================'

	private static String replaceXPathParameters(String locatorValue) throws Exception {
		Pattern p = Pattern.compile("\\$\\{([A-Za-z^\\^\\$^\\{}][A-Za-z0-9-_^\\}^\\$^\\{]*)\\}");
		Matcher m = p.matcher(locatorValue);
		while(m.find()) {
			String varName = m.group(1);
			String val = FunctionLibrary.getActualData(varName + "@@StoredValue");
			if(val==null) {
				System.out.println("Variable '" + varName + "' not declared!");
				throw new Exception("Variable '" + varName + "' not declared!");
			}
			locatorValue = locatorValue.replace("${" + varName + "}", val);
		}
		return locatorValue;
	}


	//========================================================================================================================================'	


	//========================================================================================================================================'
	public static void Driver() throws Throwable {

		TestAttributes.AutomationApp = Setup.getAutomationApplication(TestAttributes.AutomationTool);
		TestAttributes.ProjectLocation = Setup.getProjectLocation();
		TestAttributes.LogLocation = TestAttributes.ProjectLocation + "Execution Logs/";
		Setup.createDatabaseConnection(TestAttributes.Global_DataBase);

		TestParameters TestParameters = new TestParameters();
		String[] SplitTestInfo = TestParameters.getTestInfo().split("_",-1);

		TestAttributes.Global_Test_Id = SplitTestInfo[0].trim();
		TestAttributes.Global_Test_ShortName = SplitTestInfo[1].trim();
		System.out.println("Testcase Description--->>>"+TestAttributes.Global_Test_ShortName);
		startTestCase(TestAttributes.Global_Test_ShortName);
		TestAttributes.Global_Workflow_Name = TestParameters.getTestWorkflow();

		if (TestAttributes.TestInstanceControl) {

			TestAttributes.Global_SharedDataSetList = TestParameters.getSharedDataset();
			TestAttributes.Global_TestDataSetList = TestParameters.getTestDataset();			
			TestAttributes.Run_ID = getRunID();
			TestAttributes.TestLog = TestAttributes.Global_Test_Id + " Run " + TestAttributes.Run_ID + " Test log.txt";				
			TestAttributes.ExecutionLog = TestAttributes.Global_Test_Id + " Run " + TestAttributes.Run_ID + " Execution log.docx";
			UpdateInstanceRuns("Initial");
		} else {
			String strRunQuery = "Select * From SQS_INSTANCE_RUNS Where SQS_IR_Run_Id = " + TestAttributes.Run_ID;
			ResultSet Run = TestAttributes.stmtSDS.executeQuery(strRunQuery);
			Run.next();
			TestAttributes.TestLog = Run.getString("SQS_IR_TestLog_Link").trim();
			TestAttributes.ExecutionLog = Run.getString("SQS_IR_ExecutionLog_Link").trim();
			Run.close();
		}

		////
		
		
		
		//logSetup();
		Settings.Settings();	
		TestAttributes.Initialization();
		DriverSetup();		
		getStepIdResults();
		if (!TestAttributes.InitialSetUpErrorMessage.trim().equalsIgnoreCase(""))
			System.out.println(TestAttributes.InitialSetUpErrorMessage.trim() + "\n" + "Execution is not triggered");
		else {
			String strSharedDataSetQuery = "";
			String strSharedDataSet = "";
			String strTestDataSetQuery = "";
			String strTestDataSet = "";
			if (TestAttributes.Global_SharedDataSetList.equalsIgnoreCase("ALL"))
				strSharedDataSetQuery = "Select Distinct SQS_TD_Data_Set From SQS_TEST_DATASET Where SQS_TD_Test_Id Like '%" + TestAttributes.Global_Test_Id + "%' And SQS_TD_DataSet_Type = 'SharedDataSet'";
			else {
				if (!TestAttributes.Global_SharedDataSetList.equalsIgnoreCase("")) {
					String[] arrSharedDataSet = TestAttributes.Global_SharedDataSetList.split(";;");				
					for(int i = 0; i < arrSharedDataSet.length; i++)				
						strSharedDataSet = strSharedDataSet + ",'" + arrSharedDataSet[i].trim() + "'";			
					strSharedDataSet = strSharedDataSet.substring(1,strSharedDataSet.length());
					strSharedDataSetQuery = "Select Distinct SQS_TD_Data_Set From SQS_TEST_DATASET Where SQS_TD_Test_Id Like '*" + TestAttributes.Global_Test_Id + "*' And SQS_TD_DataSet_Type = 'SharedDataSet' And SQS_TD_Data_Set In (" + strSharedDataSet + ")";
				} else
					strSharedDataSetQuery = "Select Distinct '' As SQS_TD_Data_Set from SQS_TEST_DATASET";
			}	
			ResultSet SDS = TestAttributes.stmtSDS.executeQuery(strSharedDataSetQuery);
			while (SDS.next()) {
				TestAttributes.Global_SharedDataSet = SDS.getString("SQS_TD_Data_Set").trim();
				strTestDataSet = "";
				if (TestAttributes.Global_TestDataSetList.equalsIgnoreCase("ALL"))
					strTestDataSetQuery = "Select Distinct SQS_TD_Data_Set From SQS_TEST_DATASET Where SQS_TD_Test_Id = '" + TestAttributes.Global_Test_Id + "' And SQS_TD_DataSet_Type = 'TestDataSet'";
				else {
					if (!TestAttributes.Global_TestDataSetList.equalsIgnoreCase("")) {
						String[] arrTestDataSet = TestAttributes.Global_TestDataSetList.split(";;");
						for(int i = 0; i < arrTestDataSet.length; i++)
							strTestDataSet = strTestDataSet + ",'" + arrTestDataSet[i].trim() + "'";
						strTestDataSet = strTestDataSet.substring(1, strTestDataSet.length());
						strTestDataSetQuery = "Select Distinct SQS_TD_Data_Set From SQS_TEST_DATASET Where SQS_TD_Test_Id = '" + TestAttributes.Global_Test_Id + "' And SQS_TD_DataSet_Type = 'TestDataSet' And SQS_TD_Data_Set In (" + strTestDataSet + ")";
					} else
						strTestDataSetQuery = "Select Distinct '' As SQS_TD_Data_Set from SQS_TEST_DATASET";
				}
				ResultSet TDS = TestAttributes.stmtTDS.executeQuery(strTestDataSetQuery);
				while (TDS.next()) {
					TestAttributes.Global_TestDataSet = TDS.getString("SQS_TD_Data_Set").trim();					
					ExecuteSteps (TestAttributes.Global_Test_Id, TestAttributes.Global_Workflow_Name, TestAttributes.Global_SharedDataSet, TestAttributes.Global_TestDataSet, TestAttributes.StartStepId);					
				}
				TDS.close();
			}
			SDS.close();
			if (TestAttributes.TestInstanceControl) {//here both only nwhen true. Are you setting it not completed and final status. what if one fails
				getStatus();
				ConsolidateSS();
				//System.out.println("Script '" + TestAttributes.Global_Test_Id + "' is Finished. 'Status : " + TestAttributes.FinalStatus + "'.");

				System.out.println("Test Script [" + TestAttributes.Global_Test_Id + " " + TestAttributes.Global_Test_ShortName + "] is executed. Execution status is " + TestAttributes.FinalStatus.toUpperCase());

				if (!TestAttributes.FinalStatus.trim().equalsIgnoreCase("Passed")) TestAttributes.exitCode = 1;
				UpdateInstanceRuns("Final");
			}
		}
		FunctionLibrary.CloseDriver();
		
	
	}
	//=====================================================================================================
	public static void ExecuteSteps(String Test_Id, String Workflow_Name, String SharedDataSet, String TestDataSet, int StartStepID) throws Throwable {
		try {

			TestAttributes.Keyword = "";
			TestAttributes.Test_Id = Test_Id.trim();
			TestAttributes.Workflow_Name = Workflow_Name.trim();			
			TestAttributes.SharedDataSet = FunctionLibrary.getDataSet("SharedDataSet",SharedDataSet).trim();
			TestAttributes.TestDataSet = FunctionLibrary.getDataSet("TestDataSet",TestDataSet).trim();

			TestStepsResults = TestAttributes.stmtSteps.executeQuery("SELECT * FROM SQS_TEST_STEPS where SQS_TS_Test_Id = '" + TestAttributes.Test_Id + "' and UCASE(SQS_TS_Run_Type) LIKE '%YES%' and SQS_TS_Step_Id >= " + StartStepID + " order by SQS_TS_Step_Id");

			while (TestStepsResults.next()) {
				int Maximum_Error_Recovery_Attempts_Local = Settings.Maximum_Error_Recovery_Attempts;

				TestAttributes.Status= "Passed";				
				TestAttributes.ActualResult = "";			
				TestAttributes.ScreenShotsPaths = "";
				TestAttributes.TakeScreenShotFlag = false;
				TestAttributes.Keyword = "";

				TestAttributes.Step_Id = TestStepsResults.getInt("SQS_TS_Step_Id");
				TestAttributes.Step_Description = TestStepsResults.getString("SQS_TS_Step_Description").trim();
				TestAttributes.Expected_Result = TestStepsResults.getString("SQS_TS_Expected_Result").trim();
				TestAttributes.Run_Type = TestStepsResults.getString("SQS_TS_Run_Type").trim();				
				TestAttributes.Screen_Name = TestStepsResults.getString("SQS_TS_Screen_Name").trim();
				TestAttributes.Field_Name = TestStepsResults.getString("SQS_TS_Field_Name").trim();
				TestAttributes.Keyword = TestStepsResults.getString("SQS_TS_Keyword").trim();


				if (TestAttributes.Keyword.equalsIgnoreCase("USETOOL"))
					break;

				TestAttributes.element = getElement();				
				TestAttributes.Data = TestData.getData(getData().trim());

				TestAttributes.TakeScreenShotFlag = isNextScreenDifferent(TestStepsResults);
				CaptureScreenShot();

				Class<?> c = null;

				if (TestAttributes.AutomationApp.trim().equalsIgnoreCase("WEB"))
					c = Class.forName("SeleniumPackage.WebKeywordLibrary");

				if (TestAttributes.AutomationApp.trim().equalsIgnoreCase("MOBILE"))
					c = Class.forName("SeleniumPackage.MobileKeywordLibrary");

				Object obj = c.newInstance();
				Method method = c.getMethod(TestAttributes.Keyword.toUpperCase());

				boolean KeywordInvokeFlag = getKeywordInvokeFlag();

				if (KeywordInvokeFlag == true) {

					method.invoke(obj);
					CaptureScreenShot();					
					System.out.println(getSubTestDetails() + " - " + TestAttributes.ActualResult);

					if (!TestAttributes.Keyword.equalsIgnoreCase("IF") && !TestAttributes.Keyword.equalsIgnoreCase("ELSE") && !TestAttributes.Keyword.equalsIgnoreCase("ENDIF") && !TestAttributes.Keyword.equalsIgnoreCase("STARTLOOP") && !TestAttributes.Keyword.equalsIgnoreCase("EXITLOOP") && !TestAttributes.Keyword.equalsIgnoreCase("ENDLOOP") && !TestAttributes.Keyword.equalsIgnoreCase("CALLTEST")) {
						while(TestAttributes.Status.equals("Error") && Maximum_Error_Recovery_Attempts_Local != 0 ) {
							Maximum_Error_Recovery_Attempts_Local = Maximum_Error_Recovery_Attempts_Local - 1;
							System.out.println(getSubTestDetails() + " - " + "Repeating step due to error");

							TestAttributes.Status= "Passed";				
							TestAttributes.ActualResult = "";

							//Thread.sleep(6000);

							method.invoke(obj);
							CaptureScreenShot();
							System.out.println(getSubTestDetails() + " - " + TestAttributes.ActualResult);
						}
						WriteResults();
					}

					if (getBreakFlag())
						break;
				}
			}
			//Thread.sleep(3000);

		} catch (Exception e) {
			TestAttributes.TakeScreenShotFlag = true;
			TestAttributes.Status = "Error";

			if (TestAttributes.Keyword.trim().equalsIgnoreCase(""))
				TestAttributes.ActualResult = "Error possibly while retrieving Shared/Test Data Sets or selecting Test Steps for execution";
			else	
				TestAttributes.ActualResult = "Error while executing the keyword '" + TestAttributes.Keyword + "'.";

			TestAttributes.ActualResult = TestAttributes.ActualResult + " " + e.getMessage() + ".";
			System.out.println(getSubTestDetails() + " - " + TestAttributes.ActualResult);
			CaptureScreenShot();
			WriteResults();
		}
	}
	//========================================================================================================================================'	
}