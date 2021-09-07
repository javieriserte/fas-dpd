package filters.singlePrimer;

import sequences.dna.Primer;
import sequences.util.compare.MatchingStrategy;
import sequences.util.compare.SequenceComparator;


/**
 * Filter Primers that does not form an hetero dimer with other Primer of a max lenght a given 
 * value a consecutive perfectly annealed bases starting a 3' of any primer. 
 * 
 * @author jiserte
 *
 */
public class FilterHomoDimerFixed3 extends FilterSinglePrimer{

	private int largerthan;
	private MatchingStrategy matchingStrategy;
	
	public FilterHomoDimerFixed3(int largerthan,MatchingStrategy matchingStrategy) {
		this.largerthan = largerthan;
		this.matchingStrategy = matchingStrategy;
	}
	
	@Override public boolean filter(Primer p) {
		
		return ! SequenceComparator.haveNonGappedComplementaryRegionsWithFixed3End(p.getSequence() , p.getSequence(), largerthan, matchingStrategy);
		
	}

	@Override
	public String toString() {
		return "FilterHomoDimerFixed3 [largerthan= " + largerthan
				+ ", matchingStrategy= " + matchingStrategy + "]";
	}
	
		
	

}