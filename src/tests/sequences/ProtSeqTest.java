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
