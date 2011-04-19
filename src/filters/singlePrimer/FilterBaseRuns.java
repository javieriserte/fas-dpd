package filters.singlePrimer;

import sequences.dna.Primer;

/**
 * 
 * @author Javier Iserte
 *
 */
public class FilterBaseRuns extends FilterSinglePrimer {
	private int maxRunLengthAccepted;

	// TODO modify filter to accept degenerated sequences.

	public FilterBaseRuns(int maxRunLengthAccepted) {
		super();
		this.maxRunLengthAccepted = maxRunLengthAccepted;
	}

	@Override
	public boolean filter(Primer p) {
		int baseR =1;
		for (int i=1; i<p.getLength(); i++) {
			baseR = (p.getSequence().charAt(i)==p.getSequence().charAt(i-1) ? baseR+=1 : 1);
			if (baseR>this.maxRunLengthAccepted) return false;
		}
		return true;
	}

}
