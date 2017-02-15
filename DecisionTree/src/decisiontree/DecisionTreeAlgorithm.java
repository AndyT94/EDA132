package decisiontree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DecisionTreeAlgorithm {
	private Attribute goal;
	
	public DecisionTreeAlgorithm(Attribute goal) {
		this.goal = goal;
	}
	
	public Node decisionTreeLearning(List<Example> examples, List<Attribute> attributes, List<Example> parentExamples) {
		if (examples.isEmpty()) {
			return pluralityValue(parentExamples);
		} else if (hasSameClassification(examples)) {
			return new LeafNode(examples.get(0).getGoal());
		} else if (attributes.isEmpty()) {
			return pluralityValue(examples);
		} else {
			Attribute A = importance(attributes, examples);
			TreeNode root = new TreeNode(A);
			for (String value : A.getValues()) {
				ArrayList<Example> exs = new ArrayList<Example>();
				for (Example e : examples) {
					if (e.hasAttributeValue(A, value)) {
						exs.add(e);
					}
				}

				ArrayList<Attribute> newAttributes = new ArrayList<Attribute>();
				for (Attribute a : attributes) {
					if (!a.equals(A)) {
						newAttributes.add(a);
					}
				}

				Node subTree = decisionTreeLearning(exs, newAttributes, examples);
				root.addBranch(value, subTree);
			}
			return root;
		}
	}

	private Attribute importance(List<Attribute> attributes, List<Example> examples) {
		double maxGain = Double.NEGATIVE_INFINITY;
		Attribute bestAttribute = null;

		for (Attribute a : attributes) {
			if (!a.equals(goal)) {
				double gain = gain(a, examples);
				if (gain > maxGain) {
					maxGain = gain;
					bestAttribute = a;
				}
			}

		}
		return bestAttribute;
	}

	private double gain(Attribute attr, List<Example> examples) {
		double[] count = count(attr, examples);
		double p = count[0];
		double n = count[1];
		return B(p / (p + n)) - remainder(attr, examples);
	}

	private double[] count(Attribute attr, List<Example> examples) {
		double[] result = new double[2];
		for(Example e : examples) {
			if (e.getGoal().getValue().equals("yes")) {
				result[0]++;
			} else {
				result[1]++;
			}
		}
		return result;
	}

	private double remainder(Attribute attr, List<Example> examples) {
		double sum = 0;
		for (String value : attr.getValues()) {
			double pk = 0;
			double nk = 0;
			for (Example e : examples) {
				if (e.hasAttributeValue(attr, value)) {
					if (e.getGoal().getValue().equals("yes")) {
						pk++;
					} else {
						nk++;
					}
				}
			}
			sum += ((pk + nk) / examples.size()) * B(pk / (pk + nk));
		}
		return sum;
	}

	private double B(double q) {
		if(q > 0 && q < 1) {
			return -(q * log2(q) + (1 - q) * log2(1 - q));
		}
		return 0;
	}

	private double log2(double q) {
		return Math.log(q) / Math.log(2);
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
			if (count.containsKey(g)) {
				i = count.get(g);
				i += 1;
				count.put(g, i);
			} else {
				count.put(g, i);
			}
			if (i > maxPlurality) {
				result = g;
			}
		}

		return new LeafNode(result);
	}
	
	public Node pruning(Node tree) {
		if (tree.isLeafNode()) {
			return tree;
		} 
		
		
	}
}
