package decisiontree;

import java.util.List;

public class Relation {
	private String name;
	private List<Attribute> attributes;
	private List<Example> data;

	public Relation(String name, List<Attribute> attributes, List<Example> data) {
		this.name = name;
		this.attributes = attributes;
		this.data = data;
	}

	/**
	 * Returns the list of attributes
	 * 
	 * @return List of Attribute
	 */
	public List<Attribute> getAttributes() {
		return attributes;
	}

	/**
	 * Returns the list of examples
	 * 
	 * @return List of Example
	 */
	public List<Example> getExamples() {
		return data;
	}

	/**
	 * Returns the name of the relation
	 * 
	 * @return The name as string
	 */
	public String getName() {
		return name;
	}
}
