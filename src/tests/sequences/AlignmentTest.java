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
package tests.sequences;

import java.awt.Color;
import java.util.List;

import sequences.Sequence;
import sequences.alignment.Alignment;
import sequences.alignment.htmlproducer.AlignmentHTMLProducer;
import sequences.dna.DNASeq;

import degeneration.GeneticCode;
import junit.framework.TestCase;
/**
 * Test Case
 * 
 * @author "Javier Iserte <jiserte@unq.edu.ar>"
 * @version 1.1.2
 */
public class AlignmentTest extends TestCase {
	private DNASeq ds1;
	private DNASeq ds2;
	private DNASeq ds3;

	
	private Alignment alin1;

	
	
	protected void setUp() throws Exception {
		ds1 = new DNASeq("ACTGTG","ds1");
		ds2 = new DNASeq("AAAAAA","ds2");
		ds3 = new DNASeq("TTTTTT","ds3");
		                 

		alin1 = new Alignment();

		super.setUp();
	}

	public void getSeq_WhenTheAlignmentIsNew_HaveNoSequences() {
		//Arrange
		alin1 = new Alignment();
		
		//Act
		List<Sequence> sequences = alin1.getSeq();
		
		//Assert
		assertEquals(sequences.size(), 0);
	}
	
	public void getSeq_GetTheSequencesList() {
		alin1.addSequence(ds1);
		assertEquals(alin1.getSeq().size(), 1);
		alin1.addSequence(ds2);
		assertEquals(alin1.getSeq().size(), 2);
		alin1.addSequence(ds3);
		assertEquals(alin1.getSeq().size(), 3);
	}

	public void testRemoveSequence() {
		
		
		alin1.addSequence(ds1);
		alin1.addSequence(ds2);
		alin1.addSequence(ds3);
		assertEquals(alin1.getSeq().size(), 3);
		alin1.removeSequence(ds1.getDescription());
		assertEquals(alin1.getSeq().size(), 2);

	}

	public void testVerifyDifferentDescriptions() {
		alin1.addSequence(ds1);
		alin1.addSequence(ds2);
		alin1.addSequence(ds3);
		assertTrue(alin1.verifyDifferentDescriptions());
		alin1.addSequence(ds3);
		assertFalse(alin1.verifyDifferentDescriptions());
	}

	public void testPileUp() {
		alin1.addSequence(ds1);
		alin1.addSequence(ds2);
		alin1.addSequence(ds3);
		DNASeq ds = alin1.pileUp(new GeneticCode("StandardCode"));
		assertEquals(ds.getSequence(), "WHWDWD");
		
	}
	
	public void testExportHTML_highlated() {
		alin1.addSequence(ds1);
		alin1.addSequence(ds2);
		alin1.addSequence(ds3);
		
		String r =(new AlignmentHTMLProducer()).produceHTML(alin1, null, null, new Color(255, 0, 255));
		
		System.out.println(r);
//		System.out.println("HOLA");
	}

}
