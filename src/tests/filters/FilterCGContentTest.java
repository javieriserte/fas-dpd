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

import filters.singlePrimer.FilterCGContent;
import sequences.dna.Primer;
import junit.framework.TestCase;

public class FilterCGContentTest extends TestCase {
		private FilterCGContent gc;
		private Primer[] primers = new Primer[11];
	
	protected void setUp() throws Exception {
		
		primers[0] = new Primer("ATATATATAT", "description", 1, 10, true);
		primers[1] = new Primer("CTATATATAT", "description", 1, 10, true);
		primers[2] = new Primer("CGATATATAT", "description", 1, 10, true);
		primers[3]= new Primer("CGCTATATAT", "description", 1, 10, true);
		primers[4]= new Primer("CGCGATATAT", "description", 1, 10, true);
		primers[5]= new Primer("CGCGCTATAT", "description", 1, 10, true);
		primers[6]= new Primer("CGCGCGATAT", "description", 1, 10, true);
		primers[7]= new Primer("CGCGCGCTAT", "description", 1, 10, true);
		primers[8]= new Primer("CGCGCGCGAT", "description", 1, 10, true);
		primers[9]= new Primer("CGCGCGCGCT", "description", 1, 10, true);
		primers[10]= new Primer("CGCGCGCGCG", "description", 1, 10, true);		
		super.setUp();
		
		gc = new FilterCGContent(50,60);
		
	}

	public void testValidate() {
		assertFalse(gc.filter(primers[0]));
		assertFalse(gc.filter(primers[1]));
		assertFalse(gc.filter(primers[2]));
		assertFalse(gc.filter(primers[3]));
		assertFalse(gc.filter(primers[4]));
		assertTrue(gc.filter(primers[5]));
		assertTrue(gc.filter(primers[6]));
		assertFalse(gc.filter(primers[7]));
		assertFalse(gc.filter(primers[8]));
		assertFalse(gc.filter(primers[9]));
		assertFalse(gc.filter(primers[10]));		
	}

}
