package model;

import java.util.ArrayList;
import java.util.Random;

public class Robot {
	private static final Point NOTHING = new Point(-1, -1);
	private int rows;
	private int cols;
	private Random rand;
	private State currState;
	private Point currReading;

	public Robot(int rows, int cols, int head) {
		this.rows = rows;
		this.cols = cols;
		rand = new Random();
		currState = new State(new Point(rand.nextInt(rows), rand.nextInt(cols)), rand.nextInt(head));
		calcSensorOutput();
	}

	public int[] getCurrentPosition() {
		int[] res = new int[2];
		res[0] = currState.getPoint().getY();
		res[1] = currState.getPoint().getX();
		return res;
	}

	public void move() {
		if (rand.nextDouble() < 0.7 || currState.isEncounteringWall(rows, cols)) {
			ArrayList<State> sideNeighbours = currState.getSideNeighbours(rows, cols);
			int randNeighbour = rand.nextInt(sideNeighbours.size());
			currState = sideNeighbours.get(randNeighbour);
		} else {
			currState = currState.moveStraight();
		}
	}

	public int[] getCurrentReading() {
		int[] res = new int[2];
		res[0] = currReading.getY();
		res[1] = currReading.getX();
		return res;
	}

	public void calcSensorOutput() {
		double probability = rand.nextDouble();
		ArrayList<Point> firstField = currState.getPoint().getFirstField(rows, cols);
		ArrayList<Point> secondField = currState.getPoint().getSecondField(rows, cols);

		if (probability <= 0.1) {
			currReading = currState.getPoint();
		} else if (probability <= 0.1 + 0.05 * firstField.size()) {
			int randomNbr = rand.nextInt(firstField.size());
			currReading = firstField.get(randomNbr);
		} else if (probability <= 0.1 + 0.05 * firstField.size() + 0.025 * secondField.size()) {
			int randomNbr = rand.nextInt(secondField.size());
			currReading = secondField.get(randomNbr);
		} else {
			currReading = NOTHING;
		}
	}


	public double getOrXY(int rX, int rY, int x, int y) {
		Point truePoint = new Point(x, y);
		ArrayList<Point> firstField = truePoint.getFirstField(rows, cols);
		ArrayList<Point> secondField = truePoint.getSecondField(rows, cols);
		Point p = new Point(rX, rY);
		
		if(truePoint.equals(p)) {
			return 0.1;
		} else if (rX == -1 || rY == -1) {
			return 1.0 - 0.1 - 0.05 * firstField.size() - 0.025 * secondField.size();
		} else if (firstField.contains(p)) {
			return 0.05;
		} else if (secondField.contains(p)) {
			return 0.025;
		}
		return 0;
	}
}
