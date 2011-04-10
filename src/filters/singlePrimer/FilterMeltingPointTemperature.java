package filters.singlePrimer;

import sequences.dna.Primer;
import sequences.util.tmcalculator.TmEstimator;


public class FilterMeltingPointTemperature extends FilterSinglePrimer {
	private double min;
	private double max;
	private TmEstimator tmEstimator;
	
	
	public FilterMeltingPointTemperature(double tMin, double tMax, TmEstimator tmEstimator) {
		this.min = tMin;
		this.max = tMax;
		this.tmEstimator = tmEstimator;
	}
	
	@Override public boolean filter(Primer p) {
		tmEstimator.calculateTM(p);
		double temp = tmEstimator.mean();
		return temp>=this.min && temp<=this.max;
	}

}
