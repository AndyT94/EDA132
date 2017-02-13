package decisiontree;

public class Main {

	public static void main(String[] args) {
		Relation r = DecisionTreeParser.parse("data/restaurant.arff");
		System.out.println("DONE");
	}
}
