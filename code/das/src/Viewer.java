import java.util.ArrayList;


public class Viewer extends Client {
	
	ArrayList<String> servers;
	
	public Viewer(String server){
		super(server);
		servers = new ArrayList<String>();
		servers.add(server);
	}
	
	//TODO
	public void addServer(String ip){
		
	}
	
	//TODO
	public void removeServer(String ip){
		
	}
	
	public ArrayList<String> getServers(){
		return this.servers;
	}
	
	//TODO
	public void run() {

	}
	public static void main(String[] args) throws InterruptedException {
		System.out.println("RMI Diagnostics Service; Viewer - Server: "+args[0]);
		Viewer v = new Viewer (args[0]);
		Thread t = new Thread(v);
		t.run();
		t.wait();
	}

}
