
public abstract class Client extends NetworkedSystem{
	RmiClientImpl rmi_c;
	String server;
	public Client(String server) {
		super();
		rmi_c = new RmiClientImpl();
		this.server = server;
	}

}
