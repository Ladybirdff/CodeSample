import static org.junit.Assert.*;

import java.util.EmptyStackException;

import org.junit.Before;
import org.junit.Test;

/**
 * unit test class for the stack class
 * @author weixi ma
 *
 */
public class MyStackTest {

	MyStack<Character> stack; 
	
	/**
	 * set up a test class with Character as its type
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		stack = new MyStack<Character>();
	}

	/**
	 * whether test class is a stack class
	 */
	@Test
	public void testMyStack() {
		assertTrue(stack instanceof MyStack<?>);
	}

	/**
	 * test for the push method by checking the size of the stack
	 */
	@Test
	public void testPush() {
		assertEquals(stack.size(), 0);
		stack.push('a');
		assertEquals(stack.size(), 1);
		stack.push('b');
		assertEquals(stack.size(), 2);
	}

	/**
	 * test for the pop method by check the size of the stack
	 * and return values
	 */
	@Test
	public void testPop() {
		stack.push('a');
		stack.push('b');
		assertEquals(stack.size(), 2);
		assertTrue(stack.pop().equals('b'));
		assertEquals(stack.size(), 1);
		assertTrue(stack.pop().equals('a'));
		assertEquals(stack.size(), 0);
	}
	
	/**
	 * test for pop method to throw exception if the stack is empty
	 */
	@Test (expected = EmptyStackException.class)
	public void testException(){
		stack.pop();
	}

	/**
	 * test the size method of the stack class
	 */
	@Test
	public void testSize() {
		assertEquals(stack.size(), 0);
		stack.push('a');
		assertEquals(stack.size(), 1);
		stack.pop();
		assertEquals(stack.size(), 0);
	}

	/**
	 * test the isEmpty method
	 */
	@Test
	public void testIsEmpty() {
		assertTrue(stack.isEmpty());
		stack.push('a');
		assertFalse(stack.isEmpty());
		stack.pop();
		assertTrue(stack.isEmpty());
	}

}
