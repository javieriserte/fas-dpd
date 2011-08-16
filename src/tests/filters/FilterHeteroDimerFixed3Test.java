/*
 * You may not change or alter any portion of this comment or credits
 * of supporting developers from this source code or any supporting source code
 * which is considered copyrighted (c) material of the original comment or credit authors.
 *
 * THERE IS NO WARRANTY FOR THE PROGRAM, TO THE EXTENT PERMITTED BY APPLICABLE LAW. 
 * EXCEPT WHEN OTHERWISE STATED IN WRITING THE COPYRIGHT HOLDERS AND/OR OTHER PARTIES 
 * PROVIDE THE PROGRAM “AS IS” WITHOUT WARRANTY OF ANY KIND, EITHER EXPRESSED OR IMPLIED, 
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

package tests.filters;

import sequences.dna.Primer;
import sequences.util.compare.DegeneratedDNAMatchingStrategy;
import filters.primerpair.FilterHeteroDimerFixed3;
import filters.primerpair.FilterPrimerPair;
import junit.framework.TestCase;

public class FilterHeteroDimerFixed3Test extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}
	
	public void testValidate() {
		
		Primer p1a = new Primer("ACTG", "desc", 1, 20, true);
		Primer p1b = new Primer("CAGT", "desc", 100, 80, false);
		Primer p2a = new Primer("ACTG", "desc", 1, 20, true);
		Primer p2b = new Primer("TCAG", "desc", 100, 80, true);
		Primer p3a = new Primer("ACTG", "desc", 1, 20, false);
		Primer p3b = new Primer("GGCA", "desc", 100, 80, true);
		Primer p4a = new Primer("ACTG", "desc", 1, 20, false);
		Primer p4b = new Primer("AGTG", "desc", 100, 80, false);
		
		FilterPrimerPair filter2 = new FilterHeteroDimerFixed3(2,new DegeneratedDNAMatchingStrategy());
		FilterPrimerPair filter3 = new FilterHeteroDimerFixed3(3,new DegeneratedDNAMatchingStrategy());
		FilterPrimerPair filter4 = new FilterHeteroDimerFixed3(4,new DegeneratedDNAMatchingStrategy());
		
		assertFalse(filter2.filter(p1a, p1b));
		assertFalse(filter3.filter(p1a, p1b));
		assertTrue(filter4.filter(p1a, p1b));

		assertFalse(filter2.filter(p2a, p2b));
		assertTrue(filter3.filter(p2a, p2b));
		assertTrue(filter4.filter(p2a, p2b));

		assertTrue(filter2.filter(p3a, p3b));
		assertTrue(filter3.filter(p3a, p3b));
		assertTrue(filter4.filter(p3a, p3b));

		assertTrue(filter2.filter(p4a, p4b));
		assertTrue(filter3.filter(p4a, p4b));
		assertTrue(filter4.filter(p4a, p4b));
	}
	

}
