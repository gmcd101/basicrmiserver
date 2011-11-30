import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;



public abstract class Client extends java.rmi.server.UnicastRemoteObject implements Runnable {

	private static final long serialVersionUID = 1L;
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
		synchronized(importantNodes){
			importantNodes = new ArrayList<InetAddress>(impNodes);
		}
	}
	
	public List<InetAddress> getImportantNodes(){
		//TODO deep copy
		return importantNodes;
	}
	
	
	public void sendSnapshot(Snapshot shot){
		
	}
	
	public Snapshot compileSnapshot(){
		Snapshot newShot = new Snapshot(ip, id);
		Iterator<InetAddress> nodesToTest = importantNodes.iterator();
		InetAddress current;
		long finishTime = 0;
		long startTime = Calendar.getInstance().getTimeInMillis();
		
		while(nodesToTest.hasNext()){
			current = nodesToTest.next();

			Test pingResult = new PingTest(current);
			Test traceResult = new TraceTest(current);

			//set up threads
			Thread t1 = new Thread(pingResult);
			Thread t2 = new Thread(traceResult);
			//run threads
			t1.start();
			t2.start();
			try{
				//add the ping test result
				t1.join();
				newShot.addTest(pingResult.getResult());
				//add the trace route result
				t2.join();
				newShot.addTest(traceResult.getResult());
			}catch(Exception e){
				System.out.println("Test threads interrupted.");
			}
		}
		
		finishTime = Calendar.getInstance().getTimeInMillis();
		
		System.out.println("Time to compile snapshot: " + (finishTime - startTime)/1000.0 + " seconds.");
		
		return newShot;
	}

	
	public UID getUID() throws RemoteException {
		return id;
	}

}
