//import java.net.InetAddress;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;


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
			
			importantNodes = server.setup(id, type);
			if(importantNodes != null)
				System.out.println("Got list of important nodes: " + importantNodes.toString());
			
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
