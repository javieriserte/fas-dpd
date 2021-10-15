
package tests.filters;

import sequences.dna.Primer;
import sequences.util.compare.DegeneratedDNAMatchingStrategy;
import filters.primerpair.FilterHeteroDimerFixed3;
import filters.primerpair.FilterPrimerPair;
import junit.framework.TestCase;

public class FilterHeteroDimerFixed3Test extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}
	
	public void testValidate() {
		
		Primer p1a = new Primer("ACTG", "desc", 1, 20, true);
		Primer p1b = new Primer("CAGT", "desc", 100, 80, false);
		Primer p2a = new Primer("ACTG", "desc", 1, 20, true);
		Primer p2b = new Primer("TCAG", "desc", 100, 80, true);
		Primer p3a = new Primer("ACTG", "desc", 1, 20, false);
		Primer p3b = new Primer("GGCA", "desc", 100, 80, true);
		Primer p4a = new Primer("ACTG", "desc", 1, 20, false);
		Primer p4b = new Primer("AGTG", "desc", 100, 80, false);
		
		FilterPrimerPair filter2 = new FilterHeteroDimerFixed3(2,new DegeneratedDNAMatchingStrategy());
		FilterPrimerPair filter3 = new FilterHeteroDimerFixed3(3,new DegeneratedDNAMatchingStrategy());
		FilterPrimerPair filter4 = new FilterHeteroDimerFixed3(4,new DegeneratedDNAMatchingStrategy());
		
		assertFalse(filter2.filter(p1a, p1b));
		assertFalse(filter3.filter(p1a, p1b));
		assertTrue(filter4.filter(p1a, p1b));

		assertFalse(filter2.filter(p2a, p2b));
		assertTrue(filter3.filter(p2a, p2b));
		assertTrue(filter4.filter(p2a, p2b));

		assertTrue(filter2.filter(p3a, p3b));
		assertTrue(filter3.filter(p3a, p3b));
		assertTrue(filter4.filter(p3a, p3b));

		assertTrue(filter2.filter(p4a, p4b));
		assertTrue(filter3.filter(p4a, p4b));
		assertTrue(filter4.filter(p4a, p4b));
	}
	

}
