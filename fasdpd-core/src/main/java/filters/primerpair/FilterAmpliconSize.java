package filters.primerpair;

import sequences.dna.Primer;
	
/**
 * Filter primers that have more than a maximum size.
 */
public class FilterAmpliconSize extends FilterPrimerPair{
	private int maxSizeAccepted;
	
	public FilterAmpliconSize(int maxSizeAccepted) {
		super();
		this.maxSizeAccepted = maxSizeAccepted;
	}

	/**
	 * verify that two primers in opposite sense produces an amplification product shorter than a maximum value.
	 * The size of amplification product is estimated by its position in the alignment. If the alignment has gaps, the real-life prouct will be shorter than estimated value.
	 */
	@Override public boolean filter(Primer p1, Primer p2) {
		if (p1.isDirectStrand()==p2.isDirectStrand()) return false;
		
		int s1 = p1.getStart();
		int s2 = p2.getStart();
		int f1 = p1.getEnd();
		int f2 = p2.getEnd();
		
		boolean correctOrientation = (s2 > f2 && s2 > s1 && s1 < f1) 
		                              ||
		                             (s1 > f1 && s1 > s2 && s2 < f2);
		
		return correctOrientation && (Math.abs(s1 - s2) + 1 <= this.maxSizeAccepted);
	}

	@Override public String toString() {
		return "FilterAmpliconSize [maxSizeAccepted=" + maxSizeAccepted + "]";
	}
	
	
}
