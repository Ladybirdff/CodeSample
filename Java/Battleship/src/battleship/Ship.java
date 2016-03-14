package battleship;

public abstract class Ship {

	protected int bowRow;
	protected int bowColumn;
	protected int length;
	protected boolean horizontal;
	protected boolean [] hit = new boolean[4];


	public Ship() {
	}

	//Getters
	public int getLength() {
		return length;
	}

	public int getBowRow() {
		return bowRow;
	}

	public int getBowColumn() {
		return bowColumn;
	}

	public boolean isHorizontal(){
		return horizontal;
	}

	//Setters
	public void setBowRow(int bowRow) {
		this.bowRow = bowRow;
	}

	public void setBowColumn(int bowColumn) {
		this.bowColumn = bowColumn;
	}

	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
	}

	//abstract method
	public abstract String getShipType();

	/**
	 * check whether it is okay to put a ship in a certain point
	 * the ship should not overlap another ship or touch other ship
	 * can not "stick" out beyond the array
	 * @param row
	 * @param column
	 * @param horizontal
	 * @param ocean
	 * @return
	 */
	public boolean okToPlaceShipAt(int row, int column, boolean horizontal, Ocean ocean){
		if (horizontal){
			if (column > 10-length){return false;} //stick out beyond the array
			else {
				for (int j=column-1; j<=column+length; j++){
					if (!ocean.assistPlace(row-1, j)){return false;}
					if (!ocean.assistPlace(row, j)){return false;}
					if (!ocean.assistPlace(row+1, j)){return false;}
				}
				return true;
			}
			
		}
		else {
			if (row > 10-length){return false;} //stick out beyond the array
			else {
				for (int i=row-1; i<=row+length; i++){
					if (!ocean.assistPlace(i, column-1)){return false;}
					if (!ocean.assistPlace(i, column)){return false;}
					if (!ocean.assistPlace(i, column+1)){return false;}
				}
				return true;
			}
			
		}
	}

	/**
	 * put a ship in a certain location
	 * giving values to the instance variables of ship
	 * putting reference to ship in the ships array in ocean
	 * @param row
	 * @param column
	 * @param horizontal
	 * @param ocean
	 */
	public void placeShipAt(int row, int column, boolean horizontal, Ocean ocean){
		this.setBowRow(row);
		this.setBowColumn(column);
		this.setHorizontal(horizontal);
		if (horizontal){
			for (int j=column; j<=column+length-1; j++){
				ocean.ships[row][j] = this;
			}
		}
		if (!horizontal){
			for (int i=row; i<=row+length-1; i++){
				ocean.ships[i][column] = this;
			}
		}
	}

	/**
	 * if a part of the ship occupies the given location and it is not sunk, should return true
	 * the certain value in hit array of ship should be given true 
	 * @param row
	 * @param column
	 * @return
	 */
	public boolean shootAt(int row, int column){
		if (this.isSunk()){return false;}
		else {
			if (this.horizontal){
				if ((row == bowRow) && (column >= bowColumn) && (column <= bowColumn+length-1)){
					hit[column-bowColumn] = true;
					return true;
				}
				else {return false;}
			}
			else {
				if ((column == bowColumn) && (row >= bowRow) && (row <= bowRow+length-1)){
					hit[row-bowRow] = true;
					return true;
				}
				else {return false;}
			}
		}
	}

	/**
	 * check whether the ship is sunk
	 * @return
	 */
	public boolean isSunk(){
		for (int i=0; i<=length-1; i++){
			if (hit[i] != true){return false;}
		}
		return true;
	}

	/**
	 * return "x" for ships have been sunk
	 * "S" for hit
	 */
	@Override
	public String toString() {
		if (this.isSunk()){return "x";}
		else {return "S";}
	}
}
