package filters.singlePrimer;

import sequences.dna.Primer;
import sequences.util.santaLuciaEnergeticParameters.SantaLuciaEnergetics;



/**
 * This Filter compares the energetic stability of 3' and 5'. (the first and last 5 bases)
 * 5' must be more stable than 3' for a efficient and specific annealing.
 * 
 * @author Javier Iserte
 */
public class Filter5vs3Stability extends FilterSinglePrimer{


	private double deltaGLimit;
	private double deltaG3;
	private double deltaG5;
	
	// Constructor
	
	/**
	 * Creates a new instance of Filter5vs3Stability
	 * 
	 * param deltaGLimit is the minimum difference between 5' and 3' deltaG values expected for a primer.
	 * deltaGLimit = (DeltaG-5' - DeltaG-3') 
	 */
	public Filter5vs3Stability(double deltaGLimit) {
		this.deltaGLimit = deltaGLimit;
	}
	
	// Public Instance Methods
	
	@Override public boolean filter(Primer p) {
		this.deltaG5 = this.calculateStability(p.getSequence().subSequence(0, 4));
		this.deltaG3 = this.calculateStability(p.getSequence().subSequence(
				p.getSequence().length()-5, 
				p.getSequence().length()-1)
				);
		
		
		return (this.deltaG5-this.deltaG3)>this.deltaGLimit;
	}

	private double calculateStability(CharSequence subSequence) {
		return (new SantaLuciaEnergetics()).getDuplexStability(subSequence.toString(), 37).getDeltaG(37);
		
		// TODO check temperature in stability 
		
	}

}
