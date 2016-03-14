package library;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CalendarTest {

	private Calendar calendarTest;
	
	@Before
	public void setUp() throws Exception {
		calendarTest = new Calendar();
	}


	@Test
	public void testGetDate() {
		assertEquals(calendarTest.getDate(), 0);
	}

	@Test
	public void testAdvance() {
		assertEquals(calendarTest.getDate(), 0);
		calendarTest.advance();
		assertEquals(calendarTest.getDate(), 1);
		calendarTest.advance();
		assertEquals(calendarTest.getDate(), 2);
	}

}
