package sequences.dna;

import sequences.Sequence;
import sequences.alignment.Apilable;
import sequences.protein.ProtSeq;
import degeneration.GeneticCode;
/**
 * Class to represent a sequence of DNA. (or RNA)
 * @author Javier Iserte <jiserte@unq.edu.ar>
 * 
 */
public class DNASeq extends Sequence {
	public DNASeq(String sequence, String description) {
		super(sequence,description);
	}

	public DNASeq getReverseComplementary() {
		return new DNASeq(DNASeq.reverseComplementary(this.getSequence()),"Complement of " + this.getDescription());
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
	 * This method is used to design a primer from a DNA sequence. The first
	 * position of the sequence is One and the last is the same that the length
	 * of the sequence. If directStrans is false, it is assumed that this
	 * sequence is the complementary of an original one. In this case the value
	 * 'from' and 'to' are modified to fit the original sequence. If you want to
	 * design primers in the complementary strand, you only need to create it
	 * just once.
	 * <pre>
	 * Example:
	 * My original sequence 'mySeq' is:
	 *---------- ----------21-------30 ---------- ---------- ----------
	 *AAAAAAAAAA AAAAAAAAAA GCAGCAGCAG AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA
	 *TTTTTTTTTT TTTTTTTTTT CGTCGTCGTC TTTTTTTTTT TTTTTTTTTT TTTTTTTTTT
     * </pre>
	 * I want to design a primer from position 21 to 30 in the direct strand
	 * (GCAGCAGCAG).:
 	 * <pre>
	 *Primer myPrimer = mySeq.designPrimer(21,30,true)<br>
	 * </pre>
	 * If I want to design a primer from position 21 to 30 in the complementary
	 * strand of 'mySeq' (CTGCTGCTGC):
	 * <pre>
	 *myCompSeq = mySeq.getComplementary();
	 *Primer myPrimer = myCompSeq.designPrimer(21,30,false);
	 * </pre>
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
			Des="Reverse Primer " + this.getDescription() + " from: " + i + " to: " + f;
			seq = this.getSequence().substring(this.getLength()-i, getLength()+1-f);
		}
		return new Primer(seq,Des,i,f,directStrand);
	}

	/**
	 * Gets the revere complementary strand of a DNA sequence.
	 * This method supports the IUPAC ambiguity code for bases.
	 * @param sequence the original DNA sequence.
	 * @return another DNA Sequence that is the reverse-complementary one.
	 */
	static public String reverseComplementary(String sequence) {

		StringBuilder result = new StringBuilder(sequence.length());
		for (char base : sequence.toCharArray()) {
			result.append(DNASeq.getComplementaryBase(base));
		}
		return result.reverse().toString();
	}


	/**
	 * Gets the complementary strand of a DNA sequence.
	 * This method supports the IUPAC ambiguity code for bases.
	 *
	 * <p>Example:
	 * <pre>
	 * DNASeq.complementary("AAAATT") = "TTTTAA"
	 * </pre>
	 *
	 * @param sequence the original DNA sequence.
	 * @return another DNA Sequence that is the complementary one.
	 */
	static public String complementary(String sequence) {

		StringBuilder result = new StringBuilder(sequence.length());
		for (char base : sequence.toCharArray()) {
			result.append(DNASeq.getComplementaryBase(base));
		}
		return result.toString();
	}
	/**
	 * Gets the DNA sequence given in reverse order.
	 *
	 * <blockquote>
	 * Example:
	 *
	 * reverse("AAAATT") = "TTAAAA"
	 *
	 * </blockquote>
	 *
	 * @param sequence the original DNA sequence.
	 * @return another DNA Sequence that is the complementary one.
	 */
	static public String reverse(String sequence) {
		StringBuilder result = new StringBuilder(sequence);
		return result.reverse().toString();
	}

	
	/**
	 * Gets the complentary of a given base.
	 * 
	 * <blockquote>
	 * Example:
	 * 
	 * reverse("AAAATT") = "TTAAAA"
	 * 
	 * </blockquote>
	 * 
	 * @param sequence the original DNA sequence.
	 * @return another DNA Sequence that is the complementary one.
	 */	
	public static char getComplementaryBase(char base) {
		char result='?';
		switch(base){
		case 'A':result='T';break;
		case 'C':result='G';break;
		case 'T':result='A';break;
		case 'G':result='C';break;

		case 'S':result='S';break;
		case 'W':result='W';break;
		case 'K':result='M';break;
		case 'M':result='K';break;
		case 'Y':result='R';break;
		case 'R':result='Y';break;

		case 'B':result='V';break;
		case 'D':result='H';break;
		case 'H':result='D';break;
		case 'V':result='B';break;

		case 'N':result='N';break;
		case '-':result='-';break;
		}
		return result;
	}

	@Override
	public Sequence toDNA() {
		return this;
	}

	@Override
	public Sequence toProtein() {
		return new ProtSeq(this.getSequence(), this.getDescription());
	}
}
