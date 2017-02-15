package decisiontree;

public class Main {

	public static void main(String[] args) {
		Relation r = DecisionTreeParser.parse("data/restaurant.arff");
		DecisionTreeAlgorithm decisionTree = new DecisionTreeAlgorithm(
				r.getAttributes().get(r.getAttributes().size() - 1));
		Node node = decisionTree.decisionTreeLearning(r.getExamples(), r.getAttributes(), r.getExamples());
		System.out.println(node.print(""));
		Node prune = decisionTree.pruning(node, r.getExamples());
	}
}
