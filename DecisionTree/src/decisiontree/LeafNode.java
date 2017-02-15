package decisiontree;

public class LeafNode implements Node {
	private Goal goal;

	public LeafNode(Goal goal) {
		this.goal = goal;
	}

	/**
	 * Prints the value of this LeafNode
	 */
	@Override
	public String print(String indent) {
		return ": " + goal.getValue();
	}

	/**
	 * Returns true
	 */
	@Override
	public boolean isLeafNode() {
		return true;
	}
}
