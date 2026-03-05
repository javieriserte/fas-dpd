package tests.filters;

import filters.primerpair.FilterAmpliconSize;
import sequences.dna.Primer;
import junit.framework.TestCase;

public class FilterAmpliconSizeTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}
	
	public void testValidate() {
	
		Primer p1a = new Primer("ACTGCTACGTCGACTACGT", "desc", 1, 20, true);
		Primer p1b = new Primer("ACTGCTACGTCGACTACGT", "desc", 100, 80, false);
		
		Primer p2a = new Primer("ACTGCTACGTCGACTACGT", "desc", 1, 20, true);
		Primer p2b = new Primer("ACTGCTACGTCGACTACGT", "desc", 80, 100, true);

		Primer p3a = new Primer("ACTGCTACGTCGACTACGT", "desc", 20, 1, false);
		Primer p3b = new Primer("ACTGCTACGTCGACTACGT", "desc", 80, 100, true);
		
		Primer p4a = new Primer("ACTGCTACGTCGACTACGT", "desc", 20, 1, false);
		Primer p4b = new Primer("ACTGCTACGTCGACTACGT", "desc", 100, 80, false);
		
		FilterAmpliconSize filter = new FilterAmpliconSize(100);
		FilterAmpliconSize filter1 = new FilterAmpliconSize(101);
		FilterAmpliconSize filter2 = new FilterAmpliconSize(99);
		
		assertTrue(filter.filter(p1a, p1b));
		
		assertTrue(filter1.filter(p1a, p1b));
		assertFalse(filter2.filter(p1a, p1b));
		assertFalse(filter.filter(p2a, p2b));
		assertFalse(filter.filter(p3a, p3b));
		assertFalse(filter.filter(p4a, p4b));
		

		
		
	}
	

}
