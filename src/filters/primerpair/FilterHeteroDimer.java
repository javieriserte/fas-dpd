package filters.primerpair;

import sequences.dna.Primer;
import sequences.util.compare.MatchingStrategy;
import sequences.util.compare.SequenceComparator;


/**
 * Filter Primers that does not form an hetero dimer with other Primer of a max lenght a given value a consecutive perfectly annealed bases. 
 * 
 * @author jiserte
 *
 */
public class FilterHeteroDimer extends FilterPrimerPair{
	
	private int maxMatchingSegmentSize;
	private MatchingStrategy ms;

	
	public FilterHeteroDimer(int maxMatchingSegmentSize, MatchingStrategy ms) {
		super();
		this.maxMatchingSegmentSize = maxMatchingSegmentSize;
		this.ms = ms;
	}

	@Override public boolean filter(Primer p1, Primer p2) { 
	
	  return !SequenceComparator.haveNonGappedComplementaryRegions(
			  	p1.getSequence(), 
			  	p2.getSequence(), 
			  	this.maxMatchingSegmentSize, 
			  	this.ms); 
		
	}

	@Override public String toString() {
		return "FilterHeteroDimer [maxMatchingSegmentSize="
				+ maxMatchingSegmentSize + ", ms=" + ms + "]";
	}
	
	

}