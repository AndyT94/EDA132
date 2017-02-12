package decisiontree;

import java.util.List;

public class Relation {
	private String name;
	private List<Attribute> attributes;
	private List<Data> data;
	
	public Relation(String name, List<Attribute> attributes, List<Data> data) {
		this.name = name;
		this.attributes = attributes;
		this.data = data;
	}
	
	public List<Attribute> getAttributes() {
		return attributes;
	}
	
	public List<Data> getData() {
		return data;
	}
}
