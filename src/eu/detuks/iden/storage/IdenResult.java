package eu.detuks.iden.storage;

public class IdenResult {
	public String name;
	public String equal;
	public int percentage;
	
	public IdenResult(String name, String equal, int percentage) {
		this.name = name;
		this.percentage = percentage;
		this.equal = equal;
	}
}
