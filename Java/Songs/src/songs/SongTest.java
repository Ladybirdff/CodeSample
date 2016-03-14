/**
 * Hw 12
 * Authors: Kexin Huang & Weixi Ma
 */
package songs;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SongTest {
	private Song birthday;
	private Song throne;
	@Before
	public void setUp() throws Exception {
		birthday = new Song("birthday.txt");
		throne = new Song("GameOfThronesTheme.txt");
	}

	/**
	 * test creation, getTitle(), getTotalDuration() and getArtist()
	 */
	@Test
	public void testSong() {
		assertTrue(birthday.getNotesArray()[1] instanceof Note);
		assertTrue(birthday.getNotesArray()[5] instanceof Note);
		assertTrue(birthday.getNotesArray()[10] instanceof Note);
		assertTrue(birthday.getNotesArray()[24] instanceof Note);
		assertEquals(birthday.getTitle(),"something");
		assertEquals(birthday.getArtist(),"someone");
		assertEquals(birthday.getTotalDuration(), 13 , 0);
		assertEquals(birthday.getNotesArray().length, 25);
		assertEquals(throne.getTitle(),"Game of Thrones");
		assertEquals(throne.getArtist(),"Ramin Djawadi");
		assertEquals(throne.getTotalDuration(), 25.20 , 0.001);
		assertEquals(throne.getNotesArray().length, 41);
	}


	@Test
	public void testCheckOctave(){
		assertTrue(birthday.checkOctave(true));
		assertTrue(birthday.checkOctave(false));
		assertTrue(throne.checkOctave(true));
		assertTrue(throne.checkOctave(false));
	}
	
	@Test
	public void testOctaveDown() {
		assertTrue(birthday.octaveDown());
		assertTrue(throne.octaveDown());
		//Set only one octave to be zero, then check the function, it should return false;
		birthday.getNotesArray()[0].setOctave(1);
		assertFalse(birthday.octaveDown());
		//Set all octaves in birthday to 1, then check the function, it should return false;
		for (Note i : birthday.getNotesArray()){
			i.setOctave(1);
		}
		assertFalse(birthday.octaveDown());

	}

	@Test
	public void testOctaveUp() {
		assertTrue(birthday.octaveUp());
		assertTrue(throne.octaveUp());
		//Set only one octave to be 10, then check the function, it should return false;
		birthday.getNotesArray()[0].setOctave(10);
		assertFalse(birthday.octaveUp());
		//Set all octaves in birthday to 10, then check the function, it should return false;
		for (Note i : birthday.getNotesArray()){
			i.setOctave(10);
		}
		assertFalse(birthday.octaveUp());
	}

	@Test
	public void testChangeTempo() {
		assertEquals(birthday.getNotesArray()[0].getDuration(), 0.25, 0);
		assertEquals(birthday.getNotesArray()[1].getDuration(), 0.25, 0);
		assertEquals(birthday.getNotesArray()[2].getDuration(), 0.5, 0);
		birthday.changeTempo(4);
		assertEquals(birthday.getNotesArray()[0].getDuration(), 1, 0);
		assertEquals(birthday.getNotesArray()[1].getDuration(), 1, 0);
		assertEquals(birthday.getNotesArray()[2].getDuration(), 2, 0);
	}

	@Test
	public void testReverse() {
		Note note0 = birthday.getNotesArray()[0];
		Note note1 = birthday.getNotesArray()[1];
		Note note2 = birthday.getNotesArray()[2];
		birthday.reverse();
		assertEquals(note0, birthday.getNotesArray()[24]);
		assertEquals(note1, birthday.getNotesArray()[23]);
		assertEquals(note2, birthday.getNotesArray()[22]);


	}

	@Test
	public void testToString() {
		assertTrue(birthday.toString().contains("someone"));
		assertTrue(birthday.toString().contains("something"));
		assertTrue(birthday.toString().contains("3, 0.5 D 4 NATURAL false"));
		assertTrue(throne.toString().contains("Game of Thrones"));
		assertTrue(throne.toString().contains("Ramin Djawadi"));
		assertTrue(throne.toString().contains("duration"));
		assertTrue(throne.toString().contains("5, 0.4 C 6 NATURAL false"));


	}

}
