package tests.filters;

import filters.primerpair.FilterMinimumAmpliconSize;
import sequences.dna.Primer;
import junit.framework.TestCase;

public class FilterMinimumAmpliconSizeTest extends TestCase {

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
		FilterMinimumAmpliconSize filter1 = new FilterMinimumAmpliconSize(100);
		FilterMinimumAmpliconSize filter2 = new FilterMinimumAmpliconSize(101);
		FilterMinimumAmpliconSize filter3 = new FilterMinimumAmpliconSize(99);
		assertTrue(filter1.filter(p1a, p1b));
		assertFalse(filter2.filter(p1a, p1b));
		assertTrue(filter3.filter(p1a, p1b));
		assertFalse(filter1.filter(p2a, p2b));
		assertFalse(filter1.filter(p3a, p3b));
		assertFalse(filter1.filter(p4a, p4b));
	}
}
