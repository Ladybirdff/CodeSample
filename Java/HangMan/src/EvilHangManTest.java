import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

/**
 * a unit test class to test the critical method of EvilHangMan class
 * @author Weixi Ma
 *
 */
public class EvilHangManTest {

	EvilHangMan test;
	ArrayList<String> words;

	/**
	 * set up the test class
	 * rather than parsing a real text file, use an ArrayList as the original dictionary
	 * [ah, ally, cool, good, else, hope, double]
	 * force the guessed length, number of guesses and see number of remaining words
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		test = new EvilHangMan();
		words = new ArrayList<String>();	
		words.add("ah");
		words.add("ally");
		words.add("cool");
		words.add("good");
		words.add("else");
		words.add("hope");
		words.add("double");
		test.initalizeDictionary(words);
		test.gameInitialize(4, 8, true);
	}

	/**
	 * test the dictionary instance variable is initialized according to the input ArrayList
	 */
	@Test
	public void testInitalizeDictionary() {
		// check key set
		Set<Integer> keys = new HashSet<Integer>();
		keys.add(2);
		keys.add(4);
		keys.add(6);
		assertEquals(test.getDictionary().keySet(), keys);
		
		// check for each key's value
		assertEquals(test.getDictionary().get(2).size(), 1);
		assertEquals(test.getDictionary().get(2).get(0), "ah");
		
		assertEquals(test.getDictionary().get(4).size(), 5);
		assertEquals(test.getDictionary().get(4).get(0), "ally");
		assertEquals(test.getDictionary().get(4).get(1), "cool");
		assertEquals(test.getDictionary().get(4).get(2), "good");
		assertEquals(test.getDictionary().get(4).get(3), "else");
		assertEquals(test.getDictionary().get(4).get(4), "hope");
		
		assertEquals(test.getDictionary().get(6).size(), 1);
		assertEquals(test.getDictionary().get(6).get(0), "double");
	}

	/**
	 * test instance variables are initialized according to the input 
	 */
	@Test
	public void testGameInitialize() {
		assertEquals(test.getFixedLen(), 4);
		assertEquals(test.getRevealedLetters(), "----");
		assertEquals(test.getTotalGuesses(), 8);
		assertTrue(test.getLookRemainWords());
		
		// wordList holding all the words at first
		assertEquals(test.getWordsList().size(), 5);
		assertEquals(test.getWordsList().get(0), "ally");
		assertEquals(test.getWordsList().get(1), "cool");
		assertEquals(test.getWordsList().get(2), "good");
		assertEquals(test.getWordsList().get(3), "else");
		assertEquals(test.getWordsList().get(4), "hope");
	}

	/**
	 * check for the word list update correctly according to the guessed letter
	 */
	@Test
	public void testChangeWordFamilies() {
		// guess "e" first
		test.changeWordFamilies("e");
		assertEquals(test.getRevealedLetters(), "----");
		assertEquals(test.getLettersGuessed().size(), 1);
		assertEquals(test.getLettersGuessed().get(0), "e");
		assertEquals(test.getWordsList().size(), 3);
		assertEquals(test.getWordsList().get(0), "ally");
		assertEquals(test.getWordsList().get(1), "cool");
		assertEquals(test.getWordsList().get(2), "good");
		
		// then guess "o"
		test.changeWordFamilies("o");
		assertEquals(test.getRevealedLetters(), "-oo-");
		assertEquals(test.getLettersGuessed().size(), 2);
		assertEquals(test.getLettersGuessed().get(0), "e");
		assertEquals(test.getLettersGuessed().get(1), "o");
		assertEquals(test.getWordsList().size(), 2);
		assertEquals(test.getWordsList().get(0), "cool");
		assertEquals(test.getWordsList().get(1), "good");
	}

}
