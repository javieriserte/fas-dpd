package filters.singlePrimer;

import degeneration.BaseDeg;
import sequences.dna.Primer;
/**
 * This class represents the condition that a primer have a degenerated base in the 3' position.
 * @author Javier Iserte <jiserte@unq.edu.ar>
 * 
 */
public class FilterDegeneratedEnd extends FilterSinglePrimer {

	/**
	 * Validates that a primer is degenerated in the last position.
	 * 
	 * Returns true if the end is degenerated.
	 * 			false otherwise.
	 */
	public boolean filter(Primer p) {
		
		return BaseDeg.getDegValueFromChar(p.getSequence().charAt(p.getLength()-1))==1;
	}

	@Override
	public String toString() {
		return "FilterDegeneratedEnd []";
	}
	
	
	
}
