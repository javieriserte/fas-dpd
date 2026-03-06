
package tests.filters;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import sequences.dna.Primer;
import sequences.util.compare.DegeneratedDNAMatchingStrategy;
import filters.singlePrimer.FilterHomoDimerFixed3;
import filters.singlePrimer.FilterSinglePrimer;

public class FilterHomoDimerFixed3Test {

	@BeforeEach

	protected void setUp() throws Exception {
	}

	@Test

	
	public void testValidate() {
		
		Primer p1a = new Primer("ACGT", "desc", 1, 20, true);
		Primer p2a = new Primer("ACTAG", "desc", 1, 20, true);
		Primer p3a = new Primer("CCTA", "desc", 1, 20, false);
		Primer p4a = new Primer("ACTG", "desc", 1, 20, false);
		Primer p5a = new Primer("ACTGT", "desc", 1, 20, false);
		Primer p6a = new Primer("ACCGG", "desc", 1, 20, false);
		Primer p7a = new Primer("CCGGA", "desc", 1, 20, false);
		
		FilterSinglePrimer filter2 = new FilterHomoDimerFixed3(2,new DegeneratedDNAMatchingStrategy());
		FilterSinglePrimer filter3 = new FilterHomoDimerFixed3(3,new DegeneratedDNAMatchingStrategy());
		FilterSinglePrimer filter4 = new FilterHomoDimerFixed3(4,new DegeneratedDNAMatchingStrategy());
		
		assertFalse(filter2.filter(p1a));
		assertFalse(filter2.filter(p2a));
		assertTrue(filter2.filter(p3a));
		assertTrue(filter2.filter(p4a));
		assertTrue(filter2.filter(p5a));
		assertFalse(filter2.filter(p6a));
		assertTrue(filter2.filter(p7a));
		
		assertFalse(filter3.filter(p1a));
		assertFalse(filter3.filter(p2a));
		assertTrue(filter3.filter(p3a));
		assertTrue(filter3.filter(p4a));
		assertTrue(filter3.filter(p5a));
		assertFalse(filter3.filter(p6a));
		assertTrue(filter3.filter(p7a));

		assertTrue(filter4.filter(p1a));
		assertTrue(filter4.filter(p2a));
		assertTrue(filter4.filter(p3a));
		assertTrue(filter4.filter(p4a));
		assertTrue(filter4.filter(p5a));
		assertTrue(filter4.filter(p6a));
		assertTrue(filter4.filter(p7a));
		
	}
}
