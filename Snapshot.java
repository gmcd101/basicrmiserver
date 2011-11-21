
import java.io.Serializable;
import java.rmi.server.UID;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Date;

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

