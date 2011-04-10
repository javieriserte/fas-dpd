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

import sequences.dna.DNASeq;
import sequences.protein.ProtSeq;
import degeneration.GeneticCode;
import junit.framework.TestCase;
/**
 * Test Case.
 * Unfinished.
 * @author "Javier Iserte <jiserte@unq.edu.ar>"
 *
 * @version 1.1.1
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
