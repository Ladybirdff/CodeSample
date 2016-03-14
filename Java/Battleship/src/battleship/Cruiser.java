package battleship;

public class Cruiser extends Ship{

	/**
	 * constructor
	 * set the length to 3
	 */
	public Cruiser() {
		this.length = 3;
		this.hit = new boolean[4];
	}

	@Override
	public String getShipType() {
		return "cruiser";
	}

}
