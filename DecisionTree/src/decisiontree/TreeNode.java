package decisiontree;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TreeNode implements Node {
	private Attribute attribute;
	private Map<String, Node> branches;

	public TreeNode(Attribute attribute) {
		this.attribute = attribute;
		branches = new HashMap<String, Node>();
	}

	/**
	 * Prints the values of this TreeNode with indent
	 */
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

	/**
	 * Adds a branch to this node
	 * 
	 * @param value
	 *            The branch
	 * @param subTree
	 *            The subtree created from the branch
	 */
	public void addBranch(String value, Node subTree) {
		branches.put(value, subTree);
	}

	/**
	 * Returns false
	 */
	@Override
	public boolean isLeafNode() {
		return false;
	}

	/**
	 * Checks if this Node only has LeafNodes or not
	 * 
	 * @return true if the branches is only of LeafNode else false
	 */
	public boolean hasOnlyLeafNodes() {
		for (Node n : branches.values()) {
			if (!n.isLeafNode()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns the name of the branches
	 * 
	 * @return Set of keys as string
	 */
	public Set<String> getBranches() {
		return branches.keySet();
	}

	/**
	 * Returns the attribute in this node
	 * 
	 * @return The attribute
	 */
	public Attribute getAttribute() {
		return attribute;
	}

	/**
	 * Returns the node from a branch
	 * 
	 * @param branch
	 *            The branch to search from
	 * @return A node
	 */
	public Node getNode(String branch) {
		return branches.get(branch);
	}
}
