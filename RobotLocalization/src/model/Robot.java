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
		ArrayList<Point> firstField = getFirstField(currState.getPoint());
		ArrayList<Point> secondField = getSecondField(currState.getPoint());

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

	private ArrayList<Point> getSecondField(Point point) {
		ArrayList<Point> secondField = new ArrayList<Point>();

		for (int i = -2; i < 3; i++) {
			Point p = new Point(point.getY() - 2, point.getX() + i);
			if (isInsideBorders(p)) {
				secondField.add(p);
			}

			p = new Point(point.getY() + 2, point.getX() + i);
			if (isInsideBorders(p)) {
				secondField.add(p);
			}
		}

		for (int i = -1; i < 2; i++) {
			Point p = new Point(point.getY() + i, point.getX() - 2);
			if (isInsideBorders(p)) {
				secondField.add(p);
			}

			p = new Point(point.getY() + i, point.getX() + 2);
			if (isInsideBorders(p)) {
				secondField.add(p);
			}
		}
		return secondField;
	}

	private ArrayList<Point> getFirstField(Point point) {
		ArrayList<Point> firstField = new ArrayList<Point>();

		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				Point p = new Point(point.getY() + i, point.getX() + j);
				if (isInsideBorders(p) && !point.equals(p)) {
					firstField.add(p);
				}
			}
		}
		return firstField;
	}

	private boolean isInsideBorders(Point p) {
		return p.getX() >= 0 && p.getX() < cols && p.getY() >= 0 && p.getY() < rows;
	}

	public double getOrXY(int rX, int rY, int x, int y) {
		Point truePoint = new Point(x, y);
		ArrayList<Point> firstField = getFirstField(truePoint);
		ArrayList<Point> secondField = getSecondField(truePoint);
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
