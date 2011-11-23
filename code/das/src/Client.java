import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;



public abstract class Client extends NetworkedSystem {
	
	RmiClientImpl rmi_c;

	RmiServerInterface server;		//interface to call functions at RMI server
	InetAddress ip;
	RmiServerInterface.clientType type;
	
	public Client(String serverName) {
		super();
		rmi_c = new RmiClientImpl();
		
		
		try{
			ip = InetAddress.getLocalHost();
		}
		catch(UnknownHostException unknownHost){
			unknownHost.printStackTrace();
		}
		
		server = rmi_c.findSvr(serverName);
		
	}


}
