package tests.filters;

import sequences.dna.Primer;
import sequences.util.tmcalculator.TmEstimator;
import filters.primerpair.FilterMeltingTempCompatibility;
import filters.primerpair.FilterPrimerPair;
import junit.framework.TestCase;

public class FilterMeltingTempCompatibilityTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testValidate() {
		
		Primer p1 = new Primer("AA", "", 1, 10, true);
		Primer p2 = new Primer("AAA", "", 1, 10, true);
		Primer p3 = new Primer("AAAA", "", 1, 10, true);
		Primer p4 = new Primer("AAAAA", "", 1, 10, true);
		
	TmEstimator dummie = new TmEstimator() {
		float v;
		@Override public double  min()  { return v; }
		@Override public double  mean() {	return v; }
		@Override public double  max()  { return v; }
		@Override public void calculateTM(Primer primer) { int l = primer.getLength();  v = l *5; }
	};
	
	
	FilterPrimerPair filter = new FilterMeltingTempCompatibility(10, dummie);
	
	assertTrue(filter.filter(p1, p2));
	assertTrue(filter.filter(p1, p3));
	assertFalse(filter.filter(p1, p4));

	assertTrue(filter.filter(p2, p3));
	assertTrue(filter.filter(p2, p4));
	
	assertTrue(filter.filter(p3, p4));
	}
}
