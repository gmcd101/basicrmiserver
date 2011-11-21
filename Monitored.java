
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.UUID;
import java.rmi.server.UID;

public class Monitored {

	public Monitored ()
	{
		
		try
		{
			System.out.println("Setting up RMI Connection");
			ServerInterface si = setupRMI();
			System.out.println("Connection established, calling functions");
			UID id = new UID();
			String ip = InetAddress.getLocalHost().getHostAddress();
			rmiFuncs(si,ip);
			System.out.println("Client finished");
		}
		catch (Exception e)
		{
			System.out.println("Other issue.");
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{

		new Monitored();
	}

	private ServerInterface setupRMI () throws Exception
	{
		ServerInterface j = (ServerInterface) Naming.lookup("rmi://localhost/NetworkDiag");
		return j;
	}
	
	private void rmiFuncs (ServerInterface j, String ip) throws Exception
	{
		j.register(ip);
	}

}

