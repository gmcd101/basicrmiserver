import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.server.UID;
import java.util.List;


public interface RmiClientInterface extends java.rmi.Remote{

	public RmiServerInterface findSvr(String serverName) throws RemoteException;
	public void updateImportantNodes(List<InetAddress> impNodes) throws RemoteException;
	
	public List<InetAddress> getImportantNodes() throws RemoteException;
	
	public void sendSnapshots(List<Snapshot> shots) throws RemoteException;
	
	public UID getUID() throws RemoteException;
	
	public Snapshot compileSnapshot() throws RemoteException;
}
