package decisiontree;

import java.util.Map;

public class Example {
	private Map<Attribute, String> data;
	private Goal goal;

	public Example(Map<Attribute, String> data, Goal goal) {
		this.data = data;
		this.goal = goal;
	}

	/**
	 * Gets the value corresponding to the attribute parameter
	 * 
	 * @param attribute
	 *            The attribute to search from
	 * @return The value as string
	 */
	public String getValue(Attribute attribute) {
		return data.get(attribute);
	}

	/**
	 * Returns the goal for this example
	 * 
	 * @return The goal
	 */
	public Goal getGoal() {
		return goal;
	}

	/**
	 * Checks if this example has a attribute value equal to value
	 * 
	 * @param attr
	 *            The attribute
	 * @param value
	 *            The value to compare with
	 * @return true if value is in example else false
	 */
	public boolean hasAttributeValue(Attribute attr, String value) {
		return data.get(attr).equals(value);
	}
}
