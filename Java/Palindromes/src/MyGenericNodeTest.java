import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * unit test class for MyGenericNode class
 * @author weixi ma
 *
 */
public class MyGenericNodeTest {
	
	MyGenericNode<String> test1;
	MyGenericNode<Integer> test2;

	/**
	 * set up two test classes
	 * with String and Integer as their types, respectively
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		test1 = new MyGenericNode<String>("hello");
		test2 = new MyGenericNode<Integer>(2);
	}

	/**
	 * test for test classes are MyGenericNode class
	 */
	@Test
	public void testClass() {
		assertTrue(test1 instanceof MyGenericNode<?>);
		assertTrue(test2 instanceof MyGenericNode<?>);
	}

	/**
	 * test for the contents of each node class
	 */
	@Test
	public void testContents(){
		assertEquals(test1.value, "hello");
		assertEquals(test1.next, null);
		assertTrue(test2.value.equals(2));
		assertEquals(test2.next, null);
	}
}
