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
package degeneration;

import junit.framework.TestCase;

/**
 * Test case.
 * 
 * @author Javier Iserte <jiserte@unq.edu.ar>
 * @version 1.1.2 
 */
public class BaseDegTest extends TestCase {

	public void testNewBaseDeg() {
		Object expected = BaseDeg.newBaseDeg();
		Object actual = BaseDeg.newBaseDeg();
		assertSame(expected, actual);
	}

	public void testGetIntFromCharbase() {
		BaseDeg expected = BaseDeg.newBaseDeg();
		assertEquals(expected.getIntFromCharbase('-'),15);
		assertEquals(expected.getIntFromCharbase('A'),1);
		assertEquals(expected.getIntFromCharbase('C'),2);
		assertEquals(expected.getIntFromCharbase('M'),3);
		assertEquals(expected.getIntFromCharbase('T'),4);
		assertEquals(expected.getIntFromCharbase('W'),5);
		assertEquals(expected.getIntFromCharbase('Y'),6);
		assertEquals(expected.getIntFromCharbase('H'),7);
		assertEquals(expected.getIntFromCharbase('G'),8);
		assertEquals(expected.getIntFromCharbase('R'),9);
		assertEquals(expected.getIntFromCharbase('S'),10);
		assertEquals(expected.getIntFromCharbase('V'),11);
		assertEquals(expected.getIntFromCharbase('K'),12);
		assertEquals(expected.getIntFromCharbase('D'),13);
		assertEquals(expected.getIntFromCharbase('B'),14);
		assertEquals(expected.getIntFromCharbase('N'),15);
	}

	public void testGetCharbaseFromInt() {
		BaseDeg expected = BaseDeg.newBaseDeg();
		assertEquals(expected.getCharbaseFromInt(0),'-');
		assertEquals(expected.getCharbaseFromInt(1),'A');
		assertEquals(expected.getCharbaseFromInt(2),'C');
		assertEquals(expected.getCharbaseFromInt(3),'M');
		assertEquals(expected.getCharbaseFromInt(4),'T');
		assertEquals(expected.getCharbaseFromInt(5),'W');
		assertEquals(expected.getCharbaseFromInt(6),'Y');
		assertEquals(expected.getCharbaseFromInt(7),'H');
		assertEquals(expected.getCharbaseFromInt(8),'G');
		assertEquals(expected.getCharbaseFromInt(9),'R');
		assertEquals(expected.getCharbaseFromInt(10),'S');
		assertEquals(expected.getCharbaseFromInt(11),'V');
		assertEquals(expected.getCharbaseFromInt(12),'K');
		assertEquals(expected.getCharbaseFromInt(13),'D');
		assertEquals(expected.getCharbaseFromInt(14),'B');
		assertEquals(expected.getCharbaseFromInt(15),'N');
	}

	public void testPileUpBase() {
		BaseDeg expected = BaseDeg.newBaseDeg();
		assertEquals('A',expected.pileUpBase('A', 'A'));
		assertEquals('C',expected.pileUpBase('C', 'C'));
		assertEquals('T',expected.pileUpBase('T', 'T'));
		assertEquals('G',expected.pileUpBase('G', 'G'));
		
		assertEquals('W',expected.pileUpBase('A', 'T'));
		assertEquals('S',expected.pileUpBase('C', 'G'));
		assertEquals('M',expected.pileUpBase('A', 'C'));
		assertEquals('K',expected.pileUpBase('G', 'T'));
		assertEquals('R',expected.pileUpBase('A', 'G'));
		assertEquals('Y',expected.pileUpBase('C', 'T'));
		
		assertEquals('N',expected.pileUpBase('W', 'S'));
		assertEquals('N',expected.pileUpBase('R', 'Y'));
		assertEquals('N',expected.pileUpBase('M', 'K'));
	}
	
	public void testCalculateDegValue() {
		BaseDeg expected = BaseDeg.newBaseDeg();
		
		assertEquals(1, expected.calculateDegValue('A'));
		assertEquals(1, expected.calculateDegValue('C'));
		assertEquals(1, expected.calculateDegValue('T'));
		assertEquals(1, expected.calculateDegValue('G'));
		assertEquals(2, expected.calculateDegValue('M'));
		assertEquals(2, expected.calculateDegValue('R'));
		assertEquals(2, expected.calculateDegValue('W'));		
		assertEquals(2, expected.calculateDegValue('S'));
		assertEquals(2, expected.calculateDegValue('Y'));
		assertEquals(2, expected.calculateDegValue('K'));		
		assertEquals(3, expected.calculateDegValue('B'));
		assertEquals(3, expected.calculateDegValue('H'));
		assertEquals(3, expected.calculateDegValue('V'));		
		assertEquals(4, expected.calculateDegValue('N'));		
		
		
		
	}

}
