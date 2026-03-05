package filters.singlePrimer;

import degeneration.BaseDeg;
import sequences.dna.DegeneratedPrimerIterator;
import sequences.dna.Primer;
import sequences.util.santaLuciaEnergeticParameters.EnergeticValues;
import sequences.util.santaLuciaEnergeticParameters.SantaLuciaEnergetics;


/**
 * This Filter compares the energetic stability of 3' and 5'. (the first and
 * last n bases)
 * If 3' end is much more stable than 5' there is not a efficient and specific
 * annealing.
 * This calculation is made using SantaLucia method. Penalties for 5' and 3'
 * position are not considered.
 */
public class Filter5vs3Stability extends FilterSinglePrimer{
	private double deltaGLimit;
	private double deltaG3;
	private double deltaG5;
	private double kelvinTemp;
	private double monovalentMolar;
	// len is number of bases of the 3' and 5' that will be used to compare
	// stability.
	private int len;
	// CONSTRUCTOR
	/**
	 * Creates a new instance of Filter5vs3Stability
	 * @param deltaGLimit is the minimum difference between 5' and 3' deltaG
	 * values expected for a primer.
	 * DeltaG-5' + deltaGLimit < DeltaG-3' . deltaGLimit is usually 1.5 kcal/mol
	 * positive.
	 * @param kelvinTemp is the temperate in Kelvin used to estimate DeltaG
	 * values.
	 * @param monovalent is the molar concentration of monovalent ions.
	 * Typically Na(+).
	 * @param len is the number of bases from each end that will be used to
	 * estimate deltaG difference.
	 */
	public Filter5vs3Stability(
			double deltaGLimit,
			double kelvinTemp,
			double monovalentMolar,
			int len) {
		this.deltaGLimit = deltaGLimit;
		this.kelvinTemp = kelvinTemp;
		this.monovalentMolar = monovalentMolar;
		this.len = len;
	}

	/**
	 * Perform the filter operation.
	 */
	@Override public boolean filter(Primer p) {
		CharSequence ssInit = p.getSequence().subSequence(0, this.len);
		CharSequence ssEnd =  p.getSequence().subSequence(
			p.getSequence().length()-this.len,
			p.getSequence().length()
		);
		this.deltaG5 = this.calculateStability(ssInit);
		this.deltaG3 = this.calculateStability(ssEnd);
		return (this.deltaG5 + this.deltaGLimit < this.deltaG3 );
	}

	public static Filter5vs3Stability getStandard5vs3StabilityFilter() {
		return new Filter5vs3Stability(1.5, 273 + 37, 0.05, 5);
	}

	/**
	 * Calculates DeltaG values for each end.
	 */
	private double calculateStability(CharSequence subSequence) {
		SantaLuciaEnergetics sle = new SantaLuciaEnergetics();
		double dh = 0;
		double ds = 0;
		EnergeticValues ev = new EnergeticValues();
		for (int i=1; i<subSequence.length(); i++) {
			String degDinucleotide = subSequence
				.subSequence(i-1, i+1)
				.toString();
			DegeneratedPrimerIterator dpi = new DegeneratedPrimerIterator(
				degDinucleotide);
			dpi.start();
			int degValue = BaseDeg.getDegValueFromString(degDinucleotide);
			while(dpi.hasNext()) {
				ev = sle.getDuplexStability(dpi.next(), this.kelvinTemp);
				dh += ev.getDeltaH() / degValue;
				ds += ev.getDeltaS() / degValue;
			}
		}
		ev.setDeltaH(dh);
		ev.setDeltaS(ds);
		ev.setDeltaGFromDeltaHAndDeltaS(this.kelvinTemp);
		ev = sle.SaltCorrection(this.monovalentMolar, 0, ev, this.len);
		return ev.getDeltaG();
	}

	@Override
	public String toString() {
		return "Filter5vs3Stability [deltaGLimit=" + deltaGLimit + ", deltaG3="
				+ deltaG3 + ", deltaG5=" + deltaG5 + ", kelvinTemp="
				+ kelvinTemp + ", monovalentMolar=" + monovalentMolar
				+ ", len=" + len + "]";
	}
}
