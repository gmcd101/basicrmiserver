import java.util.ArrayList;

public abstract class NetworkedSystem implements Runnable {

	private ArrayList<Snapshot> snapshots;
	
	/**
	 * Constructor
	 */
	public NetworkedSystem ()
	{
		snapshots = new ArrayList<Snapshot>();
	}
	
	/**
	 * never know when this could come in handy
	 * @return
	 */
	public ArrayList<Snapshot> getSnapshots()
	{
		return snapshots;
	}
	
	/**
	 * all parts need to run seperatly
	 */
	public abstract void run();
	

}
