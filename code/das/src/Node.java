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
	
	

	
	
	public void run() {
		try {
			id = server.register(ip, type, (RmiClientInterface) rmi_c);
			rmi_c.setClientIP(ip);
			rmi_c.setClientID(id);
			
			importantNodes = server.setup(id, type);
			rmi_c.updateImportantNodes(importantNodes);
			
			//TEST Snapshot
			System.out.println("---Test Snapshot ---\n" + rmi_c.compileSnapshot().toString() + "\n-----------");
			
			
			//server.goodbye(id, type);
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
