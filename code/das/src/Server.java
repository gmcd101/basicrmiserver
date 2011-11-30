//import java.net.InetAddress;
//import java.net.UnknownHostException;
import java.net.InetAddress;
import java.rmi.ConnectException;
import java.rmi.ConnectIOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UID;
//import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TimerTask;
import java.util.Timer;
//import java.util.Scanner;
import java.util.Map;


public class Server extends java.rmi.server.UnicastRemoteObject implements Runnable,RmiServerInterface {
	//RmiServerImpl rmi_svr;
	private static final long serialVersionUID = 1L;
	
	
	//for getting (string) IP address of a node based on it's UID
	private Map<UID, RmiClientInterface> connectedNodes;
	private Map<UID, RmiClientInterface> connectedViewers;	
	
	private List<UID> subscribedViewers;
	
	//Stores IP addresses of nodes that need to be tested
	private List<InetAddress> importantNodes;
	
	
	private static class periodicGather extends TimerTask {
		private static Object lock = new Object();
		Server svr = null;
		public periodicGather(Server s){
			svr = s;
		}
		public void run(){
			synchronized(lock){
				svr.gatherSnapshots();
			}
		}
	}
	
	
	public Server() throws RemoteException
	{
		super();
		connectedNodes = new HashMap<UID, RmiClientInterface>();
		connectedViewers = new HashMap<UID, RmiClientInterface>();
		
		importantNodes = new ArrayList<InetAddress>();
		subscribedViewers = new ArrayList<UID>();
		
		//setup server on RMI
		Registry registry = LocateRegistry.getRegistry();
		registry.rebind("NetworkDiagSvr", this);
		
		Timer timer = new Timer();
		timer.schedule(new periodicGather(this),new Date(), 60000);
	}



	public void run() {

			
	}

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException{
		System.out.println("RMI Diagnostics Service\nServer Starting...");
		Server s = null;
		
		try{
			s = new Server();
			Thread t = new Thread(s);
			t.run();
			
			System.out.println("Server Started.");
			
			t.join();
		}
		catch(ConnectException ce){
			System.err.println("Failed to connect to RMI registry.");
			System.exit(-1);
		}
		catch(RemoteException re){
			System.err.println("Failed to setup RMI.");
			re.printStackTrace();
			System.exit(-1);
		}
		
	}
	private class snapshotRun implements Runnable {
		RmiClientInterface ri;
		Snapshot out;
		UID id;
		public snapshotRun (UID u,RmiClientInterface ri){
			this.ri = ri;
			id = u;
		}
		@Override
		public void run() {
			System.out.println("Snapshot fired.");
			try {
				out = ri.compileSnapshot();
			} catch (RemoteException e) {
				out = null;
				System.out.println("Failed to contact node with id " +id+", removed.");
			}
		}
		public Snapshot getResult(){
			return this.out;
		}
		
	}

	//TODO - locking required to stop new nodes/viewers being added. Causes problems when changing a list while iterating (throws error)
	private void gatherSnapshots(){
		List<Snapshot> allShots = new ArrayList<Snapshot>();
		//Iterator<Snapshot> allSnaps;
		Iterator<UID> subscribed;
		List<UID> toRemoveNodes = new ArrayList<UID>();
		List<UID> toRemoveViewers = new ArrayList<UID>();
		Iterator<UID> removing = null;
		Map<UID, Thread> jobs = new HashMap<UID, Thread>();
		Map<UID, snapshotRun> jobObj = new HashMap<UID, snapshotRun>();
		UID current = null;
		
		System.out.println("************************************");
		System.out.println("Requesting and Sending Snapshots ***");
		System.out.println("************************************");
		System.out.println("---- Gathering Snapshots -----");
		
		try {
			Iterator<UID> allNodes;
			synchronized(connectedNodes){
				allNodes = connectedNodes.keySet().iterator();
			}
			//go through all nodes and compile their snapshots
			while(allNodes.hasNext()){
				current = allNodes.next();
				System.out.println("Requesting snapshot from node " + current + "...");

				snapshotRun sr;
				synchronized(connectedNodes){
					sr = new snapshotRun(current, connectedNodes.get(current));
				}
				Thread t = new Thread(sr);
				jobObj.put(current, sr);
				jobs.put(current, t);

				t.start();
			}
			boolean finished = false;
			//go through all nodes and wait on their completion
			//	make sure that if interrupted to stop
			ArrayList<UID> jobQueue;
			synchronized(connectedNodes){
				jobQueue = new ArrayList<UID>(connectedNodes.keySet());
			}
			
			while(!finished){
				//go over all of threads and try to get data
				synchronized(jobQueue){
					Iterator<UID> i = jobQueue.iterator();				
					while(i.hasNext()){
						current = i.next();
						if(jobs.get(current).isAlive()){
							System.out.println("active job currently on node id: "+current);
							continue;
						}
						Snapshot jobResult = jobObj.get(current).getResult();
						if(jobResult == null){
							System.out.println("killing node id: "+current);
							toRemoveNodes.add(current);
						}else{
							System.out.println("adding job result from node id: "+current);
							allShots.add(jobResult);
							jobQueue.remove(current);
						}
					}
					//continue work until empty job queue
					if(jobQueue.isEmpty()){
						finished = true;
					}else{
						try {
							System.out.println("Going to sleep");
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
			
			System.out.println("------------------------------");
			System.out.println("----- Sending Snapshots ------");
			
			subscribed = subscribedViewers.iterator();
			
			while(subscribed.hasNext()){
				try{
					current = subscribed.next();
					connectedViewers.get(current).sendSnapshots(allShots);
					System.out.println("Snapshots sent to subscribed Viewer with id " + current);
				}
				catch(ConnectException ce){
					System.err.println("Error sending snapshots to subscribed Viewer with id "+current+", removed.");
					toRemoveViewers.add(current);
				}
				catch(ConnectIOException ioe){
					System.err.println("Error finding Viewer "+current+" while attempting to send snapshots, removed.");
					toRemoveViewers.add(current);
				}
				catch(Exception e){
					System.err.println("Unknown exception "+e.getMessage()+ "occured");
					toRemoveViewers.add(current);
				}
			}
			 
		//}catch (RemoteException e) {
			//System.out.println("An exception occurred: " + e.getMessage());
		}catch(Exception e){
			System.out.println("Unexpected exception: "+e.getMessage());
			e.printStackTrace();
		}
			
		
		finally{
			
			/* FR: Block here removes the nodes and viewers which were unreachable, as detected during compiling and sending of snapshots */
			removing = toRemoveViewers.iterator();
			synchronized(subscribedViewers){
				while(removing.hasNext()){
					current = removing.next();
					subscribedViewers.remove(current);
				}
			}
			
			removing = toRemoveViewers.iterator();
			synchronized(connectedViewers){
				
				while(removing.hasNext()){
					current = removing.next();
					connectedViewers.remove(current);
				}
			}
			
			removing = toRemoveNodes.iterator();
			synchronized(connectedNodes){
				while(removing.hasNext()){
					current = removing.next();
					connectedNodes.remove(current);
				}
			}
		}
		System.out.println("------------------------------");
		System.out.println("************************************");
		System.out.println("************************************");
		System.out.println("************************************\n");
		
	}
	
	
// REMOTE METHODS //	
	
	
	public UID register(InetAddress ip, clientType type, RmiClientInterface client) throws RemoteException {
		UID id = new UID(); //issue a new unique ID for the node
		
		switch(type){
			case NODE: 
				synchronized(connectedNodes){
					connectedNodes.put(id,client);
				}	
				break;
				
			case VIEWER:
				synchronized(connectedViewers){
					connectedViewers.put(id,client);
				}
				break;
				
			default:
				System.err.println("Unrecognised client type " + type + " attempted to register.");
				return null;
		}

		System.out.println("New " + type.toString().toLowerCase() + " with IP address \"" + ip + "\" registered with UID " + id.toString());
		return id;
	}
	


	public List<InetAddress> setup(UID id, clientType type) throws RemoteException {
		return importantNodes;
	}


	public void goodbye(UID id, clientType type) throws RemoteException {
		switch(type){
			case NODE: 
				synchronized(connectedNodes){
					connectedNodes.remove(id);
				}	
				break;
				
			case VIEWER:
				synchronized(connectedViewers){
					connectedViewers.remove(id);
				}
				break;
				
			default:
				System.err.println("Unrecognised client type " + type + " attempted to leave.");
				return;
		}

	
		System.out.println(type.toString().toLowerCase() + " with UID " + id.toString() + " has disconnected.");
		
	}


	public void addImportantNodes(List<InetAddress> moreNodes) throws RemoteException {
		importantNodes.addAll(moreNodes);
		RmiClientInterface currentAdd = null;
		
		//Update all connected nodes with new list
		synchronized(connectedNodes){
			Iterator<RmiClientInterface> allNodes = connectedNodes.values().iterator();
			
			while(allNodes.hasNext()){
				currentAdd = allNodes.next();
				currentAdd.updateImportantNodes(importantNodes);
			}
		}
		System.out.println("Added important nodes: " + moreNodes.toString());
	}


	public void removeImportantNodes(List<InetAddress> nodesToDel) throws RemoteException {
		synchronized(importantNodes){
			importantNodes.removeAll(nodesToDel);
			
			//Update all connected nodes with new list
			Iterator<RmiClientInterface> allNodes = connectedNodes.values().iterator();
			
			while(allNodes.hasNext()){
				allNodes.next().updateImportantNodes(importantNodes);
			}
		}
	}
	

	public boolean snapshotSubscribe(UID id, clientType type) throws RemoteException {
		if(type == clientType.NODE)
			return false;
		
		synchronized(connectedViewers){
			//check viewer is registered
			if (!connectedViewers.containsKey(id)){
				return false;
			}
		}
		synchronized(subscribedViewers){	
			//is registered, so add to list of subscribed Viewers
			subscribedViewers.add(id);
			
			return true;
		}
	}
	
	
}
