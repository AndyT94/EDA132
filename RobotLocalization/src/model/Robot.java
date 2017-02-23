package model;

import java.util.Random;

public class Robot {
	private int rows;
	private int cols;
	private int head;
	private Random rand;
	
	private State currState;
	
	public Robot(int rows, int cols, int head) {
		this.rows = rows;
		this.cols = cols;
		this.head = head;
		rand = new Random();
		currState = new State(new Point(rand.nextInt(rows), rand.nextInt(cols)), rand.nextInt(head));
	}

	public int[] getCurrentPosition() {
		int[] res = new int[2];
		res[0] = currState.getPoint().getX();
		res[1] = currState.getPoint().getY();
		return res;
	}
}
