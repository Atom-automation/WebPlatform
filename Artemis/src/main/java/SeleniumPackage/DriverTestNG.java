package SeleniumPackage;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class DriverTestNG extends TestAttributes {
	
	String browsername="Chrome";
	
	@BeforeTest
	public void beforesuite()
	{
		startResult(browsername);
	}
	
	//@Test
	//@Parameters({ "runID","runName"})
	@Test(dataProvider="RunId")
	public void mainrun(int runid, String runName) throws Throwable
	{
		try {
				System.out.println("currently running script id--->>>>"+runid);
				TestAttributes.Run_ID =0;
				TestAttributes.Run_Name = runName;
				//TestAttributes.Instance_ID = 59;
				TestAttributes.Instance_ID = runid;
				TestAttributes.Global_DataBase = "Access";
				TestAttributes.AutomationTool = "Selenium"; //SELENIUM or SEETEST
				TestAttributes.Browser = browsername.toLowerCase();
	
			/*
			 * When the selenium code is triggered for a script which has tool chaining, front end will pass a non zero value
			 * as TestAttributes.Run_ID and also additional parameters SharedDataSet, TestDataSet and StartStepId
			 */
		
			FunctionLibrary.Driver();
			
		} catch(Exception e) {		
			if(TestAttributes.executiontriggered = false){
				System.out.println("Error in execution. Possible reason coule be an issue...");
				System.out.println("with the arguments passed from front end.");
				System.out.println("while getting the test details from SQS_TEST_BATCH");
				System.out.println("while retrieving the log location from SQS_INSTANCE_RUNS");			
				System.out.println("while retrieving the step id from SQS_TEST_RESULT");
				System.out.println("while retrieving the data sets from SQS_TEST_DATASET");
				if (!TestAttributes.InitialSetUpErrorMessage.trim().equalsIgnoreCase(""))
					System.out.println("Please note there is also an initial setup error : " + TestAttributes.InitialSetUpErrorMessage.trim());
			}
			else{
				
			}
			e.printStackTrace();
		}
		
	}
	
	@AfterMethod
	public void aftermethod()
	{
		endtest();
	}
	
	@AfterTest
	public void aftersuite()
	{
		//endtest();
		endflush();
		System.exit(TestAttributes.exitCode);
	}
	
	//,parallel = true
	 @DataProvider(name="RunId")
     public static Object[][] getDataFromDataprovider(){
         return new Object[][] {
        	// { 181, "GetGo_Regression" },
        	// { 182, "GetGo_Regression" },
        	/* { 179, "GetGo_Regression" },
             { 180, "GetGo_Regression" },
             { 181, "GetGo_Regression" },
             { 182, "GetGo_Regression" },
             { 183, "GetGo_Regression" },
             { 184, "GetGo_Regression" },
             { 185, "GetGo_Regression" },
             { 186, "GetGo_Regression" },
             { 187, "GetGo_Regression" },
             { 188, "GetGo_Regression" },
             { 189, "GetGo_Regression" },
             { 190, "GetGo_Regression" },
             { 191, "GetGo_Regression" },
             { 194, "GetGo_Regression" },
             { 195, "GetGo_Regression" },
             { 196, "GetGo_Regression" },     
             { 197, "GetGo_Regression" }*/
        	 
        	   { 215, "GetGo_Regression" },
               { 233, "GetGo_Regression" },
               { 234, "GetGo_Regression" },
               { 235, "GetGo_Regression" },
               { 236, "GetGo_Regression" },
               { 237, "GetGo_Regression" },
               { 238, "GetGo_Regression" },
               { 239, "GetGo_Regression" },
               { 240, "GetGo_Regression" },
               { 241, "GetGo_Regression" },
               { 242, "GetGo_Regression" },
        	  { 243, "GetGo_Regression" },
        	  { 236, "GetGo_Regression" }
        	 
         };  
}

}