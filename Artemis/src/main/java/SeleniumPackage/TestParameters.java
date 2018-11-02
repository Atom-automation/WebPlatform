package SeleniumPackage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TestParameters {
	
	private static ResultSet TestsResults;
	private static String TestInfo,SharedDataset,TestDataset,TestWorkflow,TestName;
	


	public TestParameters() throws SQLException {
		String sql="SELECT * FROM SQS_TEST_BATCH where SQS_TB_Test_Instance_Id = " + TestAttributes.Instance_ID;
		TestsResults = TestAttributes.stmtTests.executeQuery(sql);
		TestsResults.next();
		TestInfo = TestsResults.getString("SQS_TB_Test_Id_Name").trim();
		TestWorkflow = TestsResults.getString("SQS_TB_Workflow").trim();
		SharedDataset = TestsResults.getString("SQS_TB_Shared_DataSet").trim();
		TestDataset = TestsResults.getString("SQS_TB_Test_DataSet").trim();
		TestName=TestsResults.getString("SQS_TB_Test_Id_Name").trim();
		TestsResults.close();
	}
	
	public String getTestInfo() throws SQLException {
		return TestInfo;
	}
	
	public String getTestWorkflow() throws SQLException {
		return TestWorkflow;
	}

	public String getSharedDataset() throws SQLException {
		return SharedDataset;
	}
	
	public String getTestDataset() throws SQLException {
		return TestDataset;
	}
	public static String getTestName() {
		return TestName;
	}
}
