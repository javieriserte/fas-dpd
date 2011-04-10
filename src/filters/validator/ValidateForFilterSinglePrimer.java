package filters.validator;

import filters.singlePrimer.FilterSinglePrimer;

public class ValidateForFilterSinglePrimer extends ValidatorSimple {
	
	private FilterSinglePrimer filter;
	
	public ValidateForFilterSinglePrimer(FilterSinglePrimer filter) {
		this.filter = filter;
	}
	
	@Override public boolean validate(Validable p) {
		
		PrimerValidable p0 = (PrimerValidable) p;
		
		return this.filter.filter(p0.getPrimer());
		
	}

}
