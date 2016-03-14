import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * unit test class for MyGenericLinkedList class
 * @author weixi ma
 *
 */
public class MyGenericLinkedListTest {
	
	MyGenericLinkedList<Integer> linkedList;

	/**
	 * set up a test class with Integer as its type
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		linkedList = new MyGenericLinkedList<Integer>();
	}

	/**
	 * test for test class is MyGenericLinkedList class
	 */
	@Test
	public void testMyGenericLinkedList() {
		assertTrue(linkedList instanceof MyGenericLinkedList<?>);
	}

	/**
	 * test for the addFirst method, by checking the list's size
	 */
	@Test
	public void testAddFirst() {
		assertEquals(linkedList.getSize(), 0);
		linkedList.addFirst(1);
		assertEquals(linkedList.getSize(), 1);
		linkedList.addFirst(2);
		assertEquals(linkedList.getSize(), 2);
	}

	/**
	 * test for the addLast method, by checking the list's size
	 */
	@Test
	public void testAddLast() {
		assertEquals(linkedList.getSize(), 0);
		linkedList.addLast(2);
		assertEquals(linkedList.getSize(), 1);
		linkedList.addLast(3);
		assertEquals(linkedList.getSize(), 2);
		linkedList.addLast(4);
		assertEquals(linkedList.getSize(), 3);
	}
	
	/**
	 * test for the deleteFirst method
	 * by checking the size and the return values
	 */
	@Test
	public void testDeleteFirst() {
		linkedList.addFirst(3);
		assertEquals(linkedList.getSize(), 1);
		assertTrue(linkedList.deleteFirst().equals(3));
		assertEquals(linkedList.getSize(), 0);
		assertNull(linkedList.deleteFirst());
		assertEquals(linkedList.getSize(), 0);
	}

	/**
	 * test for the getSize method
	 */
	@Test
	public void testGetSize() {
		assertEquals(linkedList.getSize(), 0);
		linkedList.addFirst(1);
		linkedList.addFirst(2);
		linkedList.addFirst(3);
		assertEquals(linkedList.getSize(), 3);
		linkedList.deleteFirst();
		linkedList.deleteFirst();
		assertEquals(linkedList.getSize(), 1);
		linkedList.deleteFirst();
		assertEquals(linkedList.getSize(), 0);
	}

}
