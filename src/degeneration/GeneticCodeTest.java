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
package degeneration;

import java.util.List;
import java.util.Vector;

import junit.framework.TestCase;

/**
 * Test Case.
 * @author "Javier Iserte <jiserte@unq.edu.ar>"
 * @version 1.1.3 
 */
public class GeneticCodeTest extends TestCase {

	
	
	public void testGeneticCode() {
		GeneticCode gc = new GeneticCode("StandardCode");
		assertEquals(gc.getAminoToCodonList().size(),21);
		assertEquals(gc.getAminoToDegCodon().size(),21);
		assertEquals(gc.getCodonToAmino().size(),64);
	}

	public void testGetRetroCodon() {
		GeneticCode gc = new GeneticCode("StandardCode");		
		assertEquals("GCN", gc.getRetroCodon("A"));
		assertEquals("YTN", gc.getRetroCodon("L"));
		assertEquals("TRR", gc.getRetroCodon("*"));
		
		
	}

	public void testAddCodons(){
		GeneticCode gc = new GeneticCode();
		
		String[] all = new String[21];
		int i=0;
		all[i++] = "A, GCT, GCC, GCA, GCG";
		all[i++] = "C, TGT, TGC";
		all[i++] = "D, GAT, GAC";
		all[i++] = "E, GAA, GAG";
		all[i++] = "F, TTT, TTC";
		all[i++] = "G, GGT, GGC, GGA, GGG";
		all[i++] = "H, CAT, CAC";
		all[i++] = "I, ATT, ATC, ATA";
		all[i++] = "K, AAA, AAG";
		all[i++] = "L, TTA, TTG, CTT, CTC, CTA, CTG";
		all[i++] = "M, ATG";
		all[i++] = "N, AAT, AAC";
		all[i++] = "P, CCT, CCC, CCA, CCG";
		all[i++] = "Q, CAA, CAG";
		all[i++] = "R, CGT, CGC, CGA, CGG, AGA, AGG";
		all[i++] = "S, TCT, TCC, TCA, TCG, AGT, AGC";
		all[i++] = "T, ACT, ACC, ACA, ACG";
		all[i++] = "V, GTT, GTC, GTA, GTG";
		all[i++] = "W, TGG";
		all[i++] = "Y, TAT, TAC";
		all[i++] = "*, TAA, TGA, TAG";
		
		List<String> c ;
		for (String list : all) {
			c = new Vector<String>();
			for (String string : list.split(",")) {
				c.add(string);
			}
			gc.addCodons(c.get(0), c.subList(1, c.size()));
		}
		
		gc.process();
		
		List<String> cc= gc.getCodonArray("A");
		 
		
		assertEquals(4,cc.size());
		assertTrue(cc.contains("GCT"));
		assertTrue(cc.contains("GCC"));
		assertTrue(cc.contains("GCA"));
		assertTrue(cc.contains("GCG"));
		



		
		
	}
	
	public void testGetCodonArray() {
		GeneticCode gc = new GeneticCode("StandardCode");
		List<String> cc= gc.getCodonArray("A");
		 
		
		assertEquals(4,cc.size());
		assertTrue(cc.contains("GCT"));
		assertTrue(cc.contains("GCC"));
		assertTrue(cc.contains("GCA"));
		assertTrue(cc.contains("GCG"));

	}

	public void testTranslate() {
		GeneticCode gc = new GeneticCode("StandardCode");

		assertTrue(gc.translate("GCT").equals("A"));
		assertTrue(gc.translate("GCC").equals("A"));
		assertTrue(gc.translate("GCA").equals("A"));
		assertTrue(gc.translate("GCG").equals("A"));
	}

	public void testPileUp() {
		GeneticCode gc = new GeneticCode("StandardCode");
		
		assertEquals(gc.pileUp("ACTGACTGACTGACTG", "AAAACCCCTTTTGGGG"), "AMWRMCYSWYTKRSKG");
		assertEquals(gc.pileUp("ACTGACTGACTGACTGACTGACTG", "MMMMWWWWRRRRYYYYKKKKSSSS"), "MMHVWHWDRVDRHYYBDBKKVSBS");
		assertEquals(gc.pileUp("ACTGACTGACTGACTG", "BBBBDDDDHHHHVVVV"),"NBBBDNDDHHHNVVNV" );
	}


}
