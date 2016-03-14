package library;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class OverdueNoticeTest {
	
	private OverdueNotice TestNotice;
	private Patron paula;
    private Book book1;
    private Book book2;
    private Book book3;

	@Before
	public void setUp() throws Exception {
		paula = new Patron("Paula", null);
        book1 = new Book("Disappearing Nightly", "Laura Resnick");
        book2 = new Book("Inferno", "Dan Brown");
        book3 = new Book("The Da Vinci Code", "Dan Brown");
        book1.checkOut(14);
    	book2.checkOut(13);
    	book3.checkOut(12);
    	paula.take(book1);
    	paula.take(book2);
    	paula.take(book3);
        TestNotice = new OverdueNotice(paula, 20);
	}


	@Test
	public void testToString() {
	assertEquals(TestNotice.toString(), "Disappearing Nightly, by Laura Resnick is going to be due at 21; Inferno, by Dan Brown is going to be due at 20; The Da Vinci Code, by Dan Brown ***This book is overdue!; ");
	}

}
