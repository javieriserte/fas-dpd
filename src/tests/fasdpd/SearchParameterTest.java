
package tests.fasdpd;

import fasdpd.InvalidCommandLineException;
import fasdpd.SearchParameter;
import junit.framework.TestCase;
/**
 * Test Case.
 * 
 */

public class SearchParameterTest extends TestCase {
	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testSearchParameter() {
		String[]  cl ="/Q:30 /FDEG /INFILE: \"c:\\javier\\archivo.txt\" /GCFILE:\"c:\\javier\\gc.txt\" /OUTFILE: \"c:\\javier\\archivo2.txt\"".split(" ");
		SearchParameter s = null;

		try { s = new SearchParameter();
			s.retrieveFromCommandLine(cl);
		} catch (InvalidCommandLineException e) {e.printStackTrace(); fail(); }

		assertEquals("\"c:\\javier\\archivo.txt\"", s.getInfile().get().strip());
		assertEquals("\"c:\\javier\\archivo2.txt\"", s.getOutfile().strip());
		assertEquals("\"c:\\javier\\gc.txt\"", s.getGCfile().strip());
		assertEquals(1f,s.getNx());
		assertEquals(1f,s.getNy());
		assertEquals(0f,s.getpA());
		assertEquals(30,s.getQuantity());
		assertEquals(-1, s.getEndPoint());
		assertEquals(1, s.getStartPoint());
		assertFalse(s.isSearchPair());
		assertEquals(20,s.getLenMin());
		assertEquals(25,s.getLenMax());
		assertNull(s.getProfile());

		assertTrue(s.getFilter().toString().contains("FilterDegeneratedEnd"));
		assertTrue(s.getFilter().toString().contains("FilterPrimerScore"));
		assertTrue(s.getFilter().toString().contains("FilterMeltingPointTemperature"));
		assertTrue(s.getFilter().toString().contains("Filter5vs3Stability"));
		assertTrue(s.getFilter().toString().contains("FilterBaseRuns"));
		assertTrue(s.getFilter().toString().contains("FilterHomoDimer"));
		assertTrue(s.getFilter().toString().contains("FilterHomoDimerFixed3"));
		assertTrue(s.getFilter().toString().contains("FilterCGContent"));
		
		assertFalse(s.getFilterpair().toString().contains("FilterOverlapping"));
		assertFalse(s.getFilterpair().toString().contains("FilterAmpliconSize"));
		assertFalse(s.getFilterpair().toString().contains("FilterGCCompatibility"));
		assertFalse(s.getFilterpair().toString().contains("FilterHeteroDimer"));
		assertFalse(s.getFilterpair().toString().contains("FilterHeteroDimerFixed3"));
		assertFalse(s.getFilterpair().toString().contains("FilterMeltingTempCompatibility"));

		
		System.out.println(s.getFilter());
		System.out.println(s.getFilterpair());
		
		
		cl ="/PAIR /Q:30 /NOBASERUNS /NOSIZE /FDEG /LENMIN:18 /LENMAX:35 /INFILE: \"c:\\javier\\archivo.txt\" /GCFILE:\"c:\\javier\\gc.txt\" /OUTFILE: \"c:\\javier\\archivo2.txt\"".split(" ");
		
		try { s = new SearchParameter();
			s.retrieveFromCommandLine(cl);
			} catch (InvalidCommandLineException e) {
			e.printStackTrace();
			fail();
		}
		
		assertEquals("\"c:\\javier\\archivo.txt\"", s.getInfile().get().strip());
		assertEquals("\"c:\\javier\\archivo2.txt\"", s.getOutfile().strip());
		assertEquals("\"c:\\javier\\gc.txt\"", s.getGCfile().strip());
		assertEquals(1f,s.getNx());
		assertEquals(1f,s.getNy());
		assertEquals(0f,s.getpA());
		assertEquals(30,s.getQuantity());
		assertEquals(-1, s.getEndPoint());
		assertEquals(1, s.getStartPoint());
		assertFalse(!s.isSearchPair());
		assertEquals(18,s.getLenMin());
		assertEquals(35,s.getLenMax());
		assertNull(s.getProfile());
		
		assertTrue(s.getFilter().toString().contains("FilterDegeneratedEnd"));
		assertTrue(s.getFilter().toString().contains("FilterPrimerScore"));
		assertTrue(s.getFilter().toString().contains("FilterMeltingPointTemperature"));
		assertTrue(s.getFilter().toString().contains("Filter5vs3Stability"));
		assertFalse(s.getFilter().toString().contains("FilterBaseRuns"));
		assertTrue(s.getFilter().toString().contains("FilterHomoDimer"));
		assertTrue(s.getFilter().toString().contains("FilterHomoDimerFixed3"));
		assertTrue(s.getFilter().toString().contains("FilterCGContent"));
		
		assertTrue(s.getFilterpair().toString().contains("FilterOverlapping"));
		assertTrue(s.getFilterpair().toString().contains("FilterAmpliconSize"));
		assertTrue(s.getFilterpair().toString().contains("FilterGCCompatibility"));
		assertTrue(s.getFilterpair().toString().contains("FilterHeteroDimer"));
		assertTrue(s.getFilterpair().toString().contains("FilterHeteroDimerFixed3"));
		assertTrue(s.getFilterpair().toString().contains("FilterMeltingTempCompatibility"));
		

	}

}
