import java.net.InetAddress;
import java.rmi.server.UID;
import java.util.List;

public interface RmiServerInterface extends java.rmi.Remote{
	public static enum clientType{
		VIEWER, NODE;
	}
	/*
	 * Common Methods
	 * */
	
	public UID register(InetAddress ip, clientType type, RmiClientInterface client) throws java.rmi.RemoteException;
	
	public List<InetAddress> setup(UID id, clientType type) throws java.rmi.RemoteException;
	
	public void goodbye(UID id, clientType type) throws java.rmi.RemoteException;
	
	/*
	 * Viewer Methods
	 * */
	public void addImportantNodes(List<InetAddress> moreNodes) throws java.rmi.RemoteException;
	
	public void removeImportantNodes (List<InetAddress> nodesToDel) throws java.rmi.RemoteException;

}
