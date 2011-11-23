import java.net.InetAddress;
import java.rmi.RemoteException;
import java.util.List;


public interface RmiClientInterface extends java.rmi.Remote{

	public RmiServerInterface findSvr(String serverName) throws RemoteException;
	public void updateImportantNodes(List<InetAddress> impNodes) throws RemoteException;
	
	public List<InetAddress> getImportantNodes() throws RemoteException;
	
	public void sendSnapshot(Snapshot shot) throws RemoteException;
	

	
}
