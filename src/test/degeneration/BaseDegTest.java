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

package test.degeneration;

import degeneration.BaseDeg;
import junit.framework.TestCase;

/**
 * Test case.
 * 
 * @author Javier Iserte <jiserte@unq.edu.ar>
 * @version 1.1.2 
 */
public class BaseDegTest extends TestCase {

	public void testGetIntFromCharbase() {
		assertEquals(BaseDeg.getIntFromChar('-'),15);
		assertEquals(BaseDeg.getIntFromChar('A'),1);
		assertEquals(BaseDeg.getIntFromChar('C'),2);
		assertEquals(BaseDeg.getIntFromChar('M'),3);
		assertEquals(BaseDeg.getIntFromChar('T'),4);
		assertEquals(BaseDeg.getIntFromChar('W'),5);
		assertEquals(BaseDeg.getIntFromChar('Y'),6);
		assertEquals(BaseDeg.getIntFromChar('H'),7);
		assertEquals(BaseDeg.getIntFromChar('G'),8);
		assertEquals(BaseDeg.getIntFromChar('R'),9);
		assertEquals(BaseDeg.getIntFromChar('S'),10);
		assertEquals(BaseDeg.getIntFromChar('V'),11);
		assertEquals(BaseDeg.getIntFromChar('K'),12);
		assertEquals(BaseDeg.getIntFromChar('D'),13);
		assertEquals(BaseDeg.getIntFromChar('B'),14);
		assertEquals(BaseDeg.getIntFromChar('N'),15);
	}

	public void testGetCharbaseFromInt() {
		assertEquals(BaseDeg.getCharFromInt(0),'-');
		assertEquals(BaseDeg.getCharFromInt(1),'A');
		assertEquals(BaseDeg.getCharFromInt(2),'C');
		assertEquals(BaseDeg.getCharFromInt(3),'M');
		assertEquals(BaseDeg.getCharFromInt(4),'T');
		assertEquals(BaseDeg.getCharFromInt(5),'W');
		assertEquals(BaseDeg.getCharFromInt(6),'Y');
		assertEquals(BaseDeg.getCharFromInt(7),'H');
		assertEquals(BaseDeg.getCharFromInt(8),'G');
		assertEquals(BaseDeg.getCharFromInt(9),'R');
		assertEquals(BaseDeg.getCharFromInt(10),'S');
		assertEquals(BaseDeg.getCharFromInt(11),'V');
		assertEquals(BaseDeg.getCharFromInt(12),'K');
		assertEquals(BaseDeg.getCharFromInt(13),'D');
		assertEquals(BaseDeg.getCharFromInt(14),'B');
		assertEquals(BaseDeg.getCharFromInt(15),'N');
	}

	public void testPileUpBase() {
		assertEquals('A',BaseDeg.pileUpBase('A', 'A'));
		assertEquals('C',BaseDeg.pileUpBase('C', 'C'));
		assertEquals('T',BaseDeg.pileUpBase('T', 'T'));
		assertEquals('G',BaseDeg.pileUpBase('G', 'G'));
		
		assertEquals('W',BaseDeg.pileUpBase('A', 'T'));
		assertEquals('S',BaseDeg.pileUpBase('C', 'G'));
		assertEquals('M',BaseDeg.pileUpBase('A', 'C'));
		assertEquals('K',BaseDeg.pileUpBase('G', 'T'));
		assertEquals('R',BaseDeg.pileUpBase('A', 'G'));
		assertEquals('Y',BaseDeg.pileUpBase('C', 'T'));
		
		assertEquals('N',BaseDeg.pileUpBase('W', 'S'));
		assertEquals('N',BaseDeg.pileUpBase('R', 'Y'));
		assertEquals('N',BaseDeg.pileUpBase('M', 'K'));
	}
	
	public void testCalculateDegValue() {
		
		assertEquals(1, BaseDeg.getDegValueFromChar('A'));
		assertEquals(1, BaseDeg.getDegValueFromChar('C'));
		assertEquals(1, BaseDeg.getDegValueFromChar('T'));
		assertEquals(1, BaseDeg.getDegValueFromChar('G'));
		assertEquals(2, BaseDeg.getDegValueFromChar('M'));
		assertEquals(2, BaseDeg.getDegValueFromChar('R'));
		assertEquals(2, BaseDeg.getDegValueFromChar('W'));		
		assertEquals(2, BaseDeg.getDegValueFromChar('S'));
		assertEquals(2, BaseDeg.getDegValueFromChar('Y'));
		assertEquals(2, BaseDeg.getDegValueFromChar('K'));		
		assertEquals(3, BaseDeg.getDegValueFromChar('B'));
		assertEquals(3, BaseDeg.getDegValueFromChar('H'));
		assertEquals(3, BaseDeg.getDegValueFromChar('V'));		
		assertEquals(4, BaseDeg.getDegValueFromChar('N'));		
		
	}

	public void testcontainsBaseInt() {
		
		// A
		assertTrue(BaseDeg.containsBaseIntInChar(0,'A'));
		assertFalse(BaseDeg.containsBaseIntInChar(0,'C'));
		assertFalse(BaseDeg.containsBaseIntInChar(0,'T'));
		assertFalse(BaseDeg.containsBaseIntInChar(0,'G'));
		assertTrue(BaseDeg.containsBaseIntInChar(0,'M'));
		assertTrue(BaseDeg.containsBaseIntInChar(0,'R'));
		assertTrue(BaseDeg.containsBaseIntInChar(0,'W'));		
		assertFalse(BaseDeg.containsBaseIntInChar(0,'S'));
		assertFalse(BaseDeg.containsBaseIntInChar(0,'Y'));
		assertFalse(BaseDeg.containsBaseIntInChar(0,'K'));		
		assertFalse(BaseDeg.containsBaseIntInChar(0,'B'));
		assertTrue(BaseDeg.containsBaseIntInChar(0,'H'));
		assertTrue(BaseDeg.containsBaseIntInChar(0,'V'));		
		assertTrue(BaseDeg.containsBaseIntInChar(0,'N'));	

		// C
		assertFalse(BaseDeg.containsBaseIntInChar(1,'A'));
		assertTrue(BaseDeg.containsBaseIntInChar(1,'C'));
		assertFalse(BaseDeg.containsBaseIntInChar(1,'T'));
		assertFalse(BaseDeg.containsBaseIntInChar(1,'G'));
		assertTrue(BaseDeg.containsBaseIntInChar(1,'M'));
		assertFalse(BaseDeg.containsBaseIntInChar(1,'R'));
		assertFalse(BaseDeg.containsBaseIntInChar(1,'W'));		
		assertTrue(BaseDeg.containsBaseIntInChar(1,'S'));
		assertTrue(BaseDeg.containsBaseIntInChar(1,'Y'));
		assertFalse(BaseDeg.containsBaseIntInChar(1,'K'));		
		assertTrue(BaseDeg.containsBaseIntInChar(1,'B'));
		assertTrue(BaseDeg.containsBaseIntInChar(1,'H'));
		assertTrue(BaseDeg.containsBaseIntInChar(1,'V'));		
		assertTrue(BaseDeg.containsBaseIntInChar(1,'N'));
//		
//		
		// T
		assertFalse(BaseDeg.containsBaseIntInChar(2,'A'));
		assertFalse(BaseDeg.containsBaseIntInChar(2,'C'));
		assertTrue(BaseDeg.containsBaseIntInChar(2,'T'));
		assertFalse(BaseDeg.containsBaseIntInChar(2,'G'));
		assertFalse(BaseDeg.containsBaseIntInChar(2,'M'));
		assertFalse(BaseDeg.containsBaseIntInChar(2,'R'));
		assertTrue(BaseDeg.containsBaseIntInChar(2,'W'));		
		assertFalse(BaseDeg.containsBaseIntInChar(2,'S'));
		assertTrue(BaseDeg.containsBaseIntInChar(2,'Y'));
		assertTrue(BaseDeg.containsBaseIntInChar(2,'K'));		
		assertTrue(BaseDeg.containsBaseIntInChar(2,'B'));
		assertTrue(BaseDeg.containsBaseIntInChar(2,'H'));
		assertFalse(BaseDeg.containsBaseIntInChar(2,'V'));		
		assertTrue(BaseDeg.containsBaseIntInChar(2,'N'));
//		
//		// G
		assertFalse(BaseDeg.containsBaseIntInChar(3,'A'));
		assertFalse(BaseDeg.containsBaseIntInChar(3,'C'));
		assertFalse(BaseDeg.containsBaseIntInChar(3,'T'));
		assertTrue(BaseDeg.containsBaseIntInChar(3,'G'));
		assertFalse(BaseDeg.containsBaseIntInChar(3,'M'));
		assertTrue(BaseDeg.containsBaseIntInChar(3,'R'));
		assertFalse(BaseDeg.containsBaseIntInChar(3,'W'));		
		assertTrue(BaseDeg.containsBaseIntInChar(3,'S'));
		assertFalse(BaseDeg.containsBaseIntInChar(3,'Y'));
		assertTrue(BaseDeg.containsBaseIntInChar(3,'K'));		
		assertTrue(BaseDeg.containsBaseIntInChar(3,'B'));
		assertFalse(BaseDeg.containsBaseIntInChar(3,'H'));
		assertTrue(BaseDeg.containsBaseIntInChar(3,'V'));		
		assertTrue(BaseDeg.containsBaseIntInChar(3,'N'));
		
	}
	
}
