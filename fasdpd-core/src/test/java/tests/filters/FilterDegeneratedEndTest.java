package tests.filters;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import sequences.dna.Primer;
import filters.singlePrimer.FilterDegeneratedEnd;
import filters.singlePrimer.FilterSinglePrimer;

public class FilterDegeneratedEndTest {

	@BeforeEach

	protected void setUp() throws Exception {
	}
	
	@Test
	
		
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
