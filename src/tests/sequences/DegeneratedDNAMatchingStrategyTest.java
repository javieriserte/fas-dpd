package tests.sequences;

import sequences.util.compare.DegeneratedDNAMatchingStrategy;
import junit.framework.TestCase;

public class DegeneratedDNAMatchingStrategyTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testMatches() {
		DegeneratedDNAMatchingStrategy nddms = new DegeneratedDNAMatchingStrategy();
		
		assertEquals((double)  1   ,nddms.matches('A', 'T') );
		assertEquals((double)  0   ,nddms.matches('A', 'C') );
		assertEquals((double)  0   ,nddms.matches('A', 'G') );
		assertEquals((double)  0   ,nddms.matches('A', 'A') );
		assertEquals(((double)1/2) ,nddms.matches('A', 'W') );
		assertEquals(((double)1/2) ,nddms.matches('A', 'Y') );
		assertEquals(((double)1/2) ,nddms.matches('A', 'K') );
		assertEquals(((double)1/3) ,nddms.matches('A', 'H') );
		assertEquals(((double)1/3) ,nddms.matches('A', 'B') );
		assertEquals(((double)1/3) ,nddms.matches('A', 'D') );
		assertEquals(((double)1/4) ,nddms.matches('A', 'N') );
		assertEquals((double)  0   ,nddms.matches('A', 'S') );
		assertEquals((double)  0   ,nddms.matches('A', 'R') );
		assertEquals((double)  0   ,nddms.matches('A', 'M') );
		assertEquals((double)  0   ,nddms.matches('A', 'V') );
		
		assertEquals((double)  0   ,nddms.matches('C', 'T') );
		assertEquals((double)  0   ,nddms.matches('C', 'C') );
		assertEquals((double)  1   ,nddms.matches('C', 'G') );
		assertEquals((double)  0   ,nddms.matches('C', 'A') );
		assertEquals((double)  0   ,nddms.matches('C', 'W') );
		assertEquals((double)  0   ,nddms.matches('C', 'Y') );
		assertEquals(((double)1/2) ,nddms.matches('C', 'K') );
		assertEquals((double)  0   ,nddms.matches('C', 'H') );
		assertEquals(((double)1/3) ,nddms.matches('C', 'B') );
		assertEquals(((double)1/3) ,nddms.matches('C', 'D') );
		assertEquals(((double)1/4) ,nddms.matches('C', 'N') );
		assertEquals(((double)1/2) ,nddms.matches('C', 'S') );
		assertEquals(((double)1/2) ,nddms.matches('C', 'R') );
		assertEquals((double) 0    ,nddms.matches('C', 'M') );
		assertEquals(((double)1/3) ,nddms.matches('C', 'V') );
		
		assertEquals( (double) 0   ,nddms.matches('G', 'T') );
		assertEquals( (double) 1   ,nddms.matches('G', 'C') );
		assertEquals( (double) 0   ,nddms.matches('G', 'G') );
		assertEquals( (double) 0   ,nddms.matches('G', 'A') );
		assertEquals( (double) 0   ,nddms.matches('G', 'W') );
		assertEquals(((double)1/2) ,nddms.matches('G', 'Y') );
		assertEquals( (double) 0   ,nddms.matches('G', 'K') );
		assertEquals(((double)1/3) ,nddms.matches('G', 'H') );
		assertEquals(((double)1/3) ,nddms.matches('G', 'B') );
		assertEquals( (double) 0   ,nddms.matches('G', 'D') );
		assertEquals(((double)1/4) ,nddms.matches('G', 'N') );
		assertEquals(((double)1/2) ,nddms.matches('G', 'S') );
		assertEquals( (double) 0   ,nddms.matches('G', 'R') );
		assertEquals(((double)1/2) ,nddms.matches('G', 'M') );
		assertEquals(((double)1/3) ,nddms.matches('G', 'V') );
		
		assertEquals( (double) 0   ,nddms.matches('T', 'T') );
		assertEquals( (double) 0   ,nddms.matches('T', 'C') );
		assertEquals( (double) 0   ,nddms.matches('T', 'G') );
		assertEquals( (double) 1   ,nddms.matches('T', 'A') );
		assertEquals(((double)1/2) ,nddms.matches('T', 'W') );
		assertEquals(((double) 0 ) ,nddms.matches('T', 'Y') );
		assertEquals( (double) 0   ,nddms.matches('T', 'K') );
		assertEquals(((double)1/3) ,nddms.matches('T', 'H') );
		assertEquals(((double) 0 ) ,nddms.matches('T', 'B') );
		assertEquals(((double)1/3) ,nddms.matches('T', 'D') );
		assertEquals(((double)1/4) ,nddms.matches('T', 'N') );
		assertEquals(((double) 0 ) ,nddms.matches('T', 'S') );
		assertEquals(((double)1/2) ,nddms.matches('T', 'R') );
		assertEquals(((double)1/2) ,nddms.matches('T', 'M') );
		assertEquals(((double)1/3) ,nddms.matches('T', 'V') );
		
		
		

	}

}
