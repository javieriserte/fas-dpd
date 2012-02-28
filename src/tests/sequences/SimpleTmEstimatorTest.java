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

import sequences.dna.Primer;
import sequences.util.tmcalculator.SimpleTmEstimator;
import junit.framework.TestCase;

public class SimpleTmEstimatorTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testMean() {
		
		SimpleTmEstimator stme = new SimpleTmEstimator();
		
		Primer p1 = new Primer("A","",1,1,true);
		Primer p2 = new Primer("C","",1,1,true);
		Primer p3 = new Primer("T","",1,1,true);
		Primer p4 = new Primer("G","",1,1,true);
		
		Primer p11 = new Primer("AA","",1,1,true);
		Primer p21 = new Primer("AC","",1,1,true);
		Primer p31 = new Primer("AT","",1,1,true);
		Primer p41 = new Primer("AG","",1,1,true);
		
		Primer p12 = new Primer("AN","",1,1,true);
		Primer p22 = new Primer("AW","",1,1,true);
		Primer p32 = new Primer("AB","",1,1,true);
		Primer p42 = new Primer("AS","",1,1,true);

		
		stme.calculateTM(p1); assertEquals(2d,stme.mean());
		stme.calculateTM(p2); assertEquals(4d,stme.mean());
		stme.calculateTM(p3); assertEquals(2d,stme.mean());
		stme.calculateTM(p4); assertEquals(4d,stme.mean());

		stme.calculateTM(p11); assertEquals(4d,stme.mean());
		stme.calculateTM(p21); assertEquals(6d,stme.mean());
		stme.calculateTM(p31); assertEquals(4d,stme.mean());
		stme.calculateTM(p41); assertEquals(6d,stme.mean());
		
		stme.calculateTM(p12); assertEquals(5d,stme.mean());
		stme.calculateTM(p22); assertEquals(4d,stme.mean());
		
		
		stme.calculateTM(p32);
		double v1 = (8d/3+2d/3+2);
		double v2 = (stme.mean());
		double epsilon = 1d/100000d;
		
		assertTrue(Math.abs(v1 - v2 )<epsilon);
		
		stme.calculateTM(p42); assertEquals(6d,stme.mean());

	}

	public void testMax() {
		SimpleTmEstimator stme = new SimpleTmEstimator();
		
		Primer p1 = new Primer("A","",1,1,true);
		Primer p2 = new Primer("C","",1,1,true);
		Primer p3 = new Primer("T","",1,1,true);
		Primer p4 = new Primer("G","",1,1,true);
		
		Primer p11 = new Primer("AA","",1,1,true);
		Primer p21 = new Primer("AC","",1,1,true);
		Primer p31 = new Primer("AT","",1,1,true);
		Primer p41 = new Primer("AG","",1,1,true);
		
		Primer p12 = new Primer("AN","",1,1,true);
		Primer p22 = new Primer("AW","",1,1,true);
		Primer p32 = new Primer("AB","",1,1,true);
		Primer p42 = new Primer("AS","",1,1,true);

		
		
		stme.calculateTM(p1); assertEquals(2d,stme.max());
		stme.calculateTM(p2); assertEquals(4d,stme.max());
		stme.calculateTM(p3); assertEquals(2d,stme.max());
		stme.calculateTM(p4); assertEquals(4d,stme.max());

		stme.calculateTM(p11); assertEquals(4d,stme.max());
		stme.calculateTM(p21); assertEquals(6d,stme.max());
		stme.calculateTM(p31); assertEquals(4d,stme.max());
		stme.calculateTM(p41); assertEquals(6d,stme.max());
		
		stme.calculateTM(p12); assertEquals(6d,stme.max());
		stme.calculateTM(p22); assertEquals(4d,stme.max());
		stme.calculateTM(p32); assertEquals(6d,stme.max());
		stme.calculateTM(p42); assertEquals(6d,stme.max());
	}

	public void testMin() {
		SimpleTmEstimator stme = new SimpleTmEstimator();
		
		Primer p1 = new Primer("A","",1,1,true);
		Primer p2 = new Primer("C","",1,1,true);
		Primer p3 = new Primer("T","",1,1,true);
		Primer p4 = new Primer("G","",1,1,true);
		
		Primer p11 = new Primer("AA","",1,1,true);
		Primer p21 = new Primer("AC","",1,1,true);
		Primer p31 = new Primer("AT","",1,1,true);
		Primer p41 = new Primer("AG","",1,1,true);
		
		Primer p12 = new Primer("AN","",1,1,true);
		Primer p22 = new Primer("AW","",1,1,true);
		Primer p32 = new Primer("AB","",1,1,true);
		Primer p42 = new Primer("AS","",1,1,true);

		
		stme.calculateTM(p1); assertEquals(2d,stme.min());
		stme.calculateTM(p2); assertEquals(4d,stme.min());
		stme.calculateTM(p3); assertEquals(2d,stme.min());
		stme.calculateTM(p4); assertEquals(4d,stme.min());

		stme.calculateTM(p11); assertEquals(4d,stme.min());
		stme.calculateTM(p21); assertEquals(6d,stme.min());
		stme.calculateTM(p31); assertEquals(4d,stme.min());
		stme.calculateTM(p41); assertEquals(6d,stme.min());
		
		stme.calculateTM(p12); assertEquals(4d,stme.min());
		stme.calculateTM(p22); assertEquals(4d,stme.min());
		stme.calculateTM(p32); assertEquals(4d,stme.min());
		stme.calculateTM(p42); assertEquals(6d,stme.min());

	}

}
