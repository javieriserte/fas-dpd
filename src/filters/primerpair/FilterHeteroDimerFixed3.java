package filters.primerpair;

import sequences.dna.Primer;
import sequences.util.compare.MatchingStrategy;
import sequences.util.compare.SequenceComparator;

public class FilterHeteroDimerFixed3 extends FilterPrimerPair {

	
	private int maxMatchingSegmentSize;
	private MatchingStrategy ms;

	
	public FilterHeteroDimerFixed3(int maxMatchingSegmentSize, MatchingStrategy ms) {
		super();
		this.maxMatchingSegmentSize = maxMatchingSegmentSize;
		this.ms = ms;
	}

	@Override public boolean filter(Primer p1, Primer p2) { 
	
	  return !SequenceComparator.haveNonGappedComplementaryRegionsWithFixed3End(
			  	p1.getSequence(), 
			  	p2.getSequence(), 
			  	this.maxMatchingSegmentSize, 
			  	this.ms); 
	}

}