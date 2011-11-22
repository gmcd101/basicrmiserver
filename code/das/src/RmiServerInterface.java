import java.rmi.server.UID;
import java.util.ArrayList;

public interface RmiServerInterface extends java.rmi.Remote{
	
	/*
	 * Common Methods
	 * */
	
	public UID register(String ip, int type) throws java.rmi.RemoteException;
	
	public ArrayList<Snapshot> setup() throws java.rmi.RemoteException;
	
	public void goodbye(UID id) throws java.rmi.RemoteException;
	
	/*
	 * Viewer Methods
	 * */
	
	public void addImportantNodes(ArrayList<String> importantNodes) throws java.rmi.RemoteException;
	
	public void removeImportantNodes (ArrayList<String> importantNodes) throws java.rmi.RemoteException;

}
