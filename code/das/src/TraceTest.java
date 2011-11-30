import java.net.InetAddress;


public class TraceTest extends Test {

	private static final long serialVersionUID = 1L;

	public TraceTest(InetAddress destination) {
		super(destination);
	}

	@Override
	public void run() {
		String output;
        try {
        	Runtime rt = Runtime.getRuntime();
			Process proc = rt.exec("traceroute "+destination.getHostAddress());
			output = inputStreamToString(proc.getInputStream());
			out = new TraceResult(output);
			
        }catch (Exception e){
        	e.printStackTrace();
        	output = null;
        }
		
	}

}
