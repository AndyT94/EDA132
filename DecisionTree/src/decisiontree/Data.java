package decisiontree;

import java.util.Map;

public class Data {
	private Map<Attribute, String> data;
	
	public Data(Map<Attribute, String> data) {
		this.data = data;
	}
	
	public String getValue(Attribute attribute) {
		return data.get(attribute);
	}
}
