import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * unit test class for the PalindromeChecker class
 * @author weixi ma
 *
 */
public class PalindromeCheckerTest {
	
	PalindromeChecker test1;
	PalindromeChecker test2;
	PalindromeChecker test3;
	PalindromeChecker test4;

	/**
	 * set up test class
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		test1 = new PalindromeChecker("");
		test2 = new PalindromeChecker(null);
		test3 = new PalindromeChecker("A man, A plan, A canal, Panama");
		test4 = new PalindromeChecker(" &%*a,.sd/$^&");
	}
	
	/**
	 * check for all the test classes are PalindromeChecker class
	 */
	@Test
	public void testPalindromeChecker() {
		assertTrue(test1 instanceof PalindromeChecker);
		assertTrue(test2 instanceof PalindromeChecker);
		assertTrue(test3 instanceof PalindromeChecker);
		assertTrue(test4 instanceof PalindromeChecker);
	}

	/**
	 * test for the check method
	 */
	@Test
	public void testCheck() {
		assertTrue(test1.check());
		assertTrue(test2.check());
		assertTrue(test3.check());
		assertFalse(test4.check());
	}

}
