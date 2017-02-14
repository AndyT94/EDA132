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
		StringBuilder sb = new StringBuilder();
		for (String value : attribute.getValues()) {
			sb.append(indent);
			sb.append(attribute.getName() + " = ");
			sb.append(value);

			Node node = branches.get(value);
			if (node.isLeafNode()) {
				sb.append(node.print("") + "\n");
			} else {
				sb.append("\n" + node.print(indent + "\t"));
			}
		}

		return sb.toString();
	}

	public void addBranch(String value, Node subTree) {
		branches.put(value, subTree);
	}

	@Override
	public boolean isLeafNode() {
		return false;
	}
}
