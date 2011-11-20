import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.server.UID;

public class Client implements Serializable{
	UID identifier;
	String ipaddr;
	public Client() throws UnknownHostException{
		identifier = new UID();
		ipaddr = InetAddress.getLocalHost().getHostAddress();
	}
	public UID getID(){return identifier;}
}

