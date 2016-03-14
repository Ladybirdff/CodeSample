package library;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BookTest {
	
	private Book bookTest;

	@Before
	public void setUp() throws Exception {
		bookTest = new Book("LOL", "Sam");
	}

	@Test
	public void testGetTitle() {
		assertEquals(bookTest.getTitle(), "LOL");
	}

	@Test
	public void testGetAuthor() {
		assertEquals(bookTest.getAuthor(), "Sam");
	}

	@Test
	public void testGetDuedate() {
		assertEquals(bookTest.getDuedate(), -1);
	}

	@Test
	public void testCheckOut() {
		assertEquals(bookTest.getDuedate(), -1);
		bookTest.checkOut(7);
		assertEquals(bookTest.getDuedate(), 0);
	}

	@Test
	public void testCheckIn() {
		assertEquals(bookTest.getDuedate(), -1);
		bookTest.checkOut(7);
		assertEquals(bookTest.getDuedate(), 0);
		bookTest.checkIn();
		assertEquals(bookTest.getDuedate(), -1);
	}

	@Test
	public void testToString() {
		assertEquals(bookTest.toString(), "LOL, by Sam");
		bookTest.checkOut(3);
		assertEquals(bookTest.toString(), "LOL, by Sam");
	}
	
	@Test
	public void testEquals(){
		Book book1 = new Book("LOL", "Sam");
		Book book2 = new Book("ABC", "Sam");
		Book book3 = new Book("LOL", "asdf");
		assertTrue(bookTest.equals(book1));
		assertFalse(bookTest.equals(book2));
		assertFalse(bookTest.equals(book3));
	}

}
