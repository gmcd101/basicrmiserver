
import java.util.ArrayList;
import java.util.HashMap;
import java.rmi.server.UID;

public interface RmiInterface extends java.rmi.Remote {
	
	/*
	Dumb Client Functions
	*/
	//TODO type of register
	public UID register (String ip) throws java.rmi.RemoteException;
	
	public ArrayList<String> setup () throws java.rmi.RemoteException;
	
	public void unregister (UID u) throws java.rmi.RemoteException;

	/*
	Viewer Functions
	*/

	public ArrayList<Snapshot> requestSpecificSnapshot (String ip) throws java.rmi.RemoteException;

	public HashMap<String,Snapshot> requestSnapshots () throws java.rmi.RemoteException;
	//add node
	public void addImportantNodes (ArrayList<String> ip) throws java.rmi.RemoteException;
	//remove node
	public void removeImportantNodes (ArrayList<String> ip) throws java.rmi.RemoteException;
	
}

