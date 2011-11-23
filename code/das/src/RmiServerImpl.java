import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class RmiServerImpl extends java.rmi.server.UnicastRemoteObject implements RmiServerInterface{
	
	private static final long serialVersionUID = 1L;
	
	
	//for getting (string) IP address of a node based on it's UID
	private Map<UID, RmiClientInterface> connectedNodes;
	private Map<UID, RmiClientInterface> connectedViewers;	
	
	
	//Stores IP addresses of nodes that need to be tested
	private List<InetAddress> importantNodes;
	
	//Stores snapshots, indexed using the IP address of the important node under test
	@SuppressWarnings("unused")
	private HashMap<InetAddress, Snapshot> snapshots;
	
	
	
	public RmiServerImpl() throws RemoteException {
		super();
		connectedNodes = new HashMap<UID, RmiClientInterface>();
		connectedViewers = new HashMap<UID, RmiClientInterface>();
		
		importantNodes = new ArrayList<InetAddress>();
		
		snapshots = new HashMap<InetAddress, Snapshot>();
		
		//setup server on RMI
		Registry registry = LocateRegistry.getRegistry();
		registry.rebind("NetworkDiagSvr", this);
		
	}
 
	
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
}
