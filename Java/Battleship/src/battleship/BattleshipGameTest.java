package battleship;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BattleshipGameTest {

	BattleshipGame game;
	
	@Before
	public void setUp() throws Exception {
		game = new BattleshipGame();
	}

	@Test
	public void testBattleshipGame() {
		assertTrue(game instanceof BattleshipGame);
	}

	@Test
	public void testStringConvert() {
		assertEquals(BattleshipGame.StringConvert("1,2")[0], 1);
		assertEquals(BattleshipGame.StringConvert("1,2")[1], 2);
		assertEquals(BattleshipGame.StringConvert("9,0")[0], 9);
		assertEquals(BattleshipGame.StringConvert("9,0")[1], 0);
	}

	@Test
	public void testErrorChecking() {
		assertTrue(BattleshipGame.errorChecking("1,2"));
		assertFalse(BattleshipGame.errorChecking(""));
		assertFalse(BattleshipGame.errorChecking("asd"));
		assertFalse(BattleshipGame.errorChecking("a,s"));
		assertFalse(BattleshipGame.errorChecking(" ,1"));
		assertFalse(BattleshipGame.errorChecking("12,1"));
	}

}
