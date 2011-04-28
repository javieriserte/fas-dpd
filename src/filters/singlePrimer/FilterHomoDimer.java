package filters.singlePrimer;

import sequences.dna.Primer;
import sequences.util.compare.MatchingStrategy;
import sequences.util.compare.SequenceComparator;

public class FilterHomoDimer extends FilterSinglePrimer{
	private int largerthan;
	private MatchingStrategy matchingStrategy;
	
	public FilterHomoDimer(int largerthan, MatchingStrategy matchingStrategy) {
		this.largerthan = largerthan;
		this.matchingStrategy = matchingStrategy;
	}
	
	@Override public boolean filter(Primer p) {
		
		return !SequenceComparator.haveNonGappedComplementaryRegions(p.getSequence() , p.getSequence(), largerthan, this.matchingStrategy);
		
	}

	@Override
	public String toString() {
		return "FilterHomoDimer [largerthan=" + largerthan
				+ ", matchingStrategy=" + matchingStrategy + "]";
	}
	
	
	

}