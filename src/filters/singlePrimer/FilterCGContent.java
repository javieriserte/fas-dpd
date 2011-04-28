package filters.singlePrimer;

import sequences.dna.Primer;
import sequences.util.gccontent.GCContent;

/**
 * Validate primers that have its G+C content between 'min' and 'max' values.
 * 
 * @author Javier Iserte
 *
 */
public class FilterCGContent extends FilterSinglePrimer {

	private float max;
	private float min;
	
	// Contructor
	/**
	 * Creates a new instance of FilterGCContent.
	 * Supports degenerated sequences.
	 * 
	 * The GC content of a primer must be between 'min' & 'max'. Min and Max are percentages.
	 */
	public FilterCGContent(float min, float max) {
		// min & max are given as percentages (0 - 100), but are stored internally as a fraction from 0 to 1.
		this.min = min / 100;
		this.max = max / 100;
	}
	
	// Public Instance Methods

	/**
	 * This method evaluate that the primer have its G+C content between 'min' and 'max' values.
	 * 
	 * returns True if the the primer has the G+C content in the range.
	 *         False otherwise.
	 */
	@Override public boolean filter(Primer p) {
		float GC = GCContent.calculateGCContent(p.getSequence());
		return GC >= this.min&&GC<=this.max;
	}

	@Override
	public String toString() {
		return "FilterCGContent [max=" + max + ", min=" + min + "]";
	}
	
	
	
	
}
