package decisiontree;

public class Main {

	public static void main(String[] args) {
		Relation r = DecisionTreeParser.parse("data/restaurant.arff");
		DecisionTreeAlgorithm decisionTree = new DecisionTreeAlgorithm();
		Node node = decisionTree.decisionTreeLearning(r.getExamples(), r.getAttributes(), r.getExamples());
		node.print("");
		System.out.println("DONE");
	}
}
