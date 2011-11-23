
public class TraceTest extends Test {

	public TraceTest(String destination) {
		super(destination);
	}

	@Override
	public TestResult run() {
		String out;
        try {
        	Runtime rt = Runtime.getRuntime();
			Process proc = rt.exec("traceroute "+destination);
			out = inputStreamToString(proc.getInputStream());
			
        }catch (Exception e){
        	return null;
        }
		return new TraceResult(out);
	}

}
