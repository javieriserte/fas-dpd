package tests.filters;

import sequences.dna.Primer;
import filters.singlePrimer.FilterBaseRuns;
import filters.singlePrimer.FilterSinglePrimer;
import junit.framework.TestCase;

public class FilterBaseRunsTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}
	
	public void testValidate() {
		
		Primer p1 = new Primer("ACTG", "desc", 1, 20, true);
		Primer p2 = new Primer("ACCT", "desc", 1, 20, true);
		Primer p3 = new Primer("ACCC", "desc", 1, 20, false);
		Primer p4 = new Primer("CCCC", "desc", 1, 20, false);
		Primer p5 = new Primer("CCCCC", "desc", 1, 20, false);
		
		FilterSinglePrimer filter = new FilterBaseRuns(2);
		FilterSinglePrimer filter1 = new FilterBaseRuns(3);
		FilterSinglePrimer filter2 = new FilterBaseRuns(4);
		
		assertTrue(filter.filter(p1));
		assertTrue(filter.filter(p2));
		assertFalse(filter.filter(p3));
		assertFalse(filter.filter(p4));
		assertFalse(filter.filter(p5));
		
		assertTrue(filter1.filter(p1));
		assertTrue(filter1.filter(p2));
		assertTrue(filter1.filter(p3));
		assertFalse(filter1.filter(p4));
		assertFalse(filter1.filter(p5));

		assertTrue(filter2.filter(p1));
		assertTrue(filter2.filter(p2));
		assertTrue(filter2.filter(p3));
		assertTrue(filter2.filter(p4));
		assertFalse(filter2.filter(p5));

	}

}
