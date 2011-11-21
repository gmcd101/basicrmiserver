
import java.rmi.Naming;
import java.util.ArrayList;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server implements Runnable
{
	ArrayList<Node> cs;
	public static void main(String[] args)
	{
		System.out.println("Start");
		new Server();
		//Thread t =
		System.out.println("Finished");
	}
	
	public Server ()
	{
		cs = new ArrayList<Node>();
		System.out.println("Setting up RMI Server");
		try
		{
			setRMI();
		}
		catch (Exception e)
		{
			System.out.println("Failure");
			e.printStackTrace();
		}
	}
	private void setRMI() throws Exception
	{
		Registry registry = LocateRegistry.getRegistry();
		ServerInterfaceImpl r = new ServerInterfaceImpl();
		registry.rebind("NetworkDiag", r);
		System.out.println("Set up successful: listening");
	}
	public void run(){
		
	}

}

