import java.util.ArrayList;
import java.io.IOException;

public class PingTest extends TestResult {
	private ArrayList<String> results;


	public PingTest(String ip){
		super(ip);
	}

	public boolean runTest(int numPings, int timeout){
		Runtime runtime = Runtime.getRuntime();
		try{
			Process p = runtime.exec("ping -c " + numPings + " -w " + timeout);
			results.add(p.getOutputStream().toString());
			return true; 
		}
		catch(IOException ioe){
			return false; 
		}
	}
}
