package filters.primerpair;

import sequences.dna.Primer;
import sequences.util.gccontent.GCContent;
/**
 * 
 * @author Javier Iserte
 */
public class FilterGCCompatibility extends FilterPrimerPair {

	private double maxDifferencePercentageAccepted;
	
	
	public FilterGCCompatibility(double maxDifferencePercentageAccepted) {
		super();
		this.maxDifferencePercentageAccepted = maxDifferencePercentageAccepted;
	}

	@Override public boolean filter(Primer p1, Primer p2) { 
	
		int v = (int) (10000* Math.abs( 
				             GCContent.calculateGCContent(p1.getSequence() ) - 
				             GCContent.calculateGCContent(p2.getSequence() )
				             )
				       ); 
		return  v <= 100*maxDifferencePercentageAccepted; 
	}

}
