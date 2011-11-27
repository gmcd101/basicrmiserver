import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



public abstract class Client extends java.rmi.server.UnicastRemoteObject implements Runnable {

	RmiServerInterface server;		//interface to call functions at RMI server
	InetAddress ip;
	UID id;
	RmiServerInterface.clientType type;
	protected List<InetAddress> importantNodes;
	
	
	public Client(String serverName) throws RemoteException {
		super();
		
		
		try{
			//rmi_c = new RmiClientImpl();
			importantNodes = getImportantNodes();
			ip = InetAddress.getLocalHost();
		}
		catch(UnknownHostException unknownHost){
			unknownHost.printStackTrace();
		}
		//catch (RemoteException e) {
		//	e.printStackTrace();
		//}
		
		server = findSvr(serverName);
	}

	/*--------------- From RmiClientInterface... -------------------*/	
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
	
	
	public void sendSnapshot(Snapshot shot){
		
	}
	
	public Snapshot compileSnapshot(){
		Snapshot newShot = new Snapshot(ip, id);
		Iterator<InetAddress> nodesToTest = importantNodes.iterator();
		InetAddress current;
		
		while(nodesToTest.hasNext()){
			current = nodesToTest.next();

			TestResult pingResult = (new PingTest(current)).run();
			TestResult traceResult = (new TraceTest(current)).run();

			//add the ping test result
			newShot.addTest(pingResult);
			//add the trace route result
			newShot.addTest(traceResult);
		}

		return newShot;
	}


}
