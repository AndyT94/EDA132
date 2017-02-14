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

	public void addBranch(String value, Node subTree) {
		// TODO Auto-generated method stub
		
	}
}
