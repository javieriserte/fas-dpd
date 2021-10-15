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
 * 
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
