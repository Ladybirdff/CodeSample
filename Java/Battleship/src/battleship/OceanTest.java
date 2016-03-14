package battleship;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class OceanTest {
	Ocean ocean;
	int battleshipCount;
	int cruiserCount;
	int destroyerCount;
	int submarineCount;
	int emptySeaCount;

	@Before
	public void setUp() throws Exception {
		this.ocean = new Ocean();	
		this.battleshipCount = 0;
		this.cruiserCount = 0;
		this.destroyerCount = 0;
		this.emptySeaCount = 0;
		this.submarineCount = 0;
	}
	
	/*
	 * test getShipArray() and Constructor
	 */
	@Test
	public void testOcean() {
		EmptySea emptySea = new EmptySea();
		assertFalse(emptySea.equals(ocean.getShipArray()[1][2]));
		assertFalse(emptySea.equals(ocean.getShipArray()[9][9]));
		assertFalse(emptySea.equals(ocean.getShipArray()[5][4]));
		assertFalse(emptySea.equals(ocean.getShipArray()[3][7]));
		assertEquals(ocean.getHitCount(), 0);
		assertEquals(ocean.getShipsSunk(), 0);
		assertEquals(ocean.getShotsFired(), 0);
		countShip();
		assertEquals(battleshipCount, 0);
		assertEquals(cruiserCount, 0);
		assertEquals(destroyerCount, 0);
		assertEquals(submarineCount, 0);
		assertEquals(emptySeaCount, 100);


	}
	private void countShip(){
		for (int i=0; i<10; i++){
			for (int j=0; j<10; j++){
				String a = ocean.getShipArray()[i][j].getShipType();
				
				if (a.equals("battleship")){
					battleshipCount ++;		
				}
				else if (a.equals("cruiser")){
					cruiserCount ++;		
				}
				else if (a.equals("destroyer")){
					destroyerCount ++;		
				}
				else if (a.equals("submarine")){
					submarineCount ++;		
				}
				else if (a.equals("empty")){
					emptySeaCount ++;
				}
			}
		}
	}
	
	/*
	 * test assistRandomPlace() and placeAllShipsRandomly()
	 */
	@Test
	public void testPlaceAllShipsRandomly() {
		ocean.placeAllShipsRandomly();
		countShip();
		assertEquals(battleshipCount, 4);
		assertEquals(cruiserCount, 6);
		assertEquals(destroyerCount, 6);
		assertEquals(submarineCount, 4);
		assertEquals(emptySeaCount, 80);

		
	}
	
	/**
	 * Two ships are place in the ocean
	 * one is cruiser, whose bow is at (0,0)
	 * one is battleship, whose bow is at (2,3)
	 */
	public void placeTwoShip() {
		Battleship battleship = new Battleship();
		Cruiser cruiser = new Cruiser();
		cruiser.placeShipAt(0, 0, true, ocean);
		battleship.placeShipAt(2, 3, false, ocean);
	}

	@Test
	public void testIsOccupied() {
		placeTwoShip();
		assertTrue(ocean.isOccupied(0, 0));//cruiser
		assertTrue(ocean.isOccupied(0, 1));//cruiser
		assertTrue(ocean.isOccupied(0, 2));//cruiser
		assertTrue(ocean.isOccupied(2, 3));//battleship
		assertTrue(ocean.isOccupied(3, 3));//battleship
		assertTrue(ocean.isOccupied(4, 3));//battleship
		assertTrue(ocean.isOccupied(5, 3));//battleship
		assertFalse(ocean.isOccupied(9, 0));//emptySea	
	}

	/**
	 * test ShootAt() and getFunctions
	 */
	@Test
	public void testShootAt() {
		//test initially get functions
		assertEquals(ocean.getShotsFired(),0);
		assertEquals(ocean.getHitCount(),0);
		assertEquals(ocean.getShipsSunk(),0);	
		assertFalse(ocean.shootAt(0, 0));//hit emptySea
		placeTwoShip();
		//start to hit ships
		assertTrue(ocean.shootAt(0, 0));//hit cruiser
		assertTrue(ocean.shootAt(0, 0));//hit cruiser at the same point again
		assertTrue(ocean.shootAt(0, 1));//hit cruiser
		assertTrue(ocean.shootAt(0, 2));//hit cruiser
		assertFalse(ocean.shootAt(0, 2));//the cruiser is sinked, therefore should return false
		assertTrue(ocean.shootAt(2, 3));//hit battleship
		assertTrue(ocean.shootAt(3, 3));//hit battleship
		assertTrue(ocean.shootAt(4, 3));//hit battleship
		assertTrue(ocean.shootAt(5, 3));//hit battleship
		assertFalse(ocean.shootAt(9, 9));//hit emptySea
		assertFalse(ocean.shootAt(5, 3));//the battleship is sinked, therefore should return false
		//test getfuncitons()
		assertEquals(ocean.getShotsFired(),12);		
		assertEquals(ocean.getHitCount(),8);		
		assertEquals(ocean.getShipsSunk(),2);
	}

	
	@Test
	public void changeDisplay() {
		assertEquals(ocean.getShipArray()[0][0].toString(),"-");
		placeTwoShip();
		assertEquals(ocean.getShipArray()[0][0].toString(),"S");
	}

	
	@Test
	public void testIsGameOver() {
		assertFalse(ocean.isGameOver());
		Battleship battleship = new Battleship();//place one battleship
		battleship.placeShipAt(0, 0, true, ocean);
		Cruiser cruiser1 = new Cruiser();
		Cruiser cruiser2 = new Cruiser();
		cruiser1.placeShipAt(2, 0, true, ocean);//place two cruisers
		cruiser2.placeShipAt(4, 0, true, ocean);
		Destroyer destroyer1 = new Destroyer();
		Destroyer destroyer2 = new Destroyer();
		Destroyer destroyer3 = new Destroyer();
		destroyer1.placeShipAt(6, 0, true, ocean);//place three destroyers
		destroyer2.placeShipAt(8, 0, true, ocean);
		destroyer3.placeShipAt(8, 3, true, ocean);
		Submarine submarine1 = new Submarine();
		Submarine submarine2 = new Submarine();
		Submarine submarine3 = new Submarine();
		Submarine submarine4 = new Submarine();
		submarine1.placeShipAt(4, 4, true, ocean);//place four submarines
		submarine2.placeShipAt(4, 6, true, ocean);
		submarine3.placeShipAt(6, 4, true, ocean);
		submarine4.placeShipAt(6, 6, true, ocean);
		//this.ocean.print();
		countShip();
		System.out.println(this.battleshipCount);
		System.out.println(this.cruiserCount);
		System.out.println(this.destroyerCount);
		System.out.println(this.submarineCount);
		ocean.shootAt(0, 0);//shoot battleship
		ocean.shootAt(0, 1);
		ocean.shootAt(0, 2);
		ocean.shootAt(0, 3);
		ocean.shootAt(2, 0);//shoot cruiser1
		ocean.shootAt(2, 1);
		ocean.shootAt(2, 2);
		ocean.shootAt(4, 0);//shoot cruiser2
		ocean.shootAt(4, 1);
		ocean.shootAt(4, 2);
		ocean.shootAt(6, 0);//shoot destroyer1
		ocean.shootAt(6, 1);
		ocean.shootAt(8, 0);//shoot destroyer2
		ocean.shootAt(8, 1);
		ocean.shootAt(8, 3);//shoot destroyer3
		ocean.shootAt(8, 4);
		ocean.shootAt(4, 4);//shoot submarine1
		ocean.shootAt(4, 6);//shoot submarine2
		ocean.shootAt(6, 4);//shoot submarine3
		ocean.shootAt(6, 6);//shoot submarine4
		//this.ocean.print();
		assertTrue(ocean.isGameOver());
	}
}
