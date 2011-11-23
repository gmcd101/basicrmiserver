import java.net.InetAddress;


public class TraceTest extends Test {

	public TraceTest(InetAddress destination) {
		super(destination);
	}

	@Override
	public TestResult run() {
		String out;
        try {
        	Runtime rt = Runtime.getRuntime();
			Process proc = rt.exec("traceroute "+destination.getHostAddress());
			out = inputStreamToString(proc.getInputStream());
			
        }catch (Exception e){
        	return null;
        }
		return new TraceResult(out);
	}

}
