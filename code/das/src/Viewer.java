import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;


public class Viewer extends Client {
	
	public Viewer(String svr){
		super(svr);
		
		type = RmiServerInterface.clientType.VIEWER;
	}
	
	
	//TODO
	public void run() {
		try {
			id = server.register(ip, type, (RmiClientInterface) rmi_c);
			rmi_c.setClientIP(ip);
			rmi_c.setClientID(id);
			server.snapshotSubscribe(id, type);
			
			//TODO: REMOVE TEST ADDS
			ArrayList<InetAddress> testingNodes = new ArrayList<InetAddress>();
			Scanner inputScanner = new Scanner(System.in);
			
			while(inputScanner.hasNextLine()){
				try {
					
						testingNodes.clear();
						testingNodes.add(InetAddress.getByName(inputScanner.nextLine()));
						server.addImportantNodes(testingNodes);
			
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
		Viewer v = new Viewer (args[0]);
		Thread t = new Thread(v);
		t.run();
		
		System.out.println("Viewer started.");
		t.join();
	}

}
