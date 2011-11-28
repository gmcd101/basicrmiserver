/*import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;*/
import java.net.InetAddress;


public class PingTest extends Test {

	private static final long serialVersionUID = 1L;

	public PingTest(InetAddress destination) {
		super(destination);
	}
	
	@Override
	public TestResult run(){
		String out;
        try {
        	Runtime rt = Runtime.getRuntime();
			Process proc = rt.exec("ping "+destination.getHostAddress()+" -c 5");
			
			out = inputStreamToString(proc.getInputStream());

        }catch (Exception e){
        	e.printStackTrace();
        	return null;
        }
		return new PingResult(out);
	}

}
