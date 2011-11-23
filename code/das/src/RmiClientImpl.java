import java.net.InetAddress;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;


public class RmiClientImpl extends java.rmi.server.UnicastRemoteObject implements RmiClientInterface{

	private static final long serialVersionUID = 1L;
	List<InetAddress> importantNodes;
	
	
	public RmiClientImpl() throws RemoteException{
		importantNodes = new ArrayList<InetAddress>();
	}

	public RmiServerInterface findSvr(String serverName) {
		RmiServerInterface server;
		
		try{
			server = (RmiServerInterface) Naming.lookup(serverName);
		}
		catch(RemoteException re){
			re.printStackTrace();
			return null;
		} catch (MalformedURLException badUrl) {
			badUrl.printStackTrace();
			return null;
		} catch (NotBoundException notBound) {
			notBound.printStackTrace();
			return null;
		}
		
		return server;
	}
	
	
	public void updateImportantNodes(List<InetAddress> impNodes){
		System.out.println("Updated important nodes list received:\n" + impNodes);
		importantNodes = new ArrayList<InetAddress>(impNodes);
	}
	
	public List<InetAddress> getImportantNodes(){
		return importantNodes;
	}
	
}
