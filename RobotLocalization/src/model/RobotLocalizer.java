package model;

import control.EstimatorInterface;

public class RobotLocalizer implements EstimatorInterface {

	private int rows, cols, head;
	private ForwardAlgorithm algo;
	
	public RobotLocalizer( int rows, int cols, int head) {
		this.rows = rows;
		this.cols = cols;
		this.head = head;
		algo = new ForwardAlgorithm(rows, cols, head);
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public int[] getCurrentTruePosition() {
		return null;
	}

	@Override
	public int[] getCurrentReading() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getCurrentProb(int x, int y) {
		return algo.getCurrentProb(x, y);
	}

	@Override
	public double getOrXY(int rX, int rY, int x, int y) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getTProb(int x, int y, int h, int nX, int nY, int nH) {
		return algo.getTProb(x, y, h, nX, nY, nH);
	}

}
