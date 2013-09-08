/*
 * You may not change or alter any portion of this comment or credits
 * of supporting developers from this source code or any supporting source code
 * which is considered copyrighted (c) material of the original comment or credit authors.
 *
 * THERE IS NO WARRANTY FOR THE PROGRAM, TO THE EXTENT PERMITTED BY APPLICABLE LAW. 
 * EXCEPT WHEN OTHERWISE STATED IN WRITING THE COPYRIGHT HOLDERS AND/OR OTHER PARTIES 
 * PROVIDE THE PROGRAM �AS IS� WITHOUT WARRANTY OF ANY KIND, EITHER EXPRESSED OR IMPLIED, 
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS 
 * FOR A PARTICULAR PURPOSE. THE ENTIRE RISK AS TO THE QUALITY AND PERFORMANCE OF THE 
 * PROGRAM IS WITH YOU. SHOULD THE PROGRAM PROVE DEFECTIVE, YOU ASSUME THE COST OF ALL 
 * NECESSARY SERVICING, REPAIR OR CORRECTION.
 
 * IN NO EVENT UNLESS REQUIRED BY APPLICABLE LAW OR AGREED TO IN WRITING WILL ANY COPYRIGHT 
 * HOLDER, OR ANY OTHER PARTY WHO MODIFIES AND/OR CONVEYS THE PROGRAM AS PERMITTED ABOVE, 
 * BE LIABLE TO YOU FOR DAMAGES, INCLUDING ANY GENERAL, SPECIAL, INCIDENTAL OR CONSEQUENTIAL
 * DAMAGES ARISING OUT OF THE USE OR INABILITY TO USE THE PROGRAM (INCLUDING BUT NOT LIMITED 
 * TO LOSS OF DATA OR DATA BEING RENDERED INACCURATE OR LOSSES SUSTAINED BY YOU OR THIRD 
 * PARTIES OR A FAILURE OF THE PROGRAM TO OPERATE WITH ANY OTHER PROGRAMS), EVEN IF SUCH 
 * HOLDER OR OTHER PARTY HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * FAS-DPD project, including algorithms design, software implementation and experimental laboratory work, is being developed as a part of the Research Program:
 * 	"Microbiolog�a molecular b�sica y aplicaciones biotecnol�gicas"
 * 		(Basic Molecular Microbiology and biotechnological applications)
 * 
 * And is being conducted in:
 * 	LIGBCM: Laboratorio de Ingenier�a Gen�tica y Biolog�a Celular y Molecular.
 *		(Laboratory of Genetic Engineering and Cellular and Molecular Biology)
 *	Universidad Nacional de Quilmes.
 *		(National University Of Quilmes)
 *	Quilmes, Buenos Aires, Argentina.
 *
 * The complete team for this project is formed by:
 *	Lic.  Javier A. Iserte.
 *	Lic.  Betina I. Stephan.
 * 	ph.D. Sandra E. Go�i.
 * 	ph.D. P. Daniel Ghiringhelli.
 *	ph.D. Mario E. Lozano.
 *
 * Corresponding Authors:
 *	Javier A. Iserte. <jiserte@unq.edu.ar>
 *	Mario E. Lozano. <mlozano@unq.edu.ar>
 */

package tests.fasdpd;

import fasdpd.InvalidCommandLineException;
import fasdpd.SearchParameter;
import junit.framework.TestCase;
/**
 * Test Case.
 * Unfinished.
 * @author "Javier Iserte <jiserte@unq.edu.ar>"
 * 
 */

public class SearchParameterTest extends TestCase {
// TODO modify test to include new options!
	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testSearchParameter() {
		String[]  cl ="/Q:30 /FDEG /INFILE: \"c:\\javier\\archivo.txt\" /GCFILE:\"c:\\javier\\gc.txt\" /OUTFILE: \"c:\\javier\\archivo2.txt\"".split(" ");
		SearchParameter s = null;

		try { s = new SearchParameter();
			s.retrieveFromCommandLine(cl);
		} catch (InvalidCommandLineException e) {e.printStackTrace(); fail(); }
		
		assertEquals("\"c:\\javier\\archivo.txt\"", s.getInfile());
		assertEquals("\"c:\\javier\\archivo2.txt\"", s.getOutfile());
		assertEquals("\"c:\\javier\\gc.txt\"", s.getGCfile());
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
		
		assertEquals("\"c:\\javier\\archivo.txt\"", s.getInfile());
		assertEquals("\"c:\\javier\\archivo2.txt\"", s.getOutfile());
		assertEquals("\"c:\\javier\\gc.txt\"", s.getGCfile());
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
		
		System.out.println(s.getFilterpair());
		
		assertTrue(s.getFilterpair().toString().contains("FilterOverlapping"));
		assertFalse(s.getFilterpair().toString().contains("FilterAmpliconSize"));
		assertTrue(s.getFilterpair().toString().contains("FilterGCCompatibility"));
		assertTrue(s.getFilterpair().toString().contains("FilterHeteroDimer"));
		assertTrue(s.getFilterpair().toString().contains("FilterHeteroDimerFixed3"));
		assertTrue(s.getFilterpair().toString().contains("FilterMeltingTempCompatibility"));
		

	}

}
