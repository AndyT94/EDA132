package model;

import java.util.ArrayList;

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
		return transitionMatrix[x * cols * head + y * head + h][nX * cols * head + nY * head + nH];
	}

	public double getCurrentProb(int x, int y) {
		double sum = 0;
		int start = x * cols * head + y * head;
		for (int i = start; i < start + head; i++) {
			sum += stateProbabilities[i];
		}
		return sum;
	}

	public void updateStateProbability(int[] reading) {
		Point sensorPoint = new Point(reading[0], reading[1]);
		ArrayList<Point> firstField = sensorPoint.getFirstField(rows, cols);
		ArrayList<Point> secondField = sensorPoint.getSecondField(rows, cols);		
		double[] sensorProbability = calcSensorProbability(sensorPoint, firstField, secondField);
		
		double[] tmp = new double[stateProbabilities.length];
		double alpha = 0;
		for(int i = 0; i < stateProbabilities.length; i++) {
			tmp[i] = 0;
			for(int j = 0; j < stateProbabilities.length; j++) {
				tmp[i] += sensorProbability[i] * transitionMatrix[j][i] * stateProbabilities[j];
			}
			alpha += tmp[i];
		}
		
		for(int i = 0; i < tmp.length; i++) {
			stateProbabilities[i] = tmp[i] / alpha;
		}
	}

	private double[] calcSensorProbability(Point sensorPoint, ArrayList<Point> firstField,
			ArrayList<Point> secondField) {
		double[] sensorProbability = new double[states.length];
		for (int i = 0; i < states.length; i++) {
			Point statePoint = states[i].getPoint();
			
			if(sensorPoint.getY() == -1 || sensorPoint.getX() == -1) {
				ArrayList<Point> stateFirstField = statePoint.getFirstField(rows, cols);
				ArrayList<Point> stateSecondField = statePoint.getSecondField(rows, cols);
				sensorProbability[i] = (1.0 - 0.1 - 0.05 * stateFirstField.size() - 0.025 * stateSecondField.size()) / head;
			} else if (statePoint.equals(sensorPoint)) {
				sensorProbability[i] = 0.1 / head;
			} else if (firstField.contains(statePoint)) {
				sensorProbability[i] = 0.05 / head;
			} else if (secondField.contains(statePoint)) {
				sensorProbability[i] = 0.025 / head;
			} else {
				sensorProbability[i] = 0.0;
			}
		}
		return sensorProbability;
	}
}
