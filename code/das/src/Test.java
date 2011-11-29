//import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
//import java.io.InputStreamReader;
//import java.io.Reader;
//import java.io.StringWriter;
import java.net.InetAddress;
import java.util.Scanner;


public abstract class Test implements Serializable {

	private static final long serialVersionUID = 1L;
	InetAddress destination;
	public Test(InetAddress destination){
		this.destination = destination;
	}
	
	protected String inputStreamToString(InputStream is) throws IOException{
		Scanner streamScanner = new Scanner(is);
		String output = "";
		
		while(streamScanner.hasNextLine()){
			output += streamScanner.nextLine();
		}
		return output;
	}
	
	public abstract TestResult run();
}
