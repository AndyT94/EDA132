package decisiontree;

import java.util.ArrayList;
import java.util.List;

import umontreal.iro.lecuyer.probdist.ChiSquareDist;

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
		double[] count = count(examples);
		double p = count[0];
		double n = count[1];
		return B(p / (p + n)) - remainder(attr, examples);
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
		if (q > 0 && q < 1) {
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
		int positives = 0;
		int negatives = 0;
		
		for(Example e : examples) {
			Goal g = e.getGoal();
			if (g.getValue().equals("yes")) {
				positives++;
			} else {
				negatives++;
			}
			if (positives > maxPlurality || negatives > maxPlurality) {
				result = g;
				maxPlurality++;
			}
		}
		return new LeafNode(result);
	}

	public Node pruning(Node tree, List<Example> examples) {
		if (tree.isLeafNode()) {
			return tree;
		}

		TreeNode hypo = (TreeNode) tree;
		if (hypo.hasOnlyLeafNodes()) {
			double deviation = 0;
			for (String value : hypo.getBranches()) {
				ArrayList<Example> exs = new ArrayList<Example>();
				for (Example e : examples) {
					if (e.hasAttributeValue(hypo.getAttribute(), value)) {
						exs.add(e);
					}
				}
				double[] counthat = count(exs);
				double[] countExample = count(examples);
				double pkhat = countExample[0] * ((counthat[0] + counthat[1]) / examples.size());
				double nkhat = countExample[1] * ((counthat[0] + counthat[1]) / examples.size());

				if(pkhat > 0 && nkhat > 0) {
					deviation += Math.pow(counthat[0] - pkhat, 2) / pkhat + Math.pow(counthat[1] - nkhat, 2) / nkhat;
				}
				
			}

			int degrees = hypo.getBranches().size();
			if (degrees > 1) {
				degrees--;
			}
			double chisquare = ChiSquareDist.inverseF(degrees, 0.95);
			
			if(deviation < chisquare) {
				return pluralityValue(examples);
			} else {
				return hypo;
			}

		} else {
			TreeNode newTree = new TreeNode(hypo.getAttribute());
			for (String value : hypo.getBranches()) {
				ArrayList<Example> exs = new ArrayList<Example>();
				for (Example e : examples) {
					if (e.hasAttributeValue(hypo.getAttribute(), value)) {
						exs.add(e);
					}
				}
				newTree.addBranch(value, pruning(hypo.getNode(value), exs));
			}
			
			if(newTree.hasOnlyLeafNodes()) {
				return pruning(newTree, examples);
			} else {
				return newTree;
			}
		}
	}

	private double[] count(List<Example> examples) {
		double[] result = new double[2];
		for (Example e : examples) {
			if (e.getGoal().getValue().equals("yes")) {
				result[0]++;
			} else {
				result[1]++;
			}
		}
		return result;
	}
}
