package tests.filters;

import sequences.dna.Primer;
import filters.primerpair.FilterGCCompatibility;
import filters.primerpair.FilterPrimerPair;
import junit.framework.TestCase;

public class FilterGCCompatibilityTest extends TestCase {

	private Primer[] primers = new Primer[11];

	protected void setUp() throws Exception {

				
		primers[0] = new Primer("ATATATATAT", "0p", 1, 10, true);
		primers[1] = new Primer("CTATATATAT", "10p", 1, 10, true);
		primers[2] = new Primer("CGATATATAT", "20p", 1, 10, true);
		primers[3]= new Primer("CGCTATATAT", "30p", 1, 10, true);
		primers[4]= new Primer("CGCGATATAT", "40p", 1, 10, true);
		primers[5]= new Primer("CGCGCTATAT", "50p", 1, 10, true);
		primers[6]= new Primer("CGCGCGATAT", "60p", 1, 10, true);
		primers[7]= new Primer("CGCGCGCTAT", "70p", 1, 10, true);
		primers[8]= new Primer("CGCGCGCGAT", "80p", 1, 10, true);
		primers[9]= new Primer("CGCGCGCGCT", "90p", 1, 10, true);
		primers[10]= new Primer("CGCGCGCGCG", "100p", 1, 10, true);		

		
	}

	public void testValidate() {
		
		FilterPrimerPair filter10 = new FilterGCCompatibility(10);
		FilterPrimerPair filter15 = new FilterGCCompatibility(15);
		FilterPrimerPair filter20 = new FilterGCCompatibility(20);
		FilterPrimerPair filter25 = new FilterGCCompatibility(25);
		
		
		assertFalse(filter10.filter(primers[0],primers[5]));
		assertFalse(filter10.filter(primers[1],primers[5]));
		assertFalse(filter10.filter(primers[2],primers[5]));
		assertFalse(filter10.filter(primers[3],primers[5]));
		assertTrue(filter10.filter(primers[4],primers[5]));
		assertTrue(filter10.filter(primers[5],primers[5]));
		assertTrue(filter10.filter(primers[6],primers[5]));
		assertFalse(filter10.filter(primers[7],primers[5]));
		assertFalse(filter10.filter(primers[8],primers[5]));
		assertFalse(filter10.filter(primers[9],primers[5]));
		assertFalse(filter10.filter(primers[10],primers[5]));

		assertFalse(filter15.filter(primers[0],primers[5]));
		assertFalse(filter15.filter(primers[1],primers[5]));
		assertFalse(filter15.filter(primers[2],primers[5]));
		assertFalse(filter15.filter(primers[3],primers[5]));
		assertTrue(filter15.filter(primers[4],primers[5]));
		assertTrue(filter15.filter(primers[5],primers[5]));
		assertTrue(filter15.filter(primers[6],primers[5]));
		assertFalse(filter15.filter(primers[7],primers[5]));
		assertFalse(filter15.filter(primers[8],primers[5]));
		assertFalse(filter15.filter(primers[9],primers[5]));
		assertFalse(filter15.filter(primers[10],primers[5]));
		
		assertFalse(filter20.filter(primers[0],primers[5]));
		assertFalse(filter20.filter(primers[1],primers[5]));
		assertFalse(filter20.filter(primers[2],primers[5]));
		assertTrue(filter20.filter(primers[3],primers[5]));
		assertTrue(filter20.filter(primers[4],primers[5]));
		assertTrue(filter20.filter(primers[5],primers[5]));
		assertTrue(filter20.filter(primers[6],primers[5]));
		assertTrue(filter20.filter(primers[7],primers[5]));
		assertFalse(filter20.filter(primers[8],primers[5]));
		assertFalse(filter20.filter(primers[9],primers[5]));
		assertFalse(filter20.filter(primers[10],primers[5]));
		
		assertFalse(filter25.filter(primers[0],primers[5]));
		assertFalse(filter25.filter(primers[1],primers[5]));
		assertFalse(filter25.filter(primers[2],primers[5]));
		assertTrue(filter25.filter(primers[3],primers[5]));
		assertTrue(filter25.filter(primers[4],primers[5]));
		assertTrue(filter25.filter(primers[5],primers[5]));
		assertTrue(filter25.filter(primers[6],primers[5]));
		assertTrue(filter25.filter(primers[7],primers[5]));
		assertFalse(filter25.filter(primers[8],primers[5]));
		assertFalse(filter25.filter(primers[9],primers[5]));
		assertFalse(filter25.filter(primers[10],primers[5]));

		
	}
	
	
}
