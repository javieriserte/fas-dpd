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
