
package tests.fasdpd;

import fasdpd.SearchParameter;
import fasdpd.cli.FASDPDCommandLine;
import fasdpd.cli.SearchParameterBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
/**
 * Test Case.
 */

public class SearchParameterTest {

	@Test
	public void testSearchParameterSinglePrimer() {
		String[]  cl ="/Q:30 /FDEG /INFILE: \"c:\\javier\\archivo.txt\" /OUTFILE: \"c:\\javier\\archivo2.txt\"".split(" ");
		var cmd = FASDPDCommandLine.create_default();
		assertDoesNotThrow(() -> cmd.parse(cl));
		SearchParameter s = assertDoesNotThrow(
			() -> SearchParameterBuilder.getSearchParameter(cmd)
		);

		assertEquals("\"c:\\javier\\archivo.txt\"", s.getInfile().get().strip());
		assertEquals("\"c:\\javier\\archivo2.txt\"", s.getOutfile().strip());
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
	}

	@Test
	public void testSearchParameterPrimerPair() {
		String[] cl ="/PAIR /Q:30 /NOBASERUNS /NOSIZE /FDEG /LENMIN:18 /LENMAX:35 /INFILE: \"c:\\javier\\archivo.txt\" /OUTFILE: \"c:\\javier\\archivo2.txt\"".split(" ");
		SearchParameter s = new SearchParameter();
		var cmd = FASDPDCommandLine.create_default();
		assertDoesNotThrow(() -> cmd.parse(cl));
		s = assertDoesNotThrow(() -> SearchParameterBuilder.getSearchParameter(cmd));

		assertEquals("\"c:\\javier\\archivo.txt\"", s.getInfile().get().strip());
		assertEquals("\"c:\\javier\\archivo2.txt\"", s.getOutfile().strip());
		assertEquals(1f,s.getNx());
		assertEquals(1f,s.getNy());
		assertEquals(0f,s.getpA());
		assertEquals(30,s.getQuantity());
		assertEquals(-1, s.getEndPoint());
		assertEquals(1, s.getStartPoint());
		assertTrue(s.isSearchPair());
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
