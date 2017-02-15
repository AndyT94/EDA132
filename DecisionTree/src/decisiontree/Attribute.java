package decisiontree;

import java.util.Set;

public class Attribute {
	private String name;
	private Set<String> values;

	public Attribute(String name, Set<String> values) {
		this.name = name;
		this.values = values;
	}

	/**
	 * Returns the name of this attribute
	 * 
	 * @return A string
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the set of values this attribute holds
	 * 
	 * @return Set of string
	 */
	public Set<String> getValues() {
		return values;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Attribute other = (Attribute) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
