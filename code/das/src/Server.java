//import java.net.InetAddress;
//import java.net.UnknownHostException;
import java.net.InetAddress;
import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UID;
//import java.util.ArrayList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
//import java.util.Scanner;
import java.util.Map;


public class Server extends java.rmi.server.UnicastRemoteObject implements Runnable,RmiServerInterface {
	//RmiServerImpl rmi_svr;
	private static final long serialVersionUID = 1L;
	
	
	//for getting (string) IP address of a node based on it's UID
	private Map<UID, RmiClientInterface> connectedNodes;
	private Map<UID, RmiClientInterface> connectedViewers;	
	
	private List<RmiClientInterface> subscribedViewers;
	
	//Stores IP addresses of nodes that need to be tested
	private List<InetAddress> importantNodes;
	
	//Stores snapshots, indexed using the IP address of the important node under test
	//@SuppressWarnings("unused")
	//private HashMap<InetAddress, Snapshot> snapshots;	
	
	public Server() throws RemoteException
	{
		super();
		connectedNodes = new HashMap<UID, RmiClientInterface>();
		connectedViewers = new HashMap<UID, RmiClientInterface>();
		
		importantNodes = new ArrayList<InetAddress>();
		subscribedViewers = new ArrayList<RmiClientInterface>();
		
		//snapshots = new HashMap<InetAddress, Snapshot>();
		
		//setup server on RMI
		Registry registry = LocateRegistry.getRegistry();
		registry.rebind("NetworkDiagSvr", this);
	}



	public void run() {

	}

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException{
		System.out.println("RMI Diagnostics Service\nServer Starting...");
		Server s = null;
		try{
			s = new Server();
			Thread t = new Thread(s);
			t.run();
			
			System.out.println("Server Started.");
			
			t.join();
		}
		catch(ConnectException ce){
			System.err.println("Failed to connect to RMI registry.");
			System.exit(-1);
		}
		catch(RemoteException re){
			System.err.println("Failed to setup RMI.");
			re.printStackTrace();
			System.exit(-1);
		}
		
	}
	
	//TODO write
	@SuppressWarnings("unused")
	private void gatherSnapshots(){
		List<Snapshot> allShots = new ArrayList<Snapshot>();
		Iterator<Snapshot> allSnaps;
		
		try {
			Iterator<RmiClientInterface> allNodes = connectedNodes.values().iterator();
			
			while(allNodes.hasNext()){
				allShots.add(allNodes.next().compileSnapshot());
			}
			
			System.out.println("Collected all snapshots ---");
			allSnaps = allShots.iterator();
			
			while(allSnaps.hasNext()){
				System.out.println(allSnaps.next().toString() + "\n----\n");
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	
// REMOTE METHODS //	
	
	@Override
	public UID register(InetAddress ip, clientType type, RmiClientInterface client) throws RemoteException {
		UID id = new UID(); //issue a new unique ID for the node
		
		switch(type){
			case NODE: 
				synchronized(connectedNodes){
					connectedNodes.put(id,client);
				}	
				break;
				
			case VIEWER:
				synchronized(connectedViewers){
					connectedViewers.put(id,client);
				}
				break;
				
			default:
				System.err.println("Unrecognised client type " + type + " attempted to register.");
				return null;
		}

		System.out.println("New " + type.toString().toLowerCase() + " with IP address \"" + ip + "\" registered with UID " + id.toString());
		return id;
	}
	

	
	
	@Override
	public List<InetAddress> setup(UID id, clientType type) throws RemoteException {
		return importantNodes;
	}

	
	
	@Override
	public void goodbye(UID id, clientType type) throws RemoteException {
		switch(type){
			case NODE: 
				synchronized(connectedNodes){
					connectedNodes.remove(id);
				}	
				break;
				
			case VIEWER:
				synchronized(connectedViewers){
					connectedViewers.remove(id);
				}
				break;
				
			default:
				System.err.println("Unrecognised client type " + type + " attempted to leave.");
				return;
		}

	
		System.out.println(type.toString().toLowerCase() + " with UID " + id.toString() + " has disconnected.");
		
	}

	
	@Override
	public void addImportantNodes(List<InetAddress> moreNodes) throws RemoteException {
		importantNodes.addAll(moreNodes);
		
		//Update all connected nodes with new list
		Iterator<RmiClientInterface> allNodes = connectedNodes.values().iterator();
		
		while(allNodes.hasNext()){
			allNodes.next().updateImportantNodes(importantNodes);
		}
		
		System.out.println("Added important nodes: " + moreNodes.toString());
	}

	
	@Override
	public void removeImportantNodes(List<InetAddress> nodesToDel) throws RemoteException {
		importantNodes.removeAll(nodesToDel);
		
		//Update all connected nodes with new list
		Iterator<RmiClientInterface> allNodes = connectedNodes.values().iterator();
		
		while(allNodes.hasNext()){
			allNodes.next().updateImportantNodes(importantNodes);
		}
	}
	
	@Override
	public boolean snapshotSubscribe(UID id, clientType type) throws RemoteException {
		if(type == clientType.NODE)
			return false;
		
		synchronized(subscribedViewers){
			//check viewer is registered
			if (!connectedViewers.containsKey(id)){
				return false;
			}
			
			//is registered, so add to list of subscribed Viewers
			subscribedViewers.add(connectedViewers.get(id));
			
			return true;
		}
	}
	
	
}
