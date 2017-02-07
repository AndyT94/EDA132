package reversi;

import java.util.LinkedList;
import java.util.List;

public class Alphabeta {
	private long thinkTime;
	private long startTime;
	private int maxDepth;
	
	public Alphabeta(long thinkTime) {
		this.thinkTime = thinkTime * 1000;
		maxDepth = (int) Math.ceil(thinkTime * 1.5);
	}

	public Action alphaBetaSearch(Board b) {
		startTime = System.currentTimeMillis();
		Action result =  maxValue(b, Integer.MIN_VALUE, Integer.MAX_VALUE, 1);
		System.out.println("Total time spent thinking: " + (System.currentTimeMillis() - startTime) + " ms");
		return result;
	}

	private Action maxValue(Board b, int alpha, int beta, int depth) {
		if(depth > maxDepth || System.currentTimeMillis() - (startTime + thinkTime) > 0 || terminalTest(b, Board.WHITE)) {
			return new Action(-1, -1, b.numberOfColor(Board.WHITE) - b.numberOfColor(Board.BLACK));
		}
		
		int v = Integer.MIN_VALUE;
		List<Action> actions = getAllActions(b);
		Action best = null;
		for(Action a : actions) {
			int vOld = v;
			v = Math.max(v, minValue(b.takeAction(a, Board.WHITE), alpha, beta, depth + 1));
			a.v = v;
			if(vOld != a.v) {
				best = a;
			}
			if(v >= beta) {
				return a;
			}
			alpha = Math.max(alpha, v);
		}
		return best;
	}

	private int minValue(Board b, int alpha, int beta, int depth) {
		if(depth > maxDepth || System.currentTimeMillis() - (startTime + thinkTime) > 0 || terminalTest(b, Board.BLACK)) {
			return b.numberOfColor(Board.WHITE) - b.numberOfColor(Board.BLACK);
		}
		int v = Integer.MAX_VALUE;
		List<Action> actions = getAllActions(b);
		for(Action a : actions) {
			v = Math.min(v, maxValue(b.takeAction(a, Board.BLACK), alpha, beta, depth + 1).v);
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
