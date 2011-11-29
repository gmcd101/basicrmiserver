import java.net.InetAddress;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UID;
import java.util.List;

public interface RmiServerInterface extends Remote{
	
	public static enum clientType{
		VIEWER, NODE;
	}
	
	
	/*
	 * Common Methods
	 * */
	public UID register(InetAddress ip, clientType type, RmiClientInterface client) throws RemoteException;
	public List<InetAddress> setup(UID id, clientType type) throws RemoteException;
	public void goodbye(UID id, clientType type) throws RemoteException;
	
	/*
	 * Viewer Methods
	 * */
	public void addImportantNodes(List<InetAddress> moreNodes) throws RemoteException;
	public void removeImportantNodes (List<InetAddress> nodesToDel) throws RemoteException;
	public boolean snapshotSubscribe(UID id, clientType type) throws RemoteException;

}
