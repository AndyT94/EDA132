package model;

import control.EstimatorInterface;

public class RobotLocalizer implements EstimatorInterface {

	private int rows, cols, head;
	private ForwardAlgorithm algo;
	private Robot robot;
	private double sumManhattan;
	private int updates;
	private int correct;
	
	public RobotLocalizer( int rows, int cols, int head) {
		this.rows = rows;
		this.cols = cols;
		this.head = head;
		algo = new ForwardAlgorithm(rows, cols, head);
		robot = new Robot(rows, cols, head);
		sumManhattan = 0.0;
		updates = 0;
		correct = 0;
	}	
	
	@Override
	public int getNumRows() {
		return rows;
	}

	@Override
	public int getNumCols() {
		return cols;
	}

	@Override
	public int getNumHead() {
		return head;
	}

	@Override
	public void update() {
		robot.move();
		robot.calcSensorOutput();
		algo.updateStateProbability(robot.getCurrentReading());
		updates++;
		printAvgManhattanDistance();
	}

	private void printAvgManhattanDistance() {
		double maxProb = -1;
		Point maxPoint = null;
		for (int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				double prob = getCurrentProb(i, j);
				if(maxProb < prob) {
					maxProb = prob;
					maxPoint = new Point(i, j);
				}
			}
		}
		
		int[] truePos = getCurrentTruePosition();
		sumManhattan += Math.abs(truePos[0] - maxPoint.getY()) + Math.abs(truePos[1] - maxPoint.getX());
		System.out.println("Average manhattan distance error: " + sumManhattan / updates);
		
		if(maxPoint.getY() == truePos[0] && maxPoint.getX() == truePos[1]) {
			correct++;
		}
		System.out.println("CORRECT RATIO: " + (double) correct / updates);
		System.out.println();
	}

	@Override
	public int[] getCurrentTruePosition() {
		return robot.getCurrentPosition();
	}

	@Override
	public int[] getCurrentReading() {
		return robot.getCurrentReading();
	}

	@Override
	public double getCurrentProb(int x, int y) {
		return algo.getCurrentProb(x, y);
	}

	@Override
	public double getOrXY(int rX, int rY, int x, int y) {
		return robot.getOrXY(rX, rY, x, y);
	}

	@Override
	public double getTProb(int x, int y, int h, int nX, int nY, int nH) {
		return algo.getTProb(x, y, h, nX, nY, nH);
	}
}
