//import java.net.InetAddress;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;


public class Node extends Client {
	
	@SuppressWarnings("unused")
	private List<Test> tests;
	
	
	public Node(String svr){
		super(svr);
		tests = new ArrayList<Test>();
		type = RmiServerInterface.clientType.NODE;
	}
	
	
	public Snapshot compileSnapshot(){
		Snapshot newShot = new Snapshot(ip, id);
		Iterator<InetAddress> nodesToTest = importantNodes.iterator();
		InetAddress current;
		
		while(nodesToTest.hasNext()){
			current = nodesToTest.next();

			TestResult pingResult = (new PingTest(current)).run();
			TestResult traceResult = (new TraceTest(current)).run();

			//add the ping test result
			newShot.addTest(pingResult);
			//add the trace route result
			newShot.addTest(traceResult);
		}
		
		
		return newShot;
	}
	
	
	public void run() {
		try {
			id = server.register(ip, type, (RmiClientInterface) rmi_c);
			
			importantNodes = server.setup(id, type);
			if(importantNodes != null)
				System.out.println("Got list of important nodes: " + importantNodes.toString());
			
			//server.goodbye(id, type);
			System.out.println("---Test Snapshot ---\n" + compileSnapshot().toString() + "\n-----------");
			
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws InterruptedException {
		System.out.println("RMI Diagnostics Service\nNode Starting (connecting to Server \"" + args[0] + "\")...");
		Node n = new Node (args[0]);
		Thread t = new Thread(n);
		t.run();
		t.join();
	}
	
}
