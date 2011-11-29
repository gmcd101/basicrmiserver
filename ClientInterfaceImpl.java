
import java.util.ArrayList;

public class ClientInterfaceImpl implements ClientInterface{

	@Override
	public ArrayList<Snapshot> requestData() throws java.rmi.RemoteException{
		return null;
	}

	//update
	public boolean update (ArrayList<String> importantNodes) throws java.rmi.RemoteException{
		return true;
	}

}

