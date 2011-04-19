package filters.singlePrimer;

import sequences.dna.DegeneratedPrimerIterator;
import sequences.dna.Primer;
import sequences.util.santaLuciaEnergeticParameters.EnergeticValues;
import sequences.util.santaLuciaEnergeticParameters.SantaLuciaEnergetics;


/**
 * This Filter compares the energetic stability of 3' and 5'. (the first and last n bases)
 * If 3' end is much more stable than 5' there is not a efficient and specific annealing.
 * 
 * This calculation is made using SantaLucia method. penalties for 5' and 3' position are not considered.
 * 
 * @author Javier Iserte
 */
public class Filter5vs3Stability extends FilterSinglePrimer{

	// TODO modify filter to accept degenerated sequences.
	
	private double deltaGLimit;
	private double deltaG3;
	private double deltaG5;
	private double kelvinTemp;
	private double monovalentMolar; 
	private int    len; // len is number of bases of the 3' and 5' that will be used to compare stability.
	
	// CONSTRUCTOR
	
	/**
	 * Creates a new instance of Filter5vs3Stability
	 * 
	 * @param deltaGLimit is the minimum difference between 5' and 3' deltaG values expected for a primer.
	 * DeltaG-5' < DeltaG-3' + deltaGLimit. deltaGLimit is usually 1.5 kcal/mol positive.  
	 * @param kelvinTemp is the temperate in Kelvin used to estimate DeltaG values.
	 * @param monovalent is the molar concentration of monovalent ions. Typically Na(+).
	 * @param len is the number of bases from each end that will be used to estimate deltaG difference.
	 *  
	 */
	public Filter5vs3Stability(double deltaGLimit, double kelvinTemp, double monovalentMolar, int len) {
		this.deltaGLimit = deltaGLimit;
		this.kelvinTemp = kelvinTemp;
		this.monovalentMolar = monovalentMolar;
		this.len = len;
	}
	
	// PUBLIC INTERFACE
	
	/**
	 * Perform the filter operation.
	 * 
	 */
	@Override public boolean filter(Primer p) {
		this.deltaG5 = this.calculateStability(p.getSequence().subSequence(0, this.len-1));
		this.deltaG3 = this.calculateStability(p.getSequence().subSequence(
				p.getSequence().length()-this.len, 
				p.getSequence().length()-1)
				);
		System.out.println(this.deltaG5 + " + " + this.deltaGLimit + " < " + this.deltaG3);
		return (this.deltaG5 < this.deltaG3 + this.deltaGLimit);
	}

	// FACTORY 
	
	public static Filter5vs3Stability getStandard5vs3StabilityFilter() {
		return new Filter5vs3Stability(1.5, 273 + 37, 0.05, 5);
	}
	
	// PRIVATE METHODS
	
	/**
	 * Calculates DeltaG values for each end.
	 */
	private double calculateStability(CharSequence subSequence) {

		// TODO implement a dynamic programming strategy 
		
		DegeneratedPrimerIterator dpi = new DegeneratedPrimerIterator(subSequence.toString(), 1000);
		
		EnergeticValues r = new EnergeticValues();
		dpi.start();
		int counter =0;
		while (dpi.hasNext()) {
			counter++;
			SantaLuciaEnergetics sle = new SantaLuciaEnergetics();
			EnergeticValues ev = sle.getDuplexStability(dpi.next(), this.kelvinTemp);
			ev = sle.SaltCorrection(this.monovalentMolar, 0, ev, this.len);
			r.add(ev);
			
		}

		return r.getDeltaG()/counter;
	
	}

}
