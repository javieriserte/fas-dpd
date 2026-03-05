package tests.filters;

import sequences.dna.Primer;
import sequences.util.compare.DegeneratedDNAMatchingStrategy;
import filters.singlePrimer.FilterHomoDimer;
import filters.singlePrimer.FilterSinglePrimer;
import junit.framework.TestCase;

public class FilterHomoDimerTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	
	public void testValidate() {
		
		Primer p1a = new Primer("ACGT", "desc", 1, 20, true);
		Primer p2a = new Primer("ACTAG", "desc", 1, 20, true);
		Primer p3a = new Primer("CCTA", "desc", 1, 20, false);
		Primer p4a = new Primer("ACTG", "desc", 1, 20, false);
		Primer p5a = new Primer("ACTGT", "desc", 1, 20, false);

		FilterSinglePrimer filter2 = new FilterHomoDimer(2,new DegeneratedDNAMatchingStrategy());
		FilterSinglePrimer filter3 = new FilterHomoDimer(3,new DegeneratedDNAMatchingStrategy());
		FilterSinglePrimer filter4 = new FilterHomoDimer(4,new DegeneratedDNAMatchingStrategy());
		
		assertFalse(filter2.filter(p1a));
		assertFalse(filter2.filter(p2a));
		assertTrue(filter2.filter(p3a));
		assertTrue(filter2.filter(p4a));
		assertTrue(filter2.filter(p5a));
		
		assertFalse(filter3.filter(p1a));
		assertFalse(filter3.filter(p2a));
		assertTrue(filter3.filter(p3a));
		assertTrue(filter3.filter(p4a));
		assertTrue(filter2.filter(p5a));

		assertTrue(filter4.filter(p1a));
		assertTrue(filter4.filter(p2a));
		assertTrue(filter4.filter(p3a));
		assertTrue(filter4.filter(p4a));
		assertTrue(filter2.filter(p5a));
	}
	
}
