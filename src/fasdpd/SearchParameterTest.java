/*
 * You may not change or alter any portion of this comment or credits
 * of supporting developers from this source code or any supporting source code
 * which is considered copyrighted (c) material of the original comment or credit authors.
 * This program is distributed WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * 
 * FAS-DPD project, including algorithms design, software implementation and experimental laboratory work, is being developed as a part of the Research Program:
 * 	"Microbiología molecular básica y aplicaciones biotecnológicas"
 * 		(Basic Molecular Microbiology and biotechnological applications)
 * 
 * And is being conducted in:
 * 	LIGBCM: Laboratorio de Ingeniería Genética y Biología Celular y Molecular.
 *		(Laboratory of Genetic Engineering and Cellular and Molecular Biology)
 *	Universidad Nacional de Quilmes.
 *		(National University Of Quilmes)
 *	Quilmes, Buenos Aires, Argentina.
 *
 * The complete team for this project is formed by:
 *	Lic.  Javier A. Iserte.
 *	Lic.  Betina I. Stephan.
 * 	ph.D. Sandra E. Goñi.
 * 	ph.D. P. Daniel Ghiringhelli.
 *	ph.D. Mario E. Lozano.
 *
 * Corresponding Authors:
 *	Javier A. Iserte. <jiserte@unq.edu.ar>
 *	Mario E. Lozano. <mlozano@unq.edu.ar>
 */
package fasdpd;

import junit.framework.TestCase;
/**
 * Test Case.
 * Unfinished.
 * @author "Javier Iserte <jiserte@unq.edu.ar>"
 * @version 1.1.1
 */
public class SearchParameterTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testSearchParameter() {
		String[]  cl ="/Len: 20 /Q:30 /FDEG /INFILE: \"c:\\javier\\archivo.txt\"".split(" ");
		SearchParameter s = null;
		try {
			s = new SearchParameter();
			s.retrieveFromCommandLine(cl);
		} catch (InvalidCommandLineException e) {
			fail();
		}
		
		
		cl ="/Len: 20 /Q:30 /FDEG /INFILE: \"c:\\javier\\archivo.txt\" /OUTFILE: \"c:\\javier\\archivo2.txt\"".split(" ");
		
		try { s = new SearchParameter();
			s.retrieveFromCommandLine(cl);
			} catch (InvalidCommandLineException e) {
			e.printStackTrace();
			fail();
		}
		
		assertEquals(s.getInfile(),"\"c:\\javier\\archivo.txt\"");
		assertEquals(s.getLen(), 20);
		assertEquals(s.getQuantity(), 30);
		assertEquals(s.getOutfile(), "\"c:\\javier\\archivo2.txt\"");
		assertEquals(s.getEndPoint(), -1);
		assertEquals(s.getStartPoint(), 0);
		
	}

}
