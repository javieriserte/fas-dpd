package tests.sequences;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import sequences.dna.DNASeq;
import sequences.dna.Primer;
import sequences.protein.ProtSeq;
import degeneration.GeneticCode;
/**
 * Test Case.
 *
 * @author "Javier Iserte <jiserte@unq.edu.ar>"
 *
 */
public class DNASeqTest {
	private DNASeq myDNASeq = null;
	private DNASeq myDNASeq2 = null;
	private ProtSeq myProtSeq = null;
	private GeneticCode myGC = null;
	private DNASeq gappedDNASeq = null;
	private DNASeq unGappedDNASeq;

	@BeforeEach

	protected void setUp() throws Exception {
		myDNASeq = new DNASeq(
			"AAAAAAAAAAAAAAAAAAAAGCAGCAGCAGAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",
			"Secuencia de prueba de 60 nt");
		myDNASeq2 = new DNASeq(
			"TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT",
			"Secuencia de prueba de 60 nt");
		myProtSeq = new ProtSeq(
			"AAAAAAAAAAAAAAAAAAAA",
			"test sequence 20 aa"
		);
		gappedDNASeq = new DNASeq(
			"-ACGT.",
			"Sequence with gaps");
		unGappedDNASeq = new DNASeq(
			"TACGTT",
			"Sequence with gaps");

		myGC = new GeneticCode("StandardCode");
	}

	@AfterEach

	protected void tearDown() throws Exception {
	}

	@Test

	
	public void testDNASeq() {
		assertEquals(myDNASeq.getClass(),DNASeq.class);
	}

	@Test

	
	public void testGetComplementary() {
		assertEquals("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTCTGCTGCTGCTTTTTTTTTTTTTTTTTTTT",myDNASeq.getReverseComplementary().getSequence());
	}

	@Test

	
	public void testTraducir() {
 
		assertEquals("KKKKKKKQQQKKKKKKKKKK",myDNASeq.translate(myGC).getSequence() );
	}

	@Test

	
	public void testApilarCon() {
		assertEquals(
			"WWWWWWWWWWWWWWWWWWWWKYWKYWKYWKWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW",
			myDNASeq
				.pileUpWith(myDNASeq2, myGC)
				.getSequence()
			);
	}

	@Test

	
	public void testPileUpWithGaps() {
		assertEquals(
			"NACGTT",
			gappedDNASeq
				.pileUpWith(unGappedDNASeq, myGC)
				.getSequence()
			);
	}

	@Test

	
	public void testApilarConDNAseq() {
		assertEquals("WWWWWWWWWWWWWWWWWWWWKYWKYWKYWKWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW",myDNASeq.pileUpWithDNAseq(myDNASeq2, myGC).getSequence());
	}

	@Test

	
	public void testApilarConProtseq() {
		DNASeq a = myDNASeq.pileUpWith(myProtSeq, myGC);
		System.out.println(a);
		assertEquals(
			"RMNRMNRMNRMNRMNRMNRMNSMNSMNSMNRMNRMNRMNRMNRMNRMNRMNRMNRMNRMN",
			myDNASeq
				.pileUpWithProtseq(myProtSeq, myGC)
				.getSequence()
		);
	}

	@Test

	
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

	@Test

	
	public void testComplementary() {
		assertEquals("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTCTGCTGCTGCTTTTTTTTTTTTTTTTTTTT",myDNASeq.getReverseComplementary().getSequence());
	}

}
