package battleship;

public class EmptySea extends Ship{

	/**
	 * constructor
	 * set the length to 1
	 */
	public EmptySea() {
		this.length = 1;
	}

	/**
	 * an empty cannot be shot at
	 */
	@Override
	public boolean shootAt(int row, int column) {
		return false;
	}
	
	/**
	 * an empty cannot sink
	 */
	@Override
	public boolean isSunk() {
		return false;
	}
	
	@Override
	public String toString() {
		return "-";
	}
	
	@Override
	public String getShipType() {
		return "empty";
	}

}
