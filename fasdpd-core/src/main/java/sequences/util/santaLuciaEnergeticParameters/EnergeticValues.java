
package sequences.util.santaLuciaEnergeticParameters;

public class EnergeticValues {

	double deltaH; 
	double deltaS; 
	double deltaG; 
	
	// PUBLIC INTERFACE
	
	public void add(EnergeticValues other) {
		this.deltaH += other.getDeltaH(); 
		this.deltaS += other.getDeltaS();
		this.deltaG += other.getDeltaG();
	}
	
	public String toString() {
		return "DeltaH: " + this.deltaH + "| DeltaS: " + this.deltaS + "| DeltaG:" + this.deltaG; 
	}
	
	// GETTERS & SETTERS
	/**
	 * @return the deltaG
	 */
	public double getDeltaG() {
		return this.deltaG;
	}
	
	public void setDeltaG(double deltag) {
		this.deltaG = deltag;
	}

	/**
	 * @return the deltaH
	 */
	public double getDeltaH() {
		return deltaH;
	}
	/**
	 * @param deltaH the deltaH to set
	 */
	public void setDeltaH(double deltaH) {
		this.deltaH = deltaH;
	}
	/**
	 * @return the deltaS
	 */
	public double getDeltaS() {
		return deltaS;
	}
	/**
	 * @param deltaS the deltaS to set
	 */
	public void setDeltaS(double deltaS) {
		this.deltaS = deltaS;
	}
	
	public void setDeltaGFromDeltaHAndDeltaS(double kelvinTemp) {
		// This calculation is correct if deltaH value is in kCal/mol and DeltaS in cal/mol
		// returns DeltaG in kCal/mol
		this.setDeltaG(deltaH - kelvinTemp*deltaS/1000); 
	}
	
	
}
