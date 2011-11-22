import java.util.ArrayList;


public class Server extends NetworkedSystem{
	RmiServerImpl rmi_s;
	ArrayList<String> testingNodes;
	
	public Server()
	{
		super();
		rmi_s = new RmiServerImpl();
		testingNodes = new ArrayList<String>();
	}


	// TODO write
	public void run() {

	}

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException{
		System.out.println("RMI Diagnostics Service; Server");
		Server s = new Server();
		Thread t = new Thread(s);
		t.run();
		t.wait();
	}
	
	//TODO write
	private void gatherSnapshots(){
		
	}
	
}
