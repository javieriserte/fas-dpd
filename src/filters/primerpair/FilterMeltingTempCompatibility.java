package filters.primerpair;

import sequences.dna.Primer;
import sequences.util.tmcalculator.TmEstimator;

public class FilterMeltingTempCompatibility extends FilterPrimerPair {
	private double maxTempDiff;
	private TmEstimator tmEstimator;
	
	public FilterMeltingTempCompatibility(double macxTempDiff,TmEstimator tmEstimator) {
		super();
		this.maxTempDiff = macxTempDiff;
		this.tmEstimator = tmEstimator;
	}

	@Override public boolean filter(Primer p1, Primer p2) {

		this.tmEstimator.calculateTM(p1);
		double t1 = tmEstimator.mean();
		
		this.tmEstimator.calculateTM(p2);
		double t2 = tmEstimator.mean();
		
		return Math.abs(t1-t2)<=this.maxTempDiff; 
		
	}

	@Override public String toString() {
		return "FilterMeltingTempCompatibility [maxTempDiff=" + maxTempDiff
				+ ", tmEstimator=" + tmEstimator + "]";
	}
	
	
}
