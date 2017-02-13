package decisiontree;

public class TreeNode implements Node {
	private Attribute attribute;
	
	public TreeNode(Attribute attribute) {
		this.attribute = attribute;
	}
	@Override
	public String print(String indent) {
		return "";
	}
}
