package battleship;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ShipTest {
	
	Ship battleship;
	Ship cruiser;
	Ship destroyer;
	Ship submarine;
	Ship emptysea;
	Ocean ocean;

	/**
	 * set up classes for unit testing
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		battleship = new Battleship();
		cruiser = new Cruiser();
		destroyer = new Destroyer();
		submarine = new Submarine();
		emptysea = new EmptySea();
		ocean = new Ocean();
	}

	/**
	 * check the set up for classes
	 */
	@Test
	public void testShip() {
		assertTrue(battleship instanceof Ship);
		assertTrue(battleship instanceof Battleship);
		assertTrue(cruiser instanceof Cruiser);
		assertTrue(destroyer instanceof Destroyer);
		assertTrue(submarine instanceof Submarine);
		assertTrue(emptysea instanceof EmptySea);
	}

	/**
	 * check the length of different ship types
	 */
	@Test
	public void testGetLength() {
		assertEquals(battleship.getLength(), 4);
		assertEquals(cruiser.getLength(), 3);
		assertEquals(destroyer.getLength(), 2);
		assertEquals(submarine.getLength(), 1);
		assertEquals(emptysea.getLength(), 1);
	}

	/**
	 * check the setBowRow and getBowRow methods
	 */
	@Test
	public void testGetBowRow() {
		battleship.setBowRow(2);
		assertEquals(battleship.getBowRow(), 2);
		battleship.setBowRow(6);
		assertEquals(battleship.getBowRow(), 6);
	}

	/**
	 * check the setBowColumn and getBowColumn methods
	 */
	@Test
	public void testGetBowColumn() {
		battleship.setBowColumn(2);
		assertEquals(battleship.getBowColumn(), 2);
		battleship.setBowColumn(6);
		assertEquals(battleship.getBowColumn(), 6);
	}

	/**
	 * check the setHorizontal and isHorizontal methods
	 */
	@Test
	public void testIsHorizontal() {
		battleship.setHorizontal(true);
		assertTrue(battleship.isHorizontal());
		battleship.setHorizontal(false);
		assertFalse(battleship.isHorizontal());
	}

	/**
	 * check the getShipType method
	 */
	@Test
	public void testGetShipType() {
		assertEquals(battleship.getShipType(), "battleship");
		assertEquals(cruiser.getShipType(), "cruiser");
		assertEquals(destroyer.getShipType(), "destroyer");
		assertEquals(submarine.getShipType(), "submarine");
		assertEquals(emptysea.getShipType(), "empty");
	}

	@Test
	public void testOkToPlaceShipAt() {
		//the ship can not stick out the column beyond of the ocean
		assertFalse(battleship.okToPlaceShipAt(1, 9, true, ocean));
		assertFalse(battleship.okToPlaceShipAt(1, 8, true, ocean));
		assertFalse(battleship.okToPlaceShipAt(1, 7, true, ocean));
		assertTrue(battleship.okToPlaceShipAt(1, 6, true, ocean));
		
		//the ship can not stick out the row beyond of the ocean
		assertFalse(battleship.okToPlaceShipAt(9, 1, false, ocean));
		assertFalse(battleship.okToPlaceShipAt(8, 1, false, ocean));
		assertFalse(battleship.okToPlaceShipAt(7, 1, false, ocean));
		assertTrue(battleship.okToPlaceShipAt(6, 1, false, ocean));
		
		//to test, place a ship first 
		battleship.placeShipAt(4, 2, true, ocean);
		//a ship can not overlap or touch another ship
		assertFalse(cruiser.okToPlaceShipAt(3, 1, true, ocean));
		assertFalse(cruiser.okToPlaceShipAt(5, 1, false, ocean));
		assertTrue(cruiser.okToPlaceShipAt(5, 0, false, ocean));
		assertFalse(submarine.okToPlaceShipAt(3, 6, true, ocean));
		assertTrue(submarine.okToPlaceShipAt(3, 7, true, ocean));
	}

	@Test
	public void testPlaceShipAt() {
		//after placing, the instance variables of the ship should be set
		//the ships array in ocean should have reference to that ship
		battleship.placeShipAt(2, 3, true, ocean);
		assertEquals(battleship.getBowColumn(), 3);
		assertEquals(battleship.getBowRow(), 2);
		assertTrue(battleship.isHorizontal());
		assertTrue(ocean.ships[2][3]==battleship);
		assertTrue(ocean.ships[2][4]==battleship);
		assertTrue(ocean.ships[2][5]==battleship);
		assertTrue(ocean.ships[2][6]==battleship);
		assertFalse(ocean.ships[2][7]==battleship);
		
		
		//after placing, the instance variables of the ship should be set
		//the ships array in ocean should have reference to that ship
		cruiser.placeShipAt(5, 1, false, ocean);
		assertEquals(cruiser.getBowColumn(), 1);
		assertEquals(cruiser.getBowRow(), 5);
		assertFalse(cruiser.isHorizontal());
		assertTrue(ocean.ships[5][1]==cruiser);
		assertTrue(ocean.ships[6][1]==cruiser);
		assertTrue(ocean.ships[7][1]==cruiser);
		assertFalse(ocean.ships[8][1]==cruiser);
	}

	@Test
	public void testShootAt() {
		//after shooting a ship, the certain part in hit array should be set to true
		cruiser.placeShipAt(4, 2, true, ocean);
		assertFalse(cruiser.shootAt(4, 1));
		assertTrue(cruiser.shootAt(4, 3));
		assertTrue(cruiser.shootAt(4, 3));
		assertTrue(cruiser.hit[1]==true);
		assertFalse(cruiser.hit[0]==true);
		assertTrue(cruiser.shootAt(4, 2));
		assertTrue(cruiser.shootAt(4, 4));
		//if a ship is sunk, the method should return false
		assertFalse(cruiser.shootAt(4, 3));
		
		destroyer.placeShipAt(1, 8, false, ocean);
		assertFalse(destroyer.shootAt(0, 8));
		assertTrue(destroyer.shootAt(2, 8));
		assertTrue(destroyer.shootAt(2, 8));
		assertTrue(destroyer.hit[1]==true);
		assertFalse(destroyer.hit[0]==true);
		assertTrue(destroyer.shootAt(1, 8));
		assertFalse(destroyer.shootAt(2, 8));
		
		//shootAt method of emptysea should always return false
		emptysea.placeShipAt(1, 1, true, ocean);
		assertFalse(emptysea.shootAt(1, 1));
	}

	/**
	 * after every part of a ship is hit, it should be sunk
	 */
	@Test
	public void testIsSunk() {
		//an emptysea can not sink
		emptysea.placeShipAt(1, 1, true, ocean);
		assertFalse(emptysea.isSunk());
		
		cruiser.placeShipAt(0, 0, true, ocean);
		cruiser.shootAt(0, 0);
		cruiser.shootAt(0, 1);
		cruiser.shootAt(0, 2);
		assertTrue(cruiser.isSunk());
	}

	/**
	 * toString method for emptysea is "-"
	 * "S" for hitting a ship, "x" for a ship which is sunk
	 */
	@Test
	public void testToString() {
		emptysea.placeShipAt(0, 0, true, ocean);
		assertEquals(emptysea.toString(), "-");
		
		cruiser.placeShipAt(1, 0, true, ocean);
		cruiser.shootAt(1, 0);
		assertEquals(cruiser.toString(), "S");
		cruiser.shootAt(1, 1);
		assertEquals(cruiser.toString(), "S");
		cruiser.shootAt(1, 2);
		assertEquals(cruiser.toString(), "x");
	}

}
