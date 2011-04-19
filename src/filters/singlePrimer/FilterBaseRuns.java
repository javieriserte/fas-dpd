package filters.singlePrimer;

import degeneration.BaseDeg;
import sequences.dna.Primer;

/**
 * 
 * @author Javier Iserte
 *
 */
public class FilterBaseRuns extends FilterSinglePrimer {
	private int maxRunLengthAccepted;

	
	public FilterBaseRuns(int maxRunLengthAccepted) {
		super();
		this.maxRunLengthAccepted = maxRunLengthAccepted;
	}

	@Override
	public boolean filter(Primer p) {
		int baseR =0;

		for (int j=0; j<4;j++) {
			baseR = 0;
			for (int i=0; i<p.getLength(); i++) {
				
				baseR = (BaseDeg.containsBaseIntInChar(j,p.getSequence().charAt(i)) ? baseR+=1 : 0);
				if (baseR>this.maxRunLengthAccepted) return false;
			}
		}
		
		return true;
	}

}
