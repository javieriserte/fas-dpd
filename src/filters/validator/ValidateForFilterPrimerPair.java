package filters.validator;

import filters.primerpair.FilterPrimerPair;


public class ValidateForFilterPrimerPair extends ValidatorSimple {

	private FilterPrimerPair filter;

	public ValidateForFilterPrimerPair(FilterPrimerPair filter) {
		super();
		this.filter = filter;
	}

	@Override
	public boolean validate(Validable p) {
		PrimerPairValidable p0 = (PrimerPairValidable) p;
		return this.filter.filter(p0.getP1(), p0.getP2());
	}

	@Override
	public String toString() {
		return "ValidateForFilterPrimerPair [filter=" + filter + "]";
	}

}
