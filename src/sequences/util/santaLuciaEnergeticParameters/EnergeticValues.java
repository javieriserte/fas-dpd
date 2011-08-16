/*
 * You may not change or alter any portion of this comment or credits
 * of supporting developers from this source code or any supporting source code
 * which is considered copyrighted (c) material of the original comment or credit authors.
 *
 * THERE IS NO WARRANTY FOR THE PROGRAM, TO THE EXTENT PERMITTED BY APPLICABLE LAW. 
 * EXCEPT WHEN OTHERWISE STATED IN WRITING THE COPYRIGHT HOLDERS AND/OR OTHER PARTIES 
 * PROVIDE THE PROGRAM “AS IS” WITHOUT WARRANTY OF ANY KIND, EITHER EXPRESSED OR IMPLIED, 
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS 
 * FOR A PARTICULAR PURPOSE. THE ENTIRE RISK AS TO THE QUALITY AND PERFORMANCE OF THE 
 * PROGRAM IS WITH YOU. SHOULD THE PROGRAM PROVE DEFECTIVE, YOU ASSUME THE COST OF ALL 
 * NECESSARY SERVICING, REPAIR OR CORRECTION.
 
 * IN NO EVENT UNLESS REQUIRED BY APPLICABLE LAW OR AGREED TO IN WRITING WILL ANY COPYRIGHT 
 * HOLDER, OR ANY OTHER PARTY WHO MODIFIES AND/OR CONVEYS THE PROGRAM AS PERMITTED ABOVE, 
 * BE LIABLE TO YOU FOR DAMAGES, INCLUDING ANY GENERAL, SPECIAL, INCIDENTAL OR CONSEQUENTIAL
 * DAMAGES ARISING OUT OF THE USE OR INABILITY TO USE THE PROGRAM (INCLUDING BUT NOT LIMITED 
 * TO LOSS OF DATA OR DATA BEING RENDERED INACCURATE OR LOSSES SUSTAINED BY YOU OR THIRD 
 * PARTIES OR A FAILURE OF THE PROGRAM TO OPERATE WITH ANY OTHER PROGRAMS), EVEN IF SUCH 
 * HOLDER OR OTHER PARTY HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * FAS-DPD project, including algorithms design, software implementation and experimental laboratory work, is being developed as a part of the Research Program:
 * 	"Microbiología molecular básica y aplicaciones biotecnológicas"
 * 		(Basic Molecular Microbiology and biotechnological applications)
 * 
 * And is being conducted in:
 * 	LIGBCM: Laboratorio de Ingeniería Genética y Biología Celular y Molecular.
 *		(Laboratory of Genetic Engineering and Cellular and Molecular Biology)
 *	Universidad Nacional de Quilmes.
 *		(National University Of Quilmes)
 *	Quilmes, Buenos Aires, Argentina.
 *
 * The complete team for this project is formed by:
 *	Lic.  Javier A. Iserte.
 *	Lic.  Betina I. Stephan.
 * 	ph.D. Sandra E. Goñi.
 * 	ph.D. P. Daniel Ghiringhelli.
 *	ph.D. Mario E. Lozano.
 *
 * Corresponding Authors:
 *	Javier A. Iserte. <jiserte@unq.edu.ar>
 *	Mario E. Lozano. <mlozano@unq.edu.ar>
 */

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
