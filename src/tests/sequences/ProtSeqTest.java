
package tests.sequences;

import sequences.dna.DNASeq;
import sequences.protein.ProtSeq;
import degeneration.GeneticCode;
import junit.framework.TestCase;
/**
 * Test Case.
 * Unfinished.
 * @author "Javier Iserte <jiserte@unq.edu.ar>"
 *
 * 
 */
public class ProtSeqTest extends TestCase {

	private ProtSeq myP1=null;
	private ProtSeq myP2=null;
	private ProtSeq myP3=null;
	private GeneticCode myGC = null;
	
	protected void setUp() throws Exception {
		super.setUp();
		myGC = new GeneticCode("standardcode");
		myP1 = new ProtSeq("ACDEFGHIKLMNPQRSTVWY*", "PROTEINA DE PRUEBA");
		myP2 = new ProtSeq("ACD", "Short Test Protein 1");
		myP3 = new ProtSeq("EFG", "Short Test Protein 2");
	}

	public void testProtSeq() {
		assertEquals(myP1.getClass(),ProtSeq.class);
	}

	public void testBackTranslate() {
		assertEquals("GCNTGYGAYGARTTYGGNCAYATHAARYTNATGAAYCCNCARMGNWSNACNGTNTGGTAYTRR", myP1.backTranslate(myGC).getSequence());
	}

	public void testApilarCon() {
		
		assertEquals("GMNTKYGRN", myP2.pileUpWith(myP3, myGC).getSequence());
		
	}

	public void testApilarConDNAseq() {
		
		assertEquals("RMNWRHRAH", myP2.pileUpWithDNAseq(new DNASeq("AAAAAAAAA", "nada"), myGC).getSequence());
		assertEquals("RARWWHRRN", myP3.pileUpWithDNAseq(new DNASeq("AAAAAAAAA", "nada"), myGC).getSequence());
		
	}

	public void testApilarConProtseq() {
		assertEquals("GMNTKYGRN", myP2.pileUpWithProtseq(myP3, myGC).getSequence());
	}
}
