package battleship;

public class Destroyer extends Ship{

	/**
	 * constructor
	 * set the length to 2
	 */
	public Destroyer() {
		this.length = 2;
		this.hit = new boolean[4];
	}

	@Override
	public String getShipType() {
		return "destroyer";
	}

}
