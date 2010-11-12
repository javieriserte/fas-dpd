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
/*
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
/**
 * Class to represent a sequence of DNA. (or RNA)
 * @author Javier Iserte <jiserte@unq.edu.ar>
 * @version 1.1.7
 */
public class DNASeq extends Sequence implements Apilable{
	
	// CONSTRUCTOR
	public DNASeq(String sequence, String description) {
		super(sequence,description);
	}

	// INSTANCE METHODS
	public DNASeq getComplementary() {
		return new DNASeq(this.complementary(this.getSequence()),"Complement of " + this.getDescription());
	}
	
	/**
	 * This method translate a DNA sequence into a amino acid sequence, according to a genetic Code.
	 * 
	 * Precondition: The sequence length is multiple of three.
	 * @param myGc a genetic code.
	 * @return a Protein sequence
	 */
	public ProtSeq translate(GeneticCode myGc) {
		StringBuilder result= new StringBuilder(this.getLength()/3);		
		for (int x=0;x<this.getLength();x=x+3) {
			result.append(myGc.translate(this.getSequence().substring(x, x+3)));
		}
		return new ProtSeq(result.toString(), "Translation of " + this.getDescription());
	}

	/**
	 * this method is used to pile up a DNA sequence with another sequence into a DNA sequence.
	 * Is part of a 'double dispatching' pattern.
	 */
	@Override
	public DNASeq pileUpWith(Apilable anotherApilable, GeneticCode myGC) {
		return anotherApilable.pileUpWithDNAseq(this, myGC);
	}
	/**
	 * this method is used to pile up two DNA sequences into a DNA sequence.
	 * Is part of a 'double dispatching' pattern.
	 */
	public DNASeq pileUpWithDNAseq(DNASeq anotherSeq, GeneticCode myGC) {
		DNASeq result = new DNASeq(myGC.pileUp(this.getSequence(), anotherSeq.getSequence()),"Piled Up Sequences");
		return result;
	}
	/**
	 * this method is used to pile up a DNA sequence to a protein sequence into a DNA sequence.
	 * Is part of a 'double dispatching' pattern.
	 */
	public DNASeq pileUpWithProtseq(ProtSeq anotherSeq, GeneticCode myGC) {
		return anotherSeq.backTranslate(myGC).pileUpWithDNAseq(this,myGC);
	}
	
	/**
	 * This method is used to design a primer from a DNA sequence. 
	 * The first position of the sequence is One and the last is the same that the length of the sequence.
	 * If directStrans is false, it is assumed that this sequence is the complementary of an original one.
	 * In this case the value 'from' and 'to' are modified to fit the original sequence.
	 * If you want to design primers in the complementary strand, you only need to create it just once.
	 * 
	 * <blockquote>
	 * Example: <br>
	 * My original sequence 'mySeq' is:<br>
	 * <p><code>
	 * |---------- ----------21-------30 ---------- ---------- ----------<br>  
	 * "AAAAAAAAAA AAAAAAAAAA GCAGCAGCAG AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA"<br>
	 * "TTTTTTTTTT TTTTTTTTTT CGTCGTCGTC TTTTTTTTTT TTTTTTTTTT TTTTTTTTTT"<br>
	 *  </code></p>
	 * I want to design a primer from position 21 to 30 in the direct strand (GCAGCAGCAG).:
 	 * <p><code>
	 * Primer myPrimer = mySeq.designPrimer(21,30,true)<br>
	 * </code></p>
	 *  
	 * If I want to design a primer from position 21 to 30 in the complementary strand of 'mySeq' (CTGCTGCTGC):
	 * <p><code>
	 * myCompSeq = mySeq.getComplementary();<br>
	 * Primer myPrimer = myCompSeq.designPrimer(21,30,false);
	 *  </code></p>
	 * 
	 * @param from an index that indicates the first position of the primer	 
	 * @param to an index that indicates the last position of the primer
	 * @return Primer the resulting primer.
	 */
	public Primer designPrimer(int from, int to, boolean directStrand) {
		String Des="";
		int i=0;
		int f=0;
		String seq = "";
	
		if(directStrand) {
			i=from;
			f=to;
			Des="Forward primer " + this.getDescription() + " from: " + i + " to: " + f;
			seq = this.getSequence().substring(i-1, f);

		} else {
			i= to;
			f= from;
			Des="Reverse Primer " + this.getDescription() + " frm: " + i + " to: " + f;
			seq = this.getSequence().substring(this.getLength()-i, getLength()+1-f);
		}
		return new Primer(seq,Des,i,f,directStrand);
	}
	
	/**
	 * Gets the complementary strand of a DNA sequence.
	 * This method supports the IUPAC ambiguity code for bases.   
	 * @param sequence the original DNA sequence.
	 * @return another DNA Sequence that is the complementary one.
	 */
	public String complementary(String sequence) {

		StringBuilder result = new StringBuilder(sequence.length()); 
		for (char base : sequence.toCharArray()) {
			
			switch(base){
			case 'A':result.append('T');break;
			case 'C':result.append('G');break;
			case 'T':result.append('A');break;
			case 'G':result.append('C');break;
			
			case 'S':result.append('S');break;
			case 'W':result.append('W');break;
			case 'K':result.append('M');break;
			case 'M':result.append('K');break;
			case 'Y':result.append('R');break;
			case 'R':result.append('Y');break;

			case 'B':result.append('V');break;
			case 'D':result.append('H');break;
			case 'H':result.append('D');break;
			case 'V':result.append('B');break;
			
			case 'N':result.append('N');break;
			case '-':result.append('-');break;
			}
		}
		return result.reverse().toString();
	}
	
}
