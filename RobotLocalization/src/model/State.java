package model;

public class State {
	public static final int NORTH = 0;
	public static final int EAST = 1;
	public static final int SOUTH = 2;
	public static final int WEST = 3;

	private Point point;
	private int direction;

	public State(Point point, int direction) {
		this.point = point;
		this.direction = direction;
	}

	public boolean isReachable(State other) {
		return point.getX() == other.point.getX() && point.getY() == other.point.getY() + 1 && other.direction == NORTH
				|| point.getX() == other.point.getX() && point.getY() == other.point.getY() - 1
						&& other.direction == SOUTH
				|| point.getX() == other.point.getX() - 1 && point.getY() == other.point.getY()
						&& other.direction == EAST
				|| point.getX() == other.point.getX() + 1 && point.getY() == other.point.getY()
						&& other.direction == WEST;
	}

	public double numberOfReachableStates(int rows, int cols) {
		double nbrOfReachable = 4;
		if (point.getX() <= 0) {
			nbrOfReachable--;
		}
		if (point.getX() >= cols - 1) {
			nbrOfReachable--;
		}
		if (point.getY() <= 0) {
			nbrOfReachable--;
		}
		if (point.getY() >= rows - 1) {
			nbrOfReachable--;
		}
		return nbrOfReachable;
	}

	public boolean isEncounteringWall(int rows, int cols) {
		return point.getX() <= 0 && direction == WEST || point.getX() >= cols - 1 && direction == EAST
				|| point.getY() <= 0 && direction == NORTH || point.getY() >= rows - 1 && direction == SOUTH;
	}

	public boolean hasSameDirection(State other) {
		return direction == other.direction;
	}

	public Point getPoint() {
		return point;
	}
}