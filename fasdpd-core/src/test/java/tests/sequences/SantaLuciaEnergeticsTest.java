package tests.sequences;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import sequences.dna.Primer;
import sequences.util.tmcalculator.SantaluciaTmEstimator;

public class SantaLuciaEnergeticsTest {

	@BeforeEach

	protected void setUp() throws Exception {
	}

	@Test

	
	public void testCalculatePolyAPrimer() {
		SantaluciaTmEstimator tme = new SantaluciaTmEstimator();
		Primer primer = new Primer("AAAAAAAAAAAAAAAAAAAA", "polyA", 0, 0, true);
		tme.calculateTM(primer);
		var tm = tme.mean();
		assertEquals(tm, 37.78, 0.05);
	}

	@Test

	
	public void testCalculatePolyGPrimer() {
		SantaluciaTmEstimator tme = new SantaluciaTmEstimator();
		Primer primer = new Primer("GGGGGGGGGGGGGGGGGGGG", "polyA", 0, 0, true);
		tme.calculateTM(primer);
		var tm = tme.mean();
		assertEquals(tm, 73.39, 0.05);
	}
}
