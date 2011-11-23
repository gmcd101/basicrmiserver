import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;


public class Server extends NetworkedSystem{
	RmiServerImpl rmi_svr;
	ArrayList<InetAddress> testingNodes;
	
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
			
		testingNodes = new ArrayList<InetAddress>();
	}


	// TODO write
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
		t.join();
	}
	
	//TODO write
	private void gatherSnapshots(){
		
	}
	
}
