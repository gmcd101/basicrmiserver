import java.net.InetAddress;
import java.rmi.server.UID;
import java.util.ArrayList;

public interface RmiServerInterface extends java.rmi.Remote{
	public static enum clientType{
		VIEWER, NODE;
	}
	/*
	 * Common Methods
	 * */
	
	public UID register(InetAddress ip, clientType type) throws java.rmi.RemoteException;
	
	public ArrayList<InetAddress> setup() throws java.rmi.RemoteException;
	
	public void goodbye(UID id) throws java.rmi.RemoteException;
	
	/*
	 * Viewer Methods
	 * */
	
	public void addImportantNodes(ArrayList<InetAddress> importantNodes) throws java.rmi.RemoteException;
	
	public void removeImportantNodes (ArrayList<InetAddress> importantNodes) throws java.rmi.RemoteException;

}
