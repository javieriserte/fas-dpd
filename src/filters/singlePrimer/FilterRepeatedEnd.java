package filters.singlePrimer;

import sequences.dna.Primer;
/**
 * This class filters primers that has the two last positions base the same base. 
 * @author Javier Iserte <jiserte@unq.edu.ar>
 * 
 */
public class FilterRepeatedEnd extends FilterSinglePrimer {
	/**
	 * Validates that a primer is repeated in the last two position.
	 * 
	 * @return false if the end is repeated
	 * 			true otherwise.
	 */
	public boolean filter(Primer p) {
		return !((p.getSequence().charAt(p.getSequence().length()-1)) == 
			    (p.getSequence().charAt(p.getSequence().length()-2)));
	}

	@Override
	public String toString() {
		return "FilterRepeatedEnd []";
	}
	
	
}
