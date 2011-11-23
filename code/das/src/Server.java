import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;


public class Server extends NetworkedSystem{
	RmiServerImpl rmi_svr;
	
	public Server()
	{
		super();
		try{
			rmi_svr = new RmiServerImpl();	//setup the RMI connections

		}
		catch(RemoteException re){
			System.err.println("Failed to setup RMI.");
			re.printStackTrace();
			System.exit(-1);
		}
	}



	public void run() {

	}

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException{
		System.out.println("RMI Diagnostics Service\nServer Starting...");
		Server s = new Server();
		Thread t = new Thread(s);
		t.run();
		
		System.out.println("Server Started.");
		
		//TODO: REMOVE TEST ADDS
		ArrayList<InetAddress> testingNodes = new ArrayList<InetAddress>();
		Scanner inputScanner = new Scanner(System.in);
		
		try {
			while(inputScanner.hasNextLine()){
				testingNodes.clear();
				testingNodes.add(InetAddress.getByName(inputScanner.nextLine()));
				s.rmi_svr.addImportantNodes(testingNodes);
			}
			
			
			
			//testingNodes.add(InetAddress.getByName("www.google.com"));
			//testingNodes.add(InetAddress.getByName("www.gla.ac.uk"));
			//rmi_svr.addImportantNodes(testingNodes);
		
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		t.join();
	}
	
	//TODO write
	private void gatherSnapshots(){
		
	}
	
}
