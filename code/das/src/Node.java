import java.rmi.RemoteException;
import java.util.ArrayList;


public class Node extends Client {
	
	ArrayList<Test> tests;
	
	public Node(String server){
		super(server);
		tests = new ArrayList<Test>();
		type = RmiServerInterface.clientType.NODE;
	}
	
	@Override
	public void run() {
		try {
			server.register(ip, type);
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
