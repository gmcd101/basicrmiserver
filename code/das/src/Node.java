import java.util.ArrayList;


public class Node extends Client {
	
	ArrayList<Test> tests;
	
	public Node(String server){
		super(server);
		tests = new ArrayList<Test>();
	}
	
	public void run() {
		// TODO Auto-generated method stub

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws InterruptedException {
		System.out.println("RMI Diagnostics Service; Node - Server: "+args[0]);
		Node n = new Node (args[0]);
		Thread t = new Thread(n);
		t.run();
		t.wait();
	}
	
}
