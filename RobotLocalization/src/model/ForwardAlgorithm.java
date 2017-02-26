package model;

public class ForwardAlgorithm {
	private int rows;
	private int cols;
	private int head;
	private State[] states;
	private double[] stateProbabilities;
	private double[][] transitionMatrix;

	public ForwardAlgorithm(int rows, int cols, int head) {
		this.rows = rows;
		this.cols = cols;
		this.head = head;
		initStates();
		initTransitionMatrix();
	}

	private void initStates() {
		int nbrStates = rows * cols * head;
		states = new State[nbrStates];
		stateProbabilities = new double[nbrStates];

		for (int i = 0; i < rows * cols; i++) {
			Point p = new Point(i / cols, i % cols);
			for (int j = State.NORTH; j < State.NORTH + 4; j++) {
				State state = new State(p, j);
				int index = i * 4 + j;
				states[index] = state;
				stateProbabilities[index] = 1.0 / nbrStates;
			}
		}
	}

	private void initTransitionMatrix() {
		int nbrStates = rows * cols * head;
		transitionMatrix = new double[nbrStates][nbrStates];

		for (int i = 0; i < nbrStates; i++) {
			State currState = states[i];
			for (int j = 0; j < nbrStates; j++) {
				State other = states[j];
				if (currState.isReachable(other)) {
					if (currState.isEncounteringWall(rows, cols)) {
						transitionMatrix[i][j] = 1.0 / currState.numberOfReachableStates(rows, cols);
					} else if (currState.hasSameDirection(other)) {
						transitionMatrix[i][j] = 0.7;
					} else {
						transitionMatrix[i][j] = 0.3 / (currState.numberOfReachableStates(rows, cols) - 1);
					}
				} else {
					transitionMatrix[i][j] = 0.0;
				}
			}
		}
	}

	public double getTProb(int x, int y, int h, int nX, int nY, int nH) {
		return transitionMatrix[y * cols + x * cols * head + h][nY * cols + nX * cols * head + nH];
	}

	public double getCurrentProb(int x, int y) {
		double sum = 0;
		for (int i = x + y; i < x + y + 4; i++) {
			sum += stateProbabilities[i];
		}
		return sum;
	}

	public void updateStateProbability(int[] reading) {
		
	}
}
