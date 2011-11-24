import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.server.UID;
import java.util.List;



public abstract class Client extends NetworkedSystem {
	
	RmiClientImpl rmi_c;

	RmiServerInterface server;		//interface to call functions at RMI server
	InetAddress ip;
	UID id;
	RmiServerInterface.clientType type;
	protected List<InetAddress> importantNodes;
	
	
	public Client(String serverName) {
		super();
		
		
		try{
			rmi_c = new RmiClientImpl();
			importantNodes = rmi_c.getImportantNodes();
			ip = InetAddress.getLocalHost();
		}
		catch(UnknownHostException unknownHost){
			unknownHost.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		server = rmi_c.findSvr(serverName);
	}


}
