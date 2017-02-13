package decisiontree;

import java.util.HashMap;
import java.util.List;

public class DecisionTreeAlgorithm {

	public Node decisionTreeLearning(List<Example> examples, List<Attribute> attributes, List<Example> parentExamples) {
		if (examples.isEmpty()) {
			return pluralityValue(parentExamples);
		} else if (hasSameClassification(examples)) {
			return new LeafNode(examples.get(0).getGoal());
		} else if (attributes.isEmpty()) {
			return pluralityValue(examples);
		} else {
			Attribute A = importance(attributes, examples);
			return null;
		}
	}

	//TODO: FIX TO CHOOSE BEST 
	private Attribute importance(List<Attribute> attributes, List<Example> examples) {
		return attributes.get(0);
	}

	private boolean hasSameClassification(List<Example> examples) {
		Goal goal = examples.get(0).getGoal();
		for (Example e : examples) {
			if (!goal.equals(e.getGoal())) {
				return false;
			}
		}
		return true;
	}

	private Node pluralityValue(List<Example> examples) {
		Goal result = null;
		int maxPlurality = 0;
		HashMap<Goal, Integer> count = new HashMap<Goal, Integer>();
		
		for (Example e : examples) {
			int i = 1;
			Goal g = e.getGoal();
			if(count.containsKey(g)) {
				i = count.get(g);
				i += 1;
				count.put(g, i);
			} else {
				count.put(g, i);
			}
			if(i > maxPlurality) {
				result = g;
			}
		}
		
		return new LeafNode(result);
	}
}
