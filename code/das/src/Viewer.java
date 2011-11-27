import java.net.InetAddress;
//import java.net.MalformedURLException;
import java.net.UnknownHostException;
//import java.rmi.Naming;
//import java.rmi.NotBoundException;
import java.rmi.RemoteException;
//import java.rmi.server.UID;
import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
import java.util.Scanner;


public class Viewer extends Client implements RmiClientInterface {
	
	private static final long serialVersionUID = 1L;


	public Viewer(String svr) throws RemoteException{
		super(svr);
		
		type = RmiServerInterface.clientType.VIEWER;
	}
	
	
	//TODO
	public void run() {
		try {
			id = server.register(ip, type, this);
			//rmi_c.setClientIP(ip);
			//rmi_c.setClientID(id);
			server.snapshotSubscribe(id, type);
			System.out.println("Viewer started. Type an address to add it to the important nodes list...");
			
			
			//TODO: REMOVE TEST ADDS
			ArrayList<InetAddress> testingNodes = new ArrayList<InetAddress>();
			Scanner inputScanner = new Scanner(System.in);
			
			while(inputScanner.hasNextLine()){
				try {
						testingNodes.clear();
						testingNodes.add(InetAddress.getByName(inputScanner.nextLine()));
						server.addImportantNodes(testingNodes);
						System.out.println("Node added to Server's Important Nodes list.");
			
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
			
		}catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		System.out.println("RMI Diagnostics Service\nViewer Starting (Connecting to Server: "+args[0] + ")");
		Viewer v;
		try {
			v = new Viewer (args[0]);
			Thread t = new Thread(v);
			t.run();
			
			
			t.join();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	


}
