package filters.primerpair;

import sequences.dna.Primer;
import filters.Filter;

public abstract class FilterPrimerPair implements Filter {

	public abstract boolean filter(Primer p1, Primer p2);
	
	public boolean isSinglePrimerFilter() {
		return false;
	}
}
