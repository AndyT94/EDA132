package reversi;

import java.util.Scanner;

public class Game {
	public static void main(String[] args) {
		Board b = new Board();
		Scanner sc = new Scanner(System.in);

		System.out.println("Welcome! What is the preferred thinking time for the bot?");
		int thinkTime = sc.nextInt();
		Alphabeta ab = new Alphabeta(thinkTime * 1000);
		System.out.println("Setting thinking time for bot to: " + thinkTime + " seconds!");
		System.out.println("========================================");
		b.print();
		System.out.println("You are playing as black");

		boolean black = false;
		boolean white = false;
		while ((black = !b.terminalTest(Board.BLACK)) || (white = !b.terminalTest(Board.WHITE)) ) {
			if (black) {
				System.out.println("========================================");
				System.out.println("What is your next move?");
				String userMove = sc.next();
				while (!handleInput(userMove, b)) {
					userMove = sc.next();
				}
				int row = Character.getNumericValue(userMove.charAt(0));
				int col = Character.getNumericValue(userMove.charAt(1)) - 9;
				b = b.takeAction(new Action(row, col), Board.BLACK);
				b.print();
			} else {
				System.out.println("No valid move(s) left. Passing turn!");
			}

			if (white) {
				System.out.println("========================================");
				System.out.println("Bot thinking...");
				Board cpyBoard = new Board(b);
				Action botAction = ab.alphaBetaSearch(cpyBoard);
				System.out.println("Bot places disk on: " + botAction.x + (char) (botAction.y + 'a' - 1));
				b = b.takeAction(botAction, Board.WHITE);
				b.print();
			} else {
				System.out.println("No valid move(s) left. Passing turn!");
			}
		}

	}

	private static boolean handleInput(String userMove, Board b) {
		if (userMove.length() != 2) {
			System.out.println("Format: XY where X is one of 1-8 (row) and Y is one of a-h (column)!");
			return false;
		}
		userMove = userMove.toLowerCase();
		int row = Character.getNumericValue(userMove.charAt(0));
		int col = Character.getNumericValue(userMove.charAt(1)) - 9;
		if (b.isLegalMove(row, col, Board.BLACK)) {
			return true;
		} else {
			System.out.println("Illegal move! Format: XY where X is one of 1-8 (row) and Y is one of a-h (column)!");
			return false;
		}
	}
}
