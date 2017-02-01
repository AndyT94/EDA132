package reversi;

public class Action {
	public int x;
	public int y;
	public int v;
	
	public Action(int x, int y, int v) {
		this.x = x;
		this.y = y;
		this.v = v;
	}
	
	public Action(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
