package decisiontree;

public class LeafNode implements Node {
	private Goal goal;
	
	public LeafNode(Goal goal) {
		this.goal = goal;
	}
	
	@Override
	public String print(String level) {
		return ": " + goal.toString();
	}
}
