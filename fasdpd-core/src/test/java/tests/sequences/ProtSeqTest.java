
package tests.sequences;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import sequences.dna.DNASeq;
import sequences.protein.ProtSeq;
import degeneration.GeneticCode;
/**
 * Test Case.
 * Unfinished.
 * @author "Javier Iserte <jiserte@unq.edu.ar>"
 */
public class ProtSeqTest {

	private ProtSeq myP1=null;
	private ProtSeq myP2=null;
	private ProtSeq myP3=null;
	private GeneticCode myGC = null;
	@BeforeEach
	protected void setUp() throws Exception {
		myGC = new GeneticCode("StandardCode");
		myP1 = new ProtSeq("ACDEFGHIKLMNPQRSTVWY*", "PROTEINA DE PRUEBA");
		myP2 = new ProtSeq("ACD", "Short Test Protein 1");
		myP3 = new ProtSeq("EFG", "Short Test Protein 2");
	}

	@Test

	
	public void testProtSeq() {
		assertEquals(myP1.getClass(),ProtSeq.class);
	}

	@Test

	
	public void testBackTranslate() {
		assertEquals(
			"GCNTGYGAYGARTTYGGNCAYATHAARYTNATGAAYCCNCARMGNWSNACNGTNTGGTAYTRR",
			myP1
				.backTranslate(myGC)
				.getSequence()
		);
	}

	@Test

	
	public void testApilarCon() {
		assertEquals(
			"GMNTKYGRN",
			myP2
				.pileUpWith(myP3, myGC)
				.getSequence()
			);
	}

	@Test

	
	public void testApilarConDNAseq() {
		assertEquals(
			"RMNWRHRAH",
			myP2
				.pileUpWithDNAseq(
					new DNASeq("AAAAAAAAA", "nada"),
					myGC
				)
				.getSequence()
		);
		assertEquals(
			"RARWWHRRN",
			myP3
				.pileUpWithDNAseq(
					new DNASeq("AAAAAAAAA", "nada"),
					myGC
				)
				.getSequence()
		);
	}

	@Test

	
	public void testApilarConProtseq() {
		assertEquals(
			"GMNTKYGRN",
			myP2
				.pileUpWithProtseq(myP3, myGC)
				.getSequence()
		);
	}
}
