package sequences.protein;

import java.util.Arrays;

import sequences.Sequence;
import sequences.alignment.Apilable;
import sequences.dna.DNASeq;
import degeneration.GeneticCode;

/**
 * This class represents a protein Sequence.
 * @author Javier Iserte <jiserte@unq.edu.ar>
 */
public class ProtSeq extends Sequence {
	//CONSTRUCTOR
	/**
	 * Creates a new instance of ProtSeq from a sequence and a description that is useful
	 * to identify the sequence.
	 */
	public ProtSeq(String sequence, String description) {
		super(sequence, description);
	}

	// INSTANCE METHODS
	/**
	 * Creates a new DNA sequence from a protein by back translation of the protein sequence.
	 * The back translation gives usually multiple choices given the degeneration of genetic code.
	 * So the newly created DNA sequence is degenerated.
	 *
	 * @param myGC is the genetic code used in the back translation process.
	 */
	public DNASeq backTranslate(GeneticCode myGC){

		StringBuilder result = new StringBuilder(3*this.getLength());
		for (int x=0;x<this.getLength();x++){
			String currentAminoAcid = this.getSequence().substring(x, x+1);
			String retroCodon = myGC.getRetroCodon(currentAminoAcid);
			if (retroCodon==null) { // If gaps or non amino acid chars are found, an "NNN" codon is assigned.
				retroCodon = "NNN";
			}
			result.append(retroCodon);
		}

		return new DNASeq(result.toString(), "Back Translation of" + this.getDescription()) ;
	}

	/**
	 * this method is used to pile up a Protein sequence with another sequence into a DNA sequence.
	 * Is part of a 'double dispatching' pattern.
	 */
	@Override
	public DNASeq pileUpWith(Apilable otroApilable, GeneticCode myGC) {
		return otroApilable.pileUpWithProtseq(this,myGC);
	}
	/**
	 * this method is used to pile up a Protein sequence with a DNA sequence into a DNA sequence.
	 * Is part of a 'double dispatching' pattern.
	 */
	public DNASeq pileUpWithDNAseq(DNASeq otraSeq, GeneticCode myGC) {
		return otraSeq.pileUpWithProtseq(this,myGC);

	}
	/**
	 * this method is used to pile up two Protein sequences into a DNA sequence.
	 * Is part of a 'double dispatching' pattern.
	 */
	public DNASeq pileUpWithProtseq(ProtSeq otraSeq, GeneticCode myGC) {
		return otraSeq.backTranslate(myGC).pileUpWithProtseq(this, myGC);
	}

	@Override
	public Sequence toDNA() {
		return new DNASeq(this.getSequence(), this.getDescription());
	}

	@Override
	public Sequence toProtein() {
		return this;
	}

	@Override
	public int sizeInBases() {
		return 3;
	}

	@Override
	public String getPrintableSequencePaddedToNucleotide() {
		return this.getSequence().chars().flatMap(c -> {
			return Arrays.stream(new int[]{' ', c, ' '});
		}).collect(
			StringBuilder::new,
			StringBuilder::appendCodePoint,
			StringBuilder::append
		).toString();
	}
}

