package filters.validator;

import sequences.dna.Primer;

public class PrimerPairValidable implements Validable {

	private Primer p1;
	private Primer p2;
	
	//  CONSTRUCTOR
	public PrimerPairValidable(Primer p1, Primer p2) {
		super();
		this.p1 = p1;
		this.p2 = p2;
	}
	
	// GETTERS
	
	/**
	 * @return the p1
	 */
	public Primer getP1() {
		return p1;
	}


	/**
	 * @return the p2
	 */
	public Primer getP2() {
		return p2;
	}

	
	
}
