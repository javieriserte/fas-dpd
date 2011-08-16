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
