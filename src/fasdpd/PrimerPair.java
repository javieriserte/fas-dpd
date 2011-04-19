package fasdpd;

import sequences.dna.Primer;

public class PrimerPair {
	private Primer forward;
	private Primer reverse;
	private double score;
	
	
	// CONSTRUCTOR
	
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
	 * @param forward the forward to set
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
	 * @param reverse the reverse to set
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
