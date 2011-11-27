//import java.net.InetAddress;
//import java.net.InetAddress;
import java.rmi.RemoteException;

//import java.util.Scanner;

 
public class Node extends Client implements RmiClientInterface {
	
	private static final long serialVersionUID = 1L;
	
	
	public Node(String svr) throws RemoteException {
		super(svr);
		type = RmiServerInterface.clientType.NODE;
	}
	
	
	public void run() {
		try {
			id = server.register(ip, type, (RmiClientInterface) this);
			
			importantNodes = server.setup(id, type);
			updateImportantNodes(importantNodes);
			
			//TEST Snapshot
			System.out.println("---Test Snapshot ---\n" + compileSnapshot().toString() + "\n-----------");
			
			
				server.goodbye(id, type);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws InterruptedException {
		System.out.println("RMI Diagnostics Service\nNode Starting (connecting to Server \"" + args[0] + "\")...");
		Node n;
		try {
			n = new Node (args[0]);
			Thread t = new Thread(n);
			t.run();
			t.join();
		} catch (RemoteException e) {
			System.err.println("Error Starting Node:");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
}
