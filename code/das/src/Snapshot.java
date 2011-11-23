import java.net.InetAddress;
import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.Date;


public class Snapshot {
	Date timestamp;
	InetAddress creatorIP;
	UID uniqueID;
	
	ArrayList<TestResult> tests;
	
	public Snapshot(InetAddress ip, UID id)
	{
		this.timestamp = new Date();
		this.creatorIP = ip;
		this.uniqueID = id;
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
		return uniqueID;
	}
}
