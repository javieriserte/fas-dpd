package filters.singlePrimer;

import sequences.dna.Primer;

public class FilterPrimerScore extends FilterSinglePrimer {
	private double minScore=0;
	
	// CONSTRUCTOR

	public FilterPrimerScore(double minScore) {
		this.minScore = minScore;
	}
	
	// PUBLIC INTERFACE
	
	@Override public boolean filter(Primer p) {
		return p.getScore()>=this.minScore;
		
	}

}
