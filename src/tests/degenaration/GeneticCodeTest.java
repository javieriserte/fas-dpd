
package tests.degenaration;

import java.util.List;
import java.util.Vector;

import degeneration.GeneticCode;

import junit.framework.TestCase;

public class GeneticCodeTest extends TestCase {

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
