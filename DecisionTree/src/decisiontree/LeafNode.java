package decisiontree;

public class LeafNode implements Node {
	private Goal goal;
	
	public LeafNode(Goal goal) {
		this.goal = goal;
	}
	
	@Override
	public String print(String indent) {
		return ": " + goal.toString();
	}

	@Override
	public boolean isLeafNode() {
		return true;
	}
}
