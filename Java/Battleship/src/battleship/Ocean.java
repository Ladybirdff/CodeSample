package battleship;

import java.util.Random;

public class Ocean {

	Ship [][] ships;
	private String [][] display;
	private int shotsFired;
	private int hitCount;
	private int shipsSunk;
	private EmptySea emptySea;


	public Ocean() {
		emptySea = new EmptySea();
		this.ships = new Ship[10][10];
		for (int i=0; i<10; i++){
			for(int j=0; j<10; j++){
				this.emptySea.placeShipAt(i, j, true, this);
			}
		}
		this.shotsFired = 0;
		this.hitCount = 0;
		this.shipsSunk = 0;
		//construct a display array;
		displayArray();
	}
	/**
	 * Construct a displayArray used to facilitate print()
	 */
	private void displayArray(){
		this.display = new String [11][11];
		for (int i=0; i<11; i++){
			for (int j=0; j<11; j++){
				if (i == 0){
					display[i][j] = Integer.toString(j-1);
				}
				else if (j == 0){
					display[i][j] = Integer.toString(i-1);
				}
				else{
					display[i][j] = ".";
				}
			}
		}
		display[0][0] = " ";
	}

	/**
	 * randomly place all ships in the ocean;
	 */
	public void placeAllShipsRandomly(){
		Battleship battleShip = new Battleship();;// L:4
		Cruiser cruiser1 = new Cruiser();// L:3
		Cruiser cruiser2 = new Cruiser();// L:3
		Destroyer destroyer1 = new Destroyer(); //L: 2
		Destroyer destroyer2 = new Destroyer(); //L: 2
		Destroyer destroyer3 = new Destroyer(); //L: 2
		Submarine submarine1 = new Submarine(); //L: 1
		Submarine submarine2 = new Submarine(); //L: 1
		Submarine submarine3 = new Submarine(); //L: 1
		Submarine submarine4 = new Submarine(); //L: 1
		assistRandomPlace(battleShip);
		assistRandomPlace(cruiser1);
		assistRandomPlace(cruiser2);
		assistRandomPlace(destroyer1);
		assistRandomPlace(destroyer2);
		assistRandomPlace(destroyer3);
		assistRandomPlace(submarine1);
		assistRandomPlace(submarine2);
		assistRandomPlace(submarine3);
		assistRandomPlace(submarine4);
	}


	/**
	 * Randomly place one ship in the ocean
	 * @param ship
	 */
	public void assistRandomPlace(Ship ship){
		boolean c;
		int row = 0;
		int column = 0;
		Random random = new Random();
		boolean horizontal = false;
		c = false;
		while (!c){
			row = random.nextInt(10);
			column = random.nextInt(10);
			horizontal = random.nextBoolean();
			c = ship.okToPlaceShipAt(row, column, horizontal, this);
		}
		ship.placeShipAt(row, column, horizontal, this);
	}

	/**
	 * check if this place is occupied or not
	 * @param row
	 * @param column
	 * @return true, if this place is occupied
	 *         false, if this place is not occupied
	 */
	public boolean isOccupied(int row, int column){
		if (this.getShipArray()[row][column].equals(emptySea)){
			return false;
		}
		else{
			return true;
		}

	}


	/**
	 * assist to check whether the position is suitable or not, this
	 * is aiming to simplify the judgment of place a boat in Ship class
	 * @param row
	 * @param column
	 * @return true if the place 
	 */
	public boolean assistPlace(int row, int column){
		if (row < 0 || row >9 || column <0 || column>9){
			return true;
		}
		else if (this.isOccupied(row, column) == false){
			return true;
		}
		else {
			return false;
		}

	}
	
	/**
	 * shoot the ship in ocean and count shots, hits, and sunk
	 * @param row
	 * @param column
	 * @return
	 */
	public boolean shootAt(int row, int column){
		this.shotsFired ++;
		if (this.getShipArray()[row][column].shootAt(row, column)){
			this.hitCount ++;
			if (this.getShipArray()[row][column].isSunk()){
				this.shipsSunk ++;
			}
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * change the display of printfunction()
	 * @param row
	 * @param column
	 */
	public void changeDisplay(int row, int column){
		Ship x = getShipArray()[row][column];
		if (x.isSunk()){
			if (x.isHorizontal()){
				for (int i=1; i<=x.getLength(); i++){
					this.display[x.getBowRow()+1][x.getBowColumn()+i] = this.getShipArray()[x.getBowRow()][x.getBowColumn()].toString();
				}			
			}
			else{
				for (int i=1; i<=x.getLength(); i++){
					this.display[x.getBowRow()+i][x.getBowColumn()+1] = this.getShipArray()[x.getBowRow()][x.getBowColumn()].toString();
				}
			}
		}
		else{
			this.display[row+1][column+1] = this.getShipArray()[row][column].toString();
		}
	}
	
	/**
	 * get shots fired
	 * @return
	 */
	public int getShotsFired(){
		return shotsFired;

	}

	/**
	 * get Hits
	 * @return
	 */
	public int getHitCount(){
		return hitCount;

	}

	/**
	 * get number of ships sunk
	 * @return
	 */
	public int getShipsSunk(){
		return shipsSunk;

	}


	/**
	 * check if game is over or not
	 * @return
	 */
	public boolean isGameOver(){
		if (this.shipsSunk == 10){
			return true;
		}
		else {return false;}

	}

	/**
	 * get the array of ship
	 * @return
	 */
	public Ship[][] getShipArray(){
		return this.ships;

	}
	
	/**
	 * print the whole ocean
	 */
	public void print(){
		for (int i = 0; i < display.length; i++) {
			for (int j = 0; j < display[i].length; j++) {
				System.out.print(display[i][j] + " ");
			}
			System.out.println();
		}
	}






}
