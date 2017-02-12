package decisiontree;

import java.util.Map;

public class Data {
	private Map<Attribute, String> data;
	private Goal goal;
	
	public Data(Map<Attribute, String> data, Goal goal) {
		this.data = data;
		this.goal = goal;
	}
	
	public String getValue(Attribute attribute) {
		return data.get(attribute);
	}
}
