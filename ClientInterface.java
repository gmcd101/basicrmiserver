public interface ClientInterface extends java.rmi.Remote{
	
	/**
	 * Allows the server to request the most up to date snapshot
	 * */
	public ArrayList<Snapshot> requestData() throws java.rmi.RemoteException;

	//update
	public bool update (ArrayList<String>) throws java.rmi.RemoteException;
}

