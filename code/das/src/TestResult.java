
public abstract class TestResult {
	String result;
	public TestResult(String result){
		this.result = result;
	}
	public String getResult(){
		return result;
	}
	protected abstract void parseResult();
}
