package model;

import java.util.ArrayList;

public class Point {
	private int x;
	private int y;
	
	public Point(int y, int x) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	public ArrayList<Point> getSecondField(int rows, int cols) {
		ArrayList<Point> secondField = new ArrayList<Point>();

		for (int i = -2; i < 3; i++) {
			Point p = new Point(y - 2, x + i);
			if (p.isInsideBorders(rows, cols)) {
				secondField.add(p);
			}

			p = new Point(y + 2, x + i);
			if (p.isInsideBorders(rows, cols)) {
				secondField.add(p);
			}
		}

		for (int i = -1; i < 2; i++) {
			Point p = new Point(y + i, x - 2);
			if (p.isInsideBorders(rows, cols)) {
				secondField.add(p);
			}

			p = new Point(y + i, x + 2);
			if (p.isInsideBorders(rows, cols)) {
				secondField.add(p);
			}
		}
		return secondField;
	}

	public ArrayList<Point> getFirstField(int rows, int cols) {
		ArrayList<Point> firstField = new ArrayList<Point>();

		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				Point p = new Point(y + i, x + j);
				if (p.isInsideBorders(rows, cols) && !this.equals(p)) {
					firstField.add(p);
				}
			}
		}
		return firstField;
	}
	
	private boolean isInsideBorders(int rows, int cols) {
		return x >= 0 && x < cols && y >= 0 && y < rows;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
}
