package model;

import java.util.ArrayList;
import java.util.Random;

public class Robot {
	private int rows;
	private int cols;
	private int head;
	private Random rand;

	private State currState;
	private Point currReading;

	public Robot(int rows, int cols, int head) {
		this.rows = rows;
		this.cols = cols;
		this.head = head;
		rand = new Random();
		currState = new State(new Point(rand.nextInt(rows), rand.nextInt(cols)), rand.nextInt(head));
		calcSensorOutput();
	}

	public int[] getCurrentPosition() {
		int[] res = new int[2];
		res[0] = currState.getPoint().getX();
		res[1] = currState.getPoint().getY();
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
		res[0] = currReading.getX();
		res[1] = currReading.getY();
		return res;
	}
	
	public void calcSensorOutput() {
		double probability = rand.nextDouble();
		
		if(probability <= 0.1) {
			currReading = currState.getPoint();
		}
	}
}
