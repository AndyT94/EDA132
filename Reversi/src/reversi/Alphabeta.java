package reversi;

import java.util.LinkedList;
import java.util.List;

public class Alphabeta {
	private Board board;

	public Alphabeta(Board board) {
		this.board = board;
	}

	public Action alphaBetaSearch(Board b) {
		int v = maxValue(b, Integer.MIN_VALUE, Integer.MAX_VALUE);
		//List<Action> actions = getAllActions(b);
		return null;
	}

	private int maxValue(Board b, int alpha, int beta) {
		if(terminalTest(b, Board.WHITE)) {
			return b.numberOfColor(Board.WHITE) - b.numberOfColor(Board.BLACK);
		}
		
		int v = Integer.MIN_VALUE;
		List<Action> actions = getAllActions(b);
		for(Action a : actions) {
			v = Math.max(v, minValue(b.takeAction(a, Board.WHITE), alpha, beta));
			if(v >= beta) {
				return v;
			}
			alpha = Math.max(alpha, v);
		}
		return v;
	}

	private int minValue(Board b, int alpha, int beta) {
		if(terminalTest(b, Board.BLACK)) {
			return b.numberOfColor(Board.WHITE) - b.numberOfColor(Board.BLACK);
		}
		int v = Integer.MAX_VALUE;
		List<Action> actions = getAllActions(b);
		for(Action a : actions) {
			v = Math.min(v, maxValue(b.takeAction(a, Board.WHITE), alpha, beta));
			if(v <= alpha) {
				return v;
			}
			beta = Math.min(beta, v);
		}
		return v;
	}

	private boolean terminalTest(Board b, int color) {
		return b.terminalTest(color);
	}

	private List<Action> getAllActions(Board b) {
		List<Action> result = new LinkedList<Action>();
		for (int i = 1; i < 9; i++) {
			for (int j = 1; j < 9; j++) {
				if (b.isLegalMove(i, j, Board.WHITE)) {
					result.add(new Action(i, j));
				}
			}
		}
		return result;
	}
}
