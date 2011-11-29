
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
	//hashmap of string(key - ip) & list of test results
	HashMap<String, ArrayList<TestResult>> results;
	
	public Snapshot (UID u, String ip)
	{
		this.timestamp = new Date();	//start time of snapshot
		this.creatorUID = u;		//UID of node carrying out test
		this.creatorIP = ip;		//IP address of node carrying out test
		this.results = new HashMap<String, ArrayList<TestResult>>(); //For sorting purposes, store result with important node's ID as a string
	}

	public void addResult(TestResult result, String nodeUnderTest){
		
	}

	
}

