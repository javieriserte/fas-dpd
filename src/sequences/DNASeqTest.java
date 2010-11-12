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
package sequences;

import degeneration.GeneticCode;
import junit.framework.TestCase;
/**
 * Test Case.
 * 
 * @author "Javier Iserte <jiserte@unq.edu.ar>"
 * @version 1.1.1
 */
public class DNASeqTest extends TestCase {
	private DNASeq myDNASeq = null;
	private DNASeq myDNASeq2 = null;
	private ProtSeq myProtSeq = null;
	private GeneticCode myGC = null;
	
	public DNASeqTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		
		myDNASeq = new DNASeq("AAAAAAAAAAAAAAAAAAAAGCAGCAGCAGAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", "Secuencia de prueba de 60 nt");
		myDNASeq2 = new DNASeq("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT", "Secuencia de prueba de 60 nt");
		myProtSeq = new ProtSeq("AAAAAAAAAAAAAAAAAAAA", "test sequence 20 aa");
		myGC = new GeneticCode("standardcode");
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testDNASeq() {
		assertEquals(myDNASeq.getClass(),DNASeq.class);
	}

	public void testGetComplementary() {
		assertEquals("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTCTGCTGCTGCTTTTTTTTTTTTTTTTTTTT",myDNASeq.getComplementary().getSequence());
	}

	public void testTraducir() {
 
		assertEquals("KKKKKKKQQQKKKKKKKKKK",myDNASeq.translate(myGC).getSequence() );
	}

	public void testApilarCon() {
		assertEquals("WWWWWWWWWWWWWWWWWWWWKYWKYWKYWKWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW",myDNASeq.pileUpWith(myDNASeq2, myGC).getSequence());
	}

	public void testApilarConDNAseq() {
		assertEquals("WWWWWWWWWWWWWWWWWWWWKYWKYWKYWKWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW",myDNASeq.pileUpWithDNAseq(myDNASeq2, myGC).getSequence());
	}

	public void testApilarConProtseq() {

		assertEquals("RMNRMNRMNRMNRMNRMNRMNSMNSMNSMNRMNRMNRMNRMNRMNRMNRMNRMNRMNRMN",myDNASeq.pileUpWithProtseq(myProtSeq, myGC).getSequence());
	}

	public void testDesignPrimer() {
		Primer p1 = myDNASeq.designPrimer(21, 30, true);
		Primer p2 = myDNASeq.getComplementary().designPrimer(21, 30, false);
		
		assertEquals("GCAGCAGCAG",p1.getSequence());
		assertEquals("CTGCTGCTGC",p2.getSequence());
		
		assertEquals(30, p2.getStart());
		assertEquals(21, p2.getEnd());

		assertEquals(21, p1.getStart());
		assertEquals(30, p1.getEnd());

		
	}

	public void testComplementary() {
		assertEquals("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTCTGCTGCTGCTTTTTTTTTTTTTTTTTTTT",myDNASeq.getComplementary().getSequence());
	}

}
