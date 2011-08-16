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

package tests.fasdpd;

import java.util.List;

import fasdpd.PriorityList;

import junit.framework.TestCase;

/**
 * Test Case.
 * Unfinished.
 * @author "Javier Iserte <jiserte@unq.edu.ar>"
 * @version 1.1.1
 */
public class PriorityListTest extends TestCase {
	private PriorityList<Integer> lp1;
	
	protected void setUp() throws Exception {
		super.setUp();
		lp1 = new PriorityList<Integer>(10);
	}

	public void testListaDePrioridad() {
		assertSame(PriorityList.class,lp1.getClass());
	}


	public void testAddValue() {
		lp1.addValue(1);
		lp1.addValue(2);
		lp1.addValue(3);
		lp1.addValue(4);
		lp1.addValue(5);
		lp1.addValue(6);
		lp1.addValue(7);
		lp1.addValue(8);
		lp1.addValue(9);
		lp1.addValue(10);
		lp1.addValue(11);
		lp1.addValue(0);
		lp1.addValue(12);
		
		System.out.println(lp1.getCurrentFilled());
		
		assertEquals(10, lp1.getCurrentFilled());
	}

	public void testExtractSortedList() {
		lp1.addValue(1);
		lp1.addValue(2);
		lp1.addValue(3);
		lp1.addValue(4);
		lp1.addValue(5);
		lp1.addValue(6);
		lp1.addValue(7);
		lp1.addValue(8);
		lp1.addValue(9);
		lp1.addValue(10);
		lp1.addValue(11);
		lp1.addValue(0);
		lp1.addValue(12);
		
		List<Integer> l = lp1.ExtractSortedList(); 
		 
		assertEquals(12, (int) l.get(0));
		assertEquals(11, (int) l.get(1));
		assertEquals(10, (int) l.get(2));
		assertEquals(9, (int) l.get(3));
		assertEquals(8, (int) l.get(4));
		assertEquals(7, (int) l.get(5));
		assertEquals(6, (int) l.get(6));
		assertEquals(5, (int) l.get(7));
		assertEquals(4, (int) l.get(8));
		assertEquals(3, (int) l.get(9));
		
		
	}

}
