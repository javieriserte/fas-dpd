package tests.sequences;

import junit.framework.TestCase;
import sequences.dna.Primer;
import sequences.util.tmcalculator.SantaluciaTmEstimator;

public class SantaLuciaEnergeticsTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testCalculatePolyAPrimer() {
		SantaluciaTmEstimator tme = new SantaluciaTmEstimator();
		Primer primer = new Primer("AAAAAAAAAAAAAAAAAAAA", "polyA", 0, 0, true);
		tme.calculateTM(primer);
		var tm = tme.mean();
		assertEquals(tm, 37.78, 0.05);
	}

	public void testCalculatePolyGPrimer() {
		SantaluciaTmEstimator tme = new SantaluciaTmEstimator();
		Primer primer = new Primer("GGGGGGGGGGGGGGGGGGGG", "polyA", 0, 0, true);
		tme.calculateTM(primer);
		var tm = tme.mean();
		assertEquals(tm, 73.39, 0.05);
	}
}
