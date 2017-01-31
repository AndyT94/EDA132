package reversi;

public class Game {
	public static void main(String[] args) {
		Board b = new Board();
		b.print();
		System.out.println(b.isLegalMove(4, 6, Board.WHITE));
		System.out.println(b.isLegalMove(4, 6, Board.BLACK));
	}
}
