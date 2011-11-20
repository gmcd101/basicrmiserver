import java.io.Serializable;
import java.rmi.server.UID;
public class Snapshot implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//timestamp
	Date timestamp;
	//ip addr
	String creatorIP;
	//uid
	UID creatorUID;
	//hashmap of string(key - ip) & 'test result class'
	HashMap<String, ArrayList<TestResult>> results;
	
	public Snapshot (UID u, String ip)
	{
		this.timestamp = new Date();
		this.creatorUID = u;
		this.creatorIP = ip;
		this.results = new HashMap<String, ArrayList<TestResult>>();
	}

	
}

