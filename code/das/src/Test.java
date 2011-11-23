import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.net.InetAddress;


public abstract class Test {
	InetAddress destination;
	public Test(InetAddress destination){
		this.destination = destination;
	}
	
	protected String inputStreamToString(InputStream is) throws IOException{
		StringWriter sw = new StringWriter();
		char[] buffer = new char[1024];
		Reader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
		int n;
		
		while ((n = reader.read(buffer)) != -1) {
			sw.write(buffer, 0, n);
		}
		
		return sw.toString();
	}
	
	public abstract TestResult run();
}
