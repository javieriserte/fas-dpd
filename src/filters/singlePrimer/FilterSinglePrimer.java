package filters.singlePrimer;

import filters.Filter;
import sequences.dna.Primer;

public abstract class FilterSinglePrimer implements Filter {
	
	public abstract boolean filter(Primer p);
	
	
}
