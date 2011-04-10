package tests.filters;

import filters.primerpair.FilterOverlapping;
import filters.primerpair.FilterPrimerPair;
import sequences.dna.Primer;
import junit.framework.TestCase;

public class FilterOverlappingTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}
	
	public void testValidate() {
		
		Primer p1a = new Primer("ACTGCTACGTCGACTACGT", "desc", 5, 10, true);
		Primer p1b = new Primer("ACTGCTACGTCGACTACGT", "desc", 25, 15, false);
		
		Primer p2a = new Primer("ACTGCTACGTCGACTACGT", "desc", 5, 15, true);
		Primer p2b = new Primer("ACTGCTACGTCGACTACGT", "desc", 20, 10, true);
		
		Primer p3a = new Primer("ACTGCTACGTCGACTACGT", "desc", 10, 20, true);
		Primer p3b = new Primer("ACTGCTACGTCGACTACGT", "desc", 15, 5, true);

		Primer p4a = new Primer("ACTGCTACGTCGACTACGT", "desc", 15, 20, true);
		Primer p4b = new Primer("ACTGCTACGTCGACTACGT", "desc", 10, 5, true);
		
		FilterPrimerPair filter = new FilterOverlapping();
		
		assertTrue(filter.filter(p1a, p1b));
		assertTrue(filter.filter(p1b, p1a));

		assertFalse(filter.filter(p2a, p2b));
		assertFalse(filter.filter(p2b, p2a));

		assertFalse(filter.filter(p3a, p3b));
		assertFalse(filter.filter(p3b, p3a));

		assertTrue(filter.filter(p4a, p4b));
		assertTrue(filter.filter(p4b, p4a));

		
	}

}
