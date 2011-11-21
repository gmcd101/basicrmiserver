
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.rmi.server.UID;

public class ServerInterfaceImpl  extends java.rmi.server.UnicastRemoteObject implements ServerInterface
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	HashMap<UID,String> clients;
	ArrayList<String> importantNodes;
	
	//<important node ip, snapshots relating to that
	HashMap<String,Snapshot> snapshots;

	public ServerInterfaceImpl() throws java.rmi.RemoteException
	{
		super();
		clients = new HashMap<UID, String>();
		importantNodes = new ArrayList<String>();
		snapshots = new HashMap<String,Snapshot>();
	}
	
	public UID register(String ip) throws java.rmi.RemoteException
	{
		System.out.println("Client Registering");
		UID id = new UID();
		//lock the list of clients - must allow multi-thread access
		synchronized(clients)
		{
			clients.put(
				id,ip
			);
		}		
		return id;
	}
	@SuppressWarnings("unchecked")
	public ArrayList<String> setup() throws java.rmi.RemoteException
	{
		ArrayList<String> out;
		
		//cant allow other threads to change while cloning
		synchronized(importantNodes)
		{
			out = (ArrayList<String>) importantNodes.clone();
		}
		return out;
	}
	public void unregister(UID u) throws java.rmi.RemoteException
	{
		synchronized(clients)
		{
			clients.remove(u);
		}
	}

	public ArrayList<Snapshot> requestSpecificSnapshot (String ip) throws java.rmi.RemoteException{
		return null;
	}

	public HashMap<String,Snapshot> requestSnapshots () throws java.rmi.RemoteException{
		return null;
	}
	
	//add node
	public void addImportantNodes (ArrayList<String> ip) throws java.rmi.RemoteException{
		;
	}

	//remove node
	public void removeImportantNodes (ArrayList<String> ip) throws java.rmi.RemoteException{
		;
	}



/*	@SuppressWarnings("unchecked")
	public ArrayList<Snapshot> requestSnapshots(int depth) throws RemoteException
	{
		ArrayList<Snapshot> out;
		synchronized(snapshots)
		{
			out = (ArrayList<Snapshot>)snapshots.clone();
		}
		return out;
	}
	
	
	public void addNode(Node n) throws RemoteException {
		synchronized(nodes)
		{
			nodes.add(n);
		}
	}

	
	public void removeNode(Node n) throws RemoteException {
		synchronized(nodes)
		{
			nodes.remove(n);
		}
	}
*/
}

