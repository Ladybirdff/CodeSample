package battleship;

public class Battleship extends Ship{

	/**
	 * constructor
	 * set the length to 4
	 */
	public Battleship() {
		this.length = 4;
		this.hit = new boolean[4];
	}

	@Override
	public String getShipType() {
		return "battleship";
	}
	
}
