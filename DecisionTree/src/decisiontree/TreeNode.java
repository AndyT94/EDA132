package decisiontree;

import java.util.HashMap;
import java.util.Map;

public class TreeNode implements Node {
	private Attribute attribute;
	private Map<String, Node> branches;
	
	public TreeNode(Attribute attribute) {
		this.attribute = attribute;
		branches = new HashMap<String, Node>();
	}
	
	@Override
	public String print(String indent) {
		return "";
	}

	public void addBranch(String value, Node subTree) {
		branches.put(value, subTree);
	}
}
