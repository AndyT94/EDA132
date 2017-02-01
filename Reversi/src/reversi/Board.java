package reversi;

import java.util.LinkedList;
import java.util.List;

public class Board {
	private int[][] board;

	private final static int EMPTY = 0;
	public final static int WHITE = 1;
	public final static int BLACK = 2;

	private final static int[] UPLEFT = { -1, -1 };
	private final static int[] UP = { 0, -1 };
	private final static int[] UPRIGHT = { 1, -1 };
	private final static int[] LEFT = { -1, 0 };
	private final static int[] RIGHT = { 1, 0 };
	private final static int[] DOWNLEFT = { -1, 1 };
	private final static int[] DOWN = { 0, 1 };
	private final static int[] DOWNRIGHT = { 1, 1 };
	
	public Board() {
		board = new int[10][10];
		board[4][4] = WHITE;
		board[4][5] = BLACK;
		board[5][4] = WHITE;
		board[5][5] = BLACK;
	}

	public Board(Board other) {
		board = new int[10][10];
		for(int i = 0; i < other.board.length; i++) {
			for(int j = 0; j < other.board.length; j++) {
				board[i][j] = other.board[i][j];
			}
		}
	}
	
	public void print() {
		System.out.println(" abcdefgh");
		for (int i = 1; i < 9; i++) {
			System.out.print(i);
			for (int j = 1; j < 9; j++) {
				String toPrint = "";
				switch (board[i][j]) {
				case EMPTY:
					toPrint = "-";
					break;
				case WHITE:
					toPrint = "W";
					break;
				case BLACK:
					toPrint = "B";
					break;
				}
				System.out.print(toPrint);
			}
			System.out.println();
		}
	}

	public boolean isLegalMove(int x, int y, int color) {
		if (x > 8 || x < 1 || y > 8 || y < 1 || board[x][y] != EMPTY) {
			return false;
		}

		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				int opp = getOpponent(color);
				if (board[x + i][y + j] == opp && checkLine(x, y, i, j, color)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean checkLine(int x, int y, int i, int j, int color) {
		int a = x + i;
		int b = y + j;
		while (board[a][b] == getOpponent(color)) {
			a += i;
			b += j;
			if (board[a][b] == color) {
				return true;
			} else if (board[a][b] == EMPTY) {
				return false;
			}
		}
		return false;

	}

	private int getOpponent(int color) {
		if (color == BLACK) {
			return WHITE;
		} else {
			return BLACK;
		}
	}
	
	public int numberOfColor(int color) {
		int sum = 0;
		for (int i = 1; i < 9; i++) {
			for (int j = 1; j < 9; j++) {
				if(board[i][j] == color) {
					sum++;
				}
			}
		}
		return sum;
	}
	
	public Board takeAction(Action a, int color) {
		Board res = new Board(this);
		res.board[a.x][a.y] = color;
		
		List<int[]> directions = new LinkedList<int[]>();
		if(checkLine(a.x, a.y, UPLEFT[0], UPLEFT[1], color)) {
			directions.add(UPLEFT);
		}
		if(checkLine(a.x, a.y, UP[0], UP[1], color)) {
			directions.add(UP);
		}
		if(checkLine(a.x, a.y, UPRIGHT[0], UPRIGHT[1], color)) {
			directions.add(UPRIGHT);
		}
		if(checkLine(a.x, a.y, LEFT[0], LEFT[1], color)) {
			directions.add(LEFT);
		}
		if(checkLine(a.x, a.y, RIGHT[0], RIGHT[1], color)) {
			directions.add(RIGHT);
		}
		if(checkLine(a.x, a.y, DOWNLEFT[0], DOWNLEFT[1], color)) {
			directions.add(DOWNLEFT);
		}
		if(checkLine(a.x, a.y, DOWN[0], DOWN[1], color)) {
			directions.add(DOWN);
		}
		if(checkLine(a.x, a.y, DOWNRIGHT[0], DOWNRIGHT[1], color)) {
			directions.add(DOWNRIGHT);
		}
		
		for(int i = 0; i < directions.size(); i++) {
			int x = a.x + directions.get(i)[0];
			int y = a.y + directions.get(i)[1];
			while(res.board[x][y] == getOpponent(color)) {
				res.board[x][y] = color;
				x = x + directions.get(i)[0];
				y = y + directions.get(i)[1];
			}
		}
		return res;
	}

	public boolean terminalTest(int color) {
		for (int i = 1; i < 9; i++) {
			for (int j = 1; j < 9; j++) {
				if(isLegalMove(i, j, color)) {
					return false;
				}
			}
		}
		return true;
	}
}
