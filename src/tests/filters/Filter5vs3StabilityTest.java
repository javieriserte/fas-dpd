package tests.filters;

import sequences.dna.Primer;
import filters.singlePrimer.Filter5vs3Stability;
import junit.framework.TestCase;

public class Filter5vs3StabilityTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}
	
	public void testFilter() {
		Filter5vs3Stability a = Filter5vs3Stability.getStandard5vs3StabilityFilter();

		Primer p1 = new Primer("CGCGCATATA", "", 1, 10, true);
		Primer p2 = new Primer("CGCGCCTGCC", "", 1, 10, true);
		Primer p3 = new Primer("CGCGCCGGGT", "", 1, 10, true);
		Primer p4 = new Primer("CGCGCATCCG", "", 1, 10, true);
		
		Primer p5 = new Primer("ATATACGCGC", "", 1, 10, true);
		Primer p6 = new Primer("CTGCCCGCGC", "", 1, 10, true);
		Primer p7 = new Primer("GACGACGCGC", "", 1, 10, true);
		Primer p8 = new Primer("TTCCTCGCGC", "", 1, 10, true);
		
		assertTrue(a.filter(p1));
		assertTrue(a.filter(p2));
		assertTrue(a.filter(p3));
		assertTrue(a.filter(p4));
		
		assertFalse(a.filter(p5));
		assertFalse(a.filter(p6));
		assertFalse(a.filter(p7));
		assertFalse(a.filter(p8));
		
		
	}
	
	

}
