package sequences;

import sequences.alignment.Apilable;

/**
 * This abstract class represents a sequence.
 * Stores the sequence itself and a description useful to identify it.
 * @author Javier Iserte <jiserte@unq.edu.ar>
 */
public abstract class Sequence implements Apilable {

	// INSTANCE VARIABLES
	private String sequence;
	private String description;

	// CONSTRUCTOR
	/**
	 * Creates a new sequence.
	 */
	public Sequence(String sequence, String description) {
		super();
		this.setSequence(sequence);
		this.description = description;
	}

	// INSTANCE METHODS
	/**
	 * return the length of the sequence.
	 */
	public int getLength() {
		return this.getSequence().length();
	}

	// GETTERS & SETTERS
	public String getSequence() {
		return sequence;
	}
	public void setSequence(String sequence) {
		this.sequence = sequence.toUpperCase();
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return  the number of bases that one element of this class uses to
	 * when it is retro-translated, copied, or transcribed to a nucleotide
	 * sequence.
	 */
	public abstract int sizeInBases();

  public abstract Sequence toDNA();

	public abstract Sequence toProtein();

	/**
	 * @return a String with the necessary padding to be printed in a nucleotide
	 * Multiple Sequence Alignment.
	 */
	public abstract String getPrintableSequencePaddedToNucleotide();
}
