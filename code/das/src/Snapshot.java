import java.io.Serializable;
import java.net.InetAddress;
import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;


public class Snapshot implements Serializable{

	private static final long serialVersionUID = 1L;
	Date timestamp;
	InetAddress creatorIP;
	UID creatorID;
	
	ArrayList<TestResult> tests;
	
	public Snapshot(InetAddress ip, UID id)
	{
		this.timestamp = new Date();
		this.creatorIP = ip;
		this.creatorID = id;
		this.tests = new ArrayList<TestResult>();
	}
	public void addTest(TestResult test){
		this.tests.add(test);
	}
	public ArrayList<TestResult> getResults(){
		return this.tests;
	}
	public Date getTimestamp(){
		return timestamp;
	}
	public InetAddress getCreator(){
		return creatorIP;
	}
	public UID getID(){
		return creatorID;
	}
	
	public String toString(){
		String output;
		Iterator<TestResult> results = tests.iterator();
		
		output  = "Timestamp:  " + timestamp.toString() + "\n";
		output += "Creator IP: " + creatorIP + "\n";
		output += "Creator ID: " + creatorID + "\n";
		output += "Tests:\n";
		while(results.hasNext()){
			try{
				output += results.next().getResult() + "\n";
		
			}		
			catch(Exception e){}
		}
		return output;
		
	}
}
