import java.util.Date;

public abstract class TestResult{

	private Date timestamp;		//Stores start of test
	private String nodeUnderTest; 	//IP address of node under test

	public TestResult(String ip){
		this.timestamp = new Date();
		this.nodeUnderTest = ip;
	}

}
