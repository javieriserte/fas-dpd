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

import sequences.dna.DNASeq;
import sequences.dna.Primer;
import sequences.protein.ProtSeq;
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
		assertEquals("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTCTGCTGCTGCTTTTTTTTTTTTTTTTTTTT",myDNASeq.getReverseComplementary().getSequence());
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
		Primer p2 = myDNASeq.getReverseComplementary().designPrimer(21, 30, false);
		
		assertEquals("GCAGCAGCAG",p1.getSequence());
		assertEquals("CTGCTGCTGC",p2.getSequence());
		
		assertEquals(30, p2.getStart());
		assertEquals(21, p2.getEnd());

		assertEquals(21, p1.getStart());
		assertEquals(30, p1.getEnd());

		
	}

	public void testComplementary() {
		assertEquals("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTCTGCTGCTGCTTTTTTTTTTTTTTTTTTTT",myDNASeq.getReverseComplementary().getSequence());
	}

}
