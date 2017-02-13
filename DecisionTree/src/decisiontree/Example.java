package decisiontree;

import java.util.Map;

public class Example {
	private Map<Attribute, String> data;
	private Goal goal;
	
	public Example(Map<Attribute, String> data, Goal goal) {
		this.data = data;
		this.goal = goal;
	}
	
	public String getValue(Attribute attribute) {
		return data.get(attribute);
	}
	
	public Goal getGoal() {
		return goal;
	}
}
