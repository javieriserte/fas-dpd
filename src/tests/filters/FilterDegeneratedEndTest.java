package tests.filters;

import sequences.dna.Primer;
import filters.singlePrimer.FilterDegeneratedEnd;
import filters.singlePrimer.FilterSinglePrimer;
import junit.framework.TestCase;

public class FilterDegeneratedEndTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}
	
	public void testValidate() {

		String sPass = "ACTG";
		
		String sFail = "WRYSDHKVBMN";

		FilterSinglePrimer filter = new FilterDegeneratedEnd();

		
		for (char c : sPass.toCharArray()) {
			Primer p1a = new Primer("A".concat(String.valueOf(c)), "desc", 1, 20, true);
			assertTrue(filter.filter(p1a));
		}

		for (char c : sFail.toCharArray()) {
			Primer p1a = new Primer("A".concat(String.valueOf(c)), "desc", 1, 20, true);
			assertFalse(filter.filter(p1a));
		}

		
	}

}
