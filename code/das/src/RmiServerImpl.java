import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RmiServerImpl extends java.rmi.server.UnicastRemoteObject implements RmiServerInterface{
	
	private static final long serialVersionUID = 1L;
	
	
	//for getting (string) IP address of a node based on it's UID
	private Map<UID, InetAddress> connectedNodes;
	private Map<UID, InetAddress> connectedViewers;
	
	//Stores IP addresses of nodes that need to be tested
	private List<InetAddress> importantNodes;
	
	//Stores snapshots, indexed using the IP address of the important node under test
	private HashMap<InetAddress, Snapshot> snapshots;
	
	
	
	public RmiServerImpl() throws RemoteException {
		super();
		connectedNodes = new HashMap<UID, InetAddress>();
		connectedViewers = new HashMap<UID, InetAddress>();
		
		importantNodes = new ArrayList<InetAddress>();
		
		snapshots = new HashMap<InetAddress, Snapshot>();
		
		//setup server on RMI
		Registry registry = LocateRegistry.getRegistry();
		registry.rebind("NetworkDiagSvr", this);
	}
 
	@Override
	public UID register(InetAddress ip, clientType type) throws RemoteException {
		UID id = new UID(); //issue a new unique ID for the node
		
		switch(type){
			case NODE: 
				synchronized(connectedNodes){
					connectedNodes.put(id,ip);
				}	
				break;
				
			case VIEWER:
				synchronized(connectedViewers){
					connectedViewers.put(id,ip);
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
	public ArrayList<InetAddress> setup() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void goodbye(UID id) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addImportantNodes(ArrayList<InetAddress> importantNodes)
			throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeImportantNodes(ArrayList<InetAddress> importantNodes)
			throws RemoteException {
		// TODO Auto-generated method stub
		
	}
}
