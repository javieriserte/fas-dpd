

package filters.validator;

import filters.singlePrimer.FilterSinglePrimer;

public class ValidateForFilterSinglePrimer extends ValidatorSimple {
	private FilterSinglePrimer filter;
	
	public ValidateForFilterSinglePrimer(FilterSinglePrimer filter2) {
		this.filter = filter2;
	}
	
	@Override public boolean validate(Validable p) {
		
		PrimerValidable p0 = (PrimerValidable) p;
		
		return this.filter.filter(p0.getPrimer());
		
	}

	@Override
	public String toString() {
		return "ValidateForFilterSinglePrimer [filter=" + filter + "]";
	}
	
	

}
