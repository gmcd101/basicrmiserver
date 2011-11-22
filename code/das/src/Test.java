
public abstract class Test {
	String destination;
	public Test(String destination){
		this.destination = destination;
	}
	public abstract TestResult run();
}
