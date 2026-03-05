package tests.filters;

import filters.singlePrimer.FilterCGContent;
import sequences.dna.Primer;
import junit.framework.TestCase;

public class FilterCGContentTest extends TestCase {
		private FilterCGContent gc;
		private Primer[] primers = new Primer[11];
	
	protected void setUp() throws Exception {
		
		primers[0] = new Primer("ATATATATAT", "description", 1, 10, true);
		primers[1] = new Primer("CTATATATAT", "description", 1, 10, true);
		primers[2] = new Primer("CGATATATAT", "description", 1, 10, true);
		primers[3]= new Primer("CGCTATATAT", "description", 1, 10, true);
		primers[4]= new Primer("CGCGATATAT", "description", 1, 10, true);
		primers[5]= new Primer("CGCGCTATAT", "description", 1, 10, true);
		primers[6]= new Primer("CGCGCGATAT", "description", 1, 10, true);
		primers[7]= new Primer("CGCGCGCTAT", "description", 1, 10, true);
		primers[8]= new Primer("CGCGCGCGAT", "description", 1, 10, true);
		primers[9]= new Primer("CGCGCGCGCT", "description", 1, 10, true);
		primers[10]= new Primer("CGCGCGCGCG", "description", 1, 10, true);		
		super.setUp();
		
		gc = new FilterCGContent(50,60);
		
	}

	public void testValidate() {
		assertFalse(gc.filter(primers[0]));
		assertFalse(gc.filter(primers[1]));
		assertFalse(gc.filter(primers[2]));
		assertFalse(gc.filter(primers[3]));
		assertFalse(gc.filter(primers[4]));
		assertTrue(gc.filter(primers[5]));
		assertTrue(gc.filter(primers[6]));
		assertFalse(gc.filter(primers[7]));
		assertFalse(gc.filter(primers[8]));
		assertFalse(gc.filter(primers[9]));
		assertFalse(gc.filter(primers[10]));		
	}

}
