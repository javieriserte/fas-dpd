package fasdpd;

import sequences.dna.Primer;
/**
 * PrimerPair objects contains two Primer object. One supposed to be forward
 * sense and the other to be reverse.
 */
public class PrimerPair {
	private Primer forward;
	private Primer reverse;
	private double score;

	// CONSTRUCTOR
	/**
	 * Creates a new PrimerPair. Both, forward and reverse primers must be supplied.
	 */
	public PrimerPair(Primer forward, Primer reverse) {
		super();
		this.forward = forward;
		this.reverse = reverse;
		this.score = 0;
	}

	// GETTERS & SETTERS

	/**
	 * @return the forward
	 */
	public Primer getForward() {
		return forward;
	}

	/**
	 * @param forward the forward primer to set
	 */
	public void setForward(Primer forward) {
		this.forward = forward;
	}

	/**
	 * @return the reverse
	 */
	public Primer getReverse() {
		return reverse;
	}


	/**
	 * @param reverse the reverse primer to set
	 */
	public void setReverse(Primer reverse) {
		this.reverse = reverse;
	}


	/**
	 * @return the score
	 */
	public double getScore() {
		return score;
	}


	/**
	 * @param score the score to set
	 */
	public void setScore(double score) {
		this.score = score;
	}
	
	

	
	
}
