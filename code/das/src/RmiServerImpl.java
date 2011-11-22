import java.rmi.RemoteException;
import java.rmi.server.UID;
import java.util.ArrayList;


public class RmiServerImpl implements RmiServerInterface{
	public RmiServerImpl(){
		
	}
 
	@Override
	public UID register(String ip, int type) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Snapshot> setup() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void goodbye(UID id) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addImportantNodes(ArrayList<String> importantNodes)
			throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeImportantNodes(ArrayList<String> importantNodes)
			throws RemoteException {
		// TODO Auto-generated method stub
		
	}
}
