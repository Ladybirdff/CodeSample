package battleship;

public class Submarine extends Ship{

	/**
	 * constructor
	 * set the length to 1
	 */
	public Submarine() {
		this.length = 1;
		this.hit = new boolean[4];
	}

	@Override
	public String getShipType() {
		return "submarine";
	}

}
