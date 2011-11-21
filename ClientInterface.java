
import java.util.ArrayList;

public interface ClientInterface extends java.rmi.Remote{
	
	/**
	 * Allows the server to request the most up to date snapshot
	 * */
	public ArrayList<Snapshot> requestData() throws java.rmi.RemoteException;

	//update
	public boolean update (ArrayList<String> importantNodes) throws java.rmi.RemoteException;
}

