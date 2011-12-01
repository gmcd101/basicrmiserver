import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.util.List;

 
public class Node extends Client implements RmiClientInterface {
	
	private static final long serialVersionUID = 1L;
	
	
	public Node(String svr) throws RemoteException {
		super(svr);
		type = RmiServerInterface.clientType.NODE;
	}
	

	public void sendSnapshots(List<Snapshot> shots) throws RemoteException {
		System.out.println("Received snapshots. Should not have happened.");
		
	} 

	
	public void run() {
		try {
			id = server.register(ip, type, (RmiClientInterface) this);
			
			importantNodes = server.setup(id, type);
			updateImportantNodes(importantNodes);
			
			//TEST Snapshot
			//System.out.println("---Test Snapshot ---\n" + compileSnapshot().toString() + "\n-----------");

			//server.goodbye(id, type);
		}catch(ConnectException ce){
			System.err.println("Failed to connect to RMI registry.\nCheck that it is running.");
			System.exit(-1);
		}
		catch(RemoteException re){
				System.err.println("Failed to setup RMI.");
				//re.printStackTrace();
				System.exit(-1);
		}
		catch(Exception e){
			System.err.println("Unable to find server "+e.getMessage());
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
			t.start();
			t.join();
		}catch(ConnectException ce){
				System.err.println("Failed to connect to RMI registry. Check that it is running.");
				System.exit(-1);
		}
		catch(RemoteException re){
				System.err.println("Failed to setup RMI.");
				re.printStackTrace();
				System.exit(-1);
		}

	}
	
}
