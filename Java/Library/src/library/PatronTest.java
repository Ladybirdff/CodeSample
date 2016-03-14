package library;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PatronTest {
	
	private Patron dave;
    private Patron paula;
    private Book book1;
    private Book book2;
    private Book book3;
    private Book book4;

	@Before
	public void setUp() throws Exception {
		dave = new Patron("Dave", null);
        paula = new Patron("Paula", null);
        book1 = new Book("Disappearing Nightly", "Laura Resnick");
        book2 = new Book("Inferno", "Dan Brown");
        book3 = new Book("The Da Vinci Code", "Dan Brown");
        book4 = new Book("Pride and Prejudice", "Jane Austen");
	}

	@Test
	public void testPatron() {
		Patron paula = new Patron("Paula", null);
        assertTrue(paula instanceof Patron);
	}

	@Test
	public void testGetName() {
		assertEquals("Dave", dave.getName());
        assertEquals("Paula", paula.getName());
	}

	@Test
	public void testTake() {
		paula.take(book1);
        assertTrue(paula.getBooks().contains(book1));
        assertFalse(dave.getBooks().contains(book1));
        
        paula.take(book2);
        paula.take(book3);
        paula.take(book4);
        assertTrue(paula.getBooks().contains(book2));
        assertTrue(paula.getBooks().contains(book3));
        assertFalse(paula.getBooks().contains(book4));
	}

	@Test
	public void testGiveBack() {
		paula.take(book1);
        assertTrue(paula.getBooks().contains(book1));
        paula.giveBack(book1);
        assertFalse(paula.getBooks().contains(book1));
	}

	@Test
	public void testGetBooks() {
		dave.take(book1);
        assertTrue(dave.getBooks().contains(book1));
        assertEquals(1, dave.getBooks().size());
	}

	@Test
	public void testToString() {
		assertEquals("Dave", dave.toString());
        assertEquals("Paula", paula.toString());
	}

}
