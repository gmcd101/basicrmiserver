import java.io.Serializable;


public abstract class TestResult implements Serializable {

	private static final long serialVersionUID = 1L;
	String result;
	public TestResult(String result){
		this.result = result;
	}
	public String getResult(){
		return result;
	}
	protected abstract void parseResult();
}
