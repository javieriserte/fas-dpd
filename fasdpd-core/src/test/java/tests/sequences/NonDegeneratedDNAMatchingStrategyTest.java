
package tests.sequences;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import sequences.util.compare.NonDegeneratedDNAMatchingStrategy;

public class NonDegeneratedDNAMatchingStrategyTest {

	@BeforeEach

	protected void setUp() throws Exception {
	}

	@Test

	
	public void testMatches() {
		NonDegeneratedDNAMatchingStrategy nddms = new NonDegeneratedDNAMatchingStrategy();
		
		assertEquals((double) 1,nddms.matches('A', 'T') );
		assertEquals((double) 0,nddms.matches('A', 'C') );
		assertEquals((double) 0,nddms.matches('A', 'G') );
		assertEquals((double) 0,nddms.matches('A', 'A') );
		
		
		assertEquals((double) 0,nddms.matches('T', 'T') );
		assertEquals((double) 0,nddms.matches('T', 'C') );
		assertEquals((double) 0,nddms.matches('T', 'G') );
		assertEquals((double) 1,nddms.matches('T', 'A') );
		
		assertEquals((double) 0,nddms.matches('G', 'T') );
		assertEquals((double) 1,nddms.matches('G', 'C') );
		assertEquals((double) 0,nddms.matches('G', 'G') );
		assertEquals((double) 0,nddms.matches('G', 'A') );
		
		assertEquals((double) 0,nddms.matches('C', 'T') );
		assertEquals((double) 0,nddms.matches('C', 'C') );
		assertEquals((double) 1,nddms.matches('C', 'G') );
		assertEquals((double) 0,nddms.matches('C', 'A') );
	}

}
