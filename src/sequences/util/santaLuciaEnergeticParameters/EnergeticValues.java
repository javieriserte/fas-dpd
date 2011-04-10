package sequences.util.santaLuciaEnergeticParameters;

public class EnergeticValues {

	double deltaH;
	double deltaS;
	
	// PUBLIC INTERFACE
	
	public void add(EnergeticValues other) {
		this.deltaH += other.getDeltaH(); 
		this.deltaS += other.getDeltaS();
	}
	
	// GETTERS & SETTERS
	/**
	 * @return the deltaG
	 */
	public double getDeltaG(double temp) {
		return deltaH - temp*deltaS;
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
	
	
	
}
