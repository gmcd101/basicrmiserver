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
	public void run() {
		String output;
        try {
        	System.out.println("Starting ping test");
        	Runtime rt = Runtime.getRuntime();
			Process proc = rt.exec("ping "+destination.getHostAddress()+" -c 5");
			output = inputStreamToString(proc.getInputStream());
			out = new PingResult(output);
			System.out.println("Finished ping test");
        }catch (Exception e){
        	e.printStackTrace();
        	out = null;
        }
	}
}
