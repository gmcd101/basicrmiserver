import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;


public class RmiClientImpl implements RmiClientInterface{

	public RmiClientImpl(){
		

	}

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
	
}
