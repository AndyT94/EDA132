package decisiontree;

public class DecisionTreeMain {

	public static void main(String[] args) {
//		if(args.length != 1) {
//			System.out.println("Wrong parameter length");
//			return;
//		}
		Relation r = DecisionTreeParser.parse("data/restaurant.arff");
		DecisionTreeAlgorithm decisionTree = new DecisionTreeAlgorithm(
				r.getAttributes().get(r.getAttributes().size() - 1));
		Node node = decisionTree.decisionTreeLearning(r.getExamples(), r.getAttributes(), r.getExamples());
		System.out.println(node.print(""));
		System.out.println("========================PRUNING========================");
		Node prune = decisionTree.pruning(node, r.getExamples());
		System.out.println(prune.print(""));
	}
}
