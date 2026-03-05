package tests.filters;

import filters.singlePrimer.FilterRepeatedEnd;
import filters.singlePrimer.FilterSinglePrimer;
import sequences.dna.Primer;
import junit.framework.TestCase;

public class FilterRepeatedEndTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}
	
	public void testValidate() {
	
		Primer[] primers = new Primer[10];
		
		primers[0] = new Primer("ATATATATAA", "description", 1, 10, true);
		primers[1] = new Primer("CTATATATTT", "description", 1, 10, true);
		primers[2] = new Primer("CGATATATAT", "description", 1, 10, true);
		primers[3] = new Primer("CGCTATATTA", "description", 1, 10, true);
		primers[4] = new Primer("CGCGATATCC", "description", 1, 10, true);
		primers[5] = new Primer("CGCGCTATGC", "description", 1, 10, true);
		primers[6] = new Primer("CGCGCGATCG", "description", 1, 10, true);
		primers[7] = new Primer("CGCGCGCTGG", "description", 1, 10, true);
		
		FilterSinglePrimer filter = new FilterRepeatedEnd();
		
		
		assertFalse(filter.filter(primers[0]));
		assertFalse(filter.filter(primers[1]));
		assertFalse(filter.filter(primers[4]));
		assertFalse(filter.filter(primers[7]));
		
		assertTrue(filter.filter(primers[2]));
		assertTrue(filter.filter(primers[3]));
		assertTrue(filter.filter(primers[5]));
		assertTrue(filter.filter(primers[6]));
		
		
		
	}
	

}
